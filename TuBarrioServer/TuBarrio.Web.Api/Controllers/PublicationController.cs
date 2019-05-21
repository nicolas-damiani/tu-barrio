using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using TuBarrio.Entities;
using TuBarrio.BusinessLogic;

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

        [HttpGet]
        [Route("api/Publications")]
        public IHttpActionResult GetAllPublications()
        {
            try
            {
                List<Publication> publications = publicationLogic.GetAllPublications();
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
        [Route("api/Publications")]
        public IHttpActionResult GetAllPublicationsFromUser(string token)
        {
            try
            {
                List<Publication> publications = publicationLogic.GetAllFromUser(token);
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
        public IHttpActionResult GetPublicationById(int id, string token)
        {
            try
            {
                Publication result = publicationLogic.GetPublicationById(id, token);
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

        [HttpPost]
        [Route("api/Publication/{publicationId}/Images")]
        public IHttpActionResult AddImagesToPublication(int publicationId, string token, [FromBody]EncodedImageModel[] images)
        {
            try
            {
                Publication publicationToModify = publicationLogic.GetPublicationById(publicationId, token);
                List<EncodedImage> imagesToAdd = new List<EncodedImage>();
                for (int i = 0; i < images.Length; i++)
                {
                    EncodedImage image = imageLogic.getEncodedImageFromModel(images[i]);
                    imagesToAdd.Add(image);
                }
                publicationLogic.AddImageToPublication(imagesToAdd, publicationToModify, token);
                return Ok("Imagen/es agregada/s exitosamente a la publicacion");
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


        [HttpPut]
        [Route("api/Publication")]
        public IHttpActionResult UpdatePublication(string token, int id, [FromBody]PublicationModel model)
        {
            try
            {
                Publication publicationToUpdate = publicationLogic.GetPublicationFromModel(model);
                publicationLogic.UpdatePublication(publicationToUpdate, id, token);
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
        public IHttpActionResult DeletePublication(int id, string token)
        {
            try
            {
                publicationLogic.RemovePublication(id, token);
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
    }
}