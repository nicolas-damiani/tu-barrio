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
using System.Net.Http.Headers;
using System.Dynamic;
using TuBarrio.Repository;

namespace TuBarrio.Web.Api.Controllers
{
    public class UserController : ApiController
    {
        private IAuthenticationLogic authenticationLogic;
        private IUserLogic userLogic;
        

        public UserController()
        {
            IUserRepository userRepository = new UserRepository();
            this.authenticationLogic = new AuthenticationLogic(userRepository);
            this.userLogic = new UserLogic(userRepository);
        }

        [HttpGet]
        [Route("api/User/GetByToken")]
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
        [Route("api/User/GetByEmail")]
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

        [HttpGet]
        [Route("api/User/LoginUserGoogle")]
        public IHttpActionResult LogInGoogle(string googleToken)
        {
            try
            {
                String token = GetTokenInfo(googleToken);
                User user =  authenticationLogic.GetUserWithToken(token);
                UserModel userModel = new UserModel(user);
                TokenModel tokenModel = new TokenModel(token, userModel);
                return Ok(tokenModel);
            }
            catch (Exception ex) when (ex is System.Data.Entity.Core.EntityException)
            {
                return Content(HttpStatusCode.BadRequest, ex.Message);
            }
            catch (Exception ex)
            {
                return Content(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        public string GetTokenInfo(String googleToken)
        {
            String URL = "https://www.googleapis.com/oauth2/v3/tokeninfo";
            String urlParameters = "?id_token=" + googleToken;
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri(URL);
            client.DefaultRequestHeaders.Accept.Add(
            new MediaTypeWithQualityHeaderValue("application/json"));
            HttpResponseMessage response = client.GetAsync(urlParameters).Result;
            if (response.IsSuccessStatusCode)
            {
                dynamic dataObjects = response.Content.ReadAsAsync<ExpandoObject>().Result;
                String email = dataObjects.email;
                String name = dataObjects.given_name;
                String surname = dataObjects.family_name;
                return authenticationLogic.HandleGoogleSignIn(email, name, surname);
            }
            throw new Exception("Error obteniendo datos de Google");
        }
    }
}