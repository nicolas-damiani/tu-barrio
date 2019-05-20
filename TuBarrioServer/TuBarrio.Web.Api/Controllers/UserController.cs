using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using TuBarrio.BusinessLogic;
using TuBarrio.Entities;
using TuBarrio.Exceptions;
using TuBarrio.EntityModels;

namespace TuBarrio.Web.Api.Controllers
{
    public class UserController : ApiController
    {
        private IAuthenticationLogic authenticationLogic;
        private IUserLogic userLogic;

        public UserController(IUserLogic uLogic, IAuthenticationLogic authLogic)
        {
            authenticationLogic = authLogic;
            userLogic = uLogic;
        }

        [HttpGet]
        [Route("api/User")]
        public IHttpActionResult GetUserByToken(string token)
        {
            try
            {
                User user = authenticationLogic.GetUserWithToken(token);
                return Ok(user);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is UserException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }

        }

        [HttpGet]
        [Route("api/User")]
        public IHttpActionResult GetUserByEmail(string email)
        {
            try
            {
                User user = authenticationLogic.GetUserByEmail(email);
                return Ok(user);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is UserException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }

        }

        [HttpPost]
        [Route("api/User")]
        public IHttpActionResult AddUser([FromBody]UserModel model)
        {
            try
            {
                User user = userLogic.GetUserFromModel(model);
                userLogic.AddUser(user);
                return Ok("Usuario agregado exitosamente");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is UserException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        [HttpPut]
        [Route("api/User")]
        public IHttpActionResult UpdateUser([FromBody]UserModel modifiedUser)
        {
            try
            {
                User user = userLogic.GetUserFromModel(modifiedUser);
                userLogic.UpdateUser(user);
                return Ok("Usuario modificado correctamente");
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException || ex is UserException)
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