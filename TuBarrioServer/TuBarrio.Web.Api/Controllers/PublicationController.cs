using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using TuBarrio.Entities;
using TuBarrio.BusinessLogic;
using TuBarrio.EntityModels;
using TuBarrio.Exceptions;
using TuBarrio.Repository;

namespace TuBarrio.Web.Api.Controllers
{
    public class PublicationController : ApiController
    {
        private IAuthenticationLogic authenticationLogic;
        private IPublicationLogic publicationLogic;
        private IUserLogic userLogic;
        private IEncodedImageLogic imageLogic;


        public PublicationController(IAuthenticationLogic authLogic, IPublicationLogic pubLogic, IUserLogic usLogic, IEncodedImageLogic imLogic)
        {
            authenticationLogic = authLogic;
            publicationLogic = pubLogic;
            userLogic = usLogic;
            imageLogic = imLogic;
        }

        public PublicationController()
        {
            IPublicationRepository publicationRepository = new PublicationRepository();
            IUserRepository userRepository = new UserRepository();

            this.authenticationLogic = new AuthenticationLogic(userRepository);

            this.publicationLogic = new PublicationLogic(publicationRepository, authenticationLogic);
            this.imageLogic = new EncodedImageLogic();
            this.userLogic = new UserLogic(userRepository);
        }

        [HttpGet]
        [Route("api/Publications")]
        public IHttpActionResult GetAllPublications()
        {
            try
            {
                List<Publication> publications = publicationLogic.GetAllPublications();
                List<PublicationModel> publicationModels = new List<PublicationModel>();
                foreach (Publication publication in publications)
                {
                    PublicationModel publicationModel = new PublicationModel(publication);
                    publicationModels.Add(publicationModel);
                }
                return Ok(publicationModels);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        [HttpGet]
        [Route("api/Publications/AllFromUser")]
        public IHttpActionResult GetAllPublicationsFromUser()
        {
            try
            {
                User user = GetUserFromToken();
                List<Publication> publications = publicationLogic.GetAllPublicationsFromUser(user.Token);
                return Ok(publications);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        [HttpGet]
        [Route("api/Publication/{id}")]
        public IHttpActionResult GetPublicationById(int id)
        {
            try
            {
                User user = GetUserFromToken();
                Publication result = publicationLogic.GetPublicationById(id, user.Token);
                return Ok(result);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        [HttpPost]
        [Route("api/Publication")]
        public IHttpActionResult AddPublication([FromBody]PublicationModel model)
        {
            try
            {
                User user = GetUserFromToken();
                model.Creator = new UserModel(user);
                Publication publicationToAdd = publicationLogic.GetPublicationFromModel(model);
                publicationLogic.AddPublication(publicationToAdd);
                return Ok("Publicacion agregada exitosamente");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }




        //[HttpPost]
        //[Route("api/Publication/{publicationId}/Images")]
        //public IHttpActionResult AddImagesToPublication(int publicationId, string token, [FromBody]EncodedImageModel[] images)
        //{
        //    try
        //    {
        //        Publication publicationToModify = publicationLogic.GetPublicationById(publicationId, token);
          
        //        publicationLogic.AddImageToPublication(imagesToAdd, publicationToModify, id ,token);
        //        return Ok("Imagen/es agregada/s exitosamente a la publicacion");
        //    }
        //    catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
        //    {
        //        return Content(HttpStatusCode.BadRequest, ex.Message);
        //    }
        //    catch (Exception ex)
        //    {
        //        return Content(HttpStatusCode.InternalServerError, ex.Message);
        //    }
        //}


        [HttpPut]
        [Route("api/Publication")]
        public IHttpActionResult UpdatePublication([FromBody]PublicationModel model, int id)
        {
            try
            {
                User user = GetUserFromToken();
                Publication publicationToUpdate = publicationLogic.GetPublicationFromModel(model);
                publicationLogic.UpdatePublication(publicationToUpdate, id, user.Token);
                return Ok("Publicacion modificada exitosamente");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        [HttpDelete]
        [Route("api/Publication")]
        public IHttpActionResult DeletePublication(int id)
        {
            try
            {
                User user = GetUserFromToken();       
               publicationLogic.RemovePublication(id, user.Token);
                return Ok("Publicacion eliminada exitosamente");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        [HttpPost]
        [Route("api/Publication/Comment")]
        public IHttpActionResult AddCommentToPublication([FromBody]CommentModel commentModel, int publicationId)
        {
            try
            {
                User user = GetUserFromToken();
                Publication publicationToModify = publicationLogic.GetPublicationById(publicationId, user.Token);
                Comment commentToAdd = publicationLogic.GetCommentFromModel(commentModel);
                commentToAdd.Creator = user;
                publicationLogic.AddCommentToPublication(commentToAdd, publicationToModify);
                return Ok("Comentario agregado a publicacion exitosamente");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }


        [HttpGet]
        [Route("api/Publication/GetComments")]
        public IHttpActionResult GetCommentsFromPublication(int publicationId)
        {
            try
            {
                User user = GetUserFromToken();
                Publication publicationToGet = publicationLogic.GetPublicationById(publicationId, user.Token);
                List<Comment> comments = publicationLogic.GetCommentsFromPublication(publicationToGet);
                return Ok(comments);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }


        [HttpGet]
        [Route("api/Publication/GetFollowedPublications")]
        public IHttpActionResult GetFollowedPublication()
        {
            try
            {
                User user = GetUserFromToken();
                List<Publication> toReturn = publicationLogic.GetFollowedPublications(user);
                return Ok(toReturn);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }

        }

        [HttpPost]
        [Route("api/Publication/FollowPublication")]
        public IHttpActionResult FollowPublication(int publicationId)
        {
            try
            {
                User user = GetUserFromToken();
                publicationLogic.FollowPublication(user, publicationId);
                return Ok("Publicacion seguida con exito");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        [HttpDelete]
        [Route("api/Publication/UnfollowPublication")]
        public IHttpActionResult UnfollowPublication(int publicationId)
        {
            try
            {
                User user = GetUserFromToken();
                publicationLogic.UnfollowPublication(user, publicationId);
                return Ok("Publicacion dejada de seguir");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is PublicationException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }


        private User GetUserFromToken()
        {
            string token = ActionContext.Request.Headers.GetValues("Token").First();
            User user = authenticationLogic.GetUserWithToken(token);
            return user;
        }


    }

        }



       