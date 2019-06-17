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
using FirebaseAdmin.Messaging;

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
       //     SendNotification();
        }

        [HttpGet]
        [Route("api/User/GetByToken")]
        public IHttpActionResult GetUserByToken()
        {
            try
            {
                User user = GetUserFromToken();
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


        [HttpPost]
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
        public IHttpActionResult LogInGoogle(string googleToken, string fcmToken)
        {
            try
            {
                String token = GetTokenInfo(googleToken, fcmToken);
                User user = authenticationLogic.GetUserWithToken(token);
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

        [HttpGet]
        [Route("api/User/GetStats")]
        public IHttpActionResult GetStatisticsFromUser()
        {
            try
            {
                User user = GetUserFromToken();
                int cantPublications = userLogic.GetCantPublications(user);
                int cantComments = userLogic.GetCantComments(user);
                int cantFollowedPublications = userLogic.GetCantFollowed(user);
                StatisticModel statModel = new StatisticModel(cantPublications, cantFollowedPublications, cantComments);
                return Ok(statModel);

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




        public string GetTokenInfo(String googleToken, string fcmToken)
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
                return authenticationLogic.HandleGoogleSignIn(email, name, surname, fcmToken);
            }
            throw new Exception("Error obteniendo datos de Google");
        }


        private User GetUserFromToken()
        {
            string token = ActionContext.Request.Headers.GetValues("Token").First();
            User user = authenticationLogic.GetUserWithToken(token);
            return user;
        }

        public async void SendNotification()
        {
            var registrationToken = "fGzhfCYExKo:APA91bECeI59LLZZ780-DrSYrrjpkY0WwC5rl2DDBB0JvytxsYJbiDjpXmX3xUfPATPcNdig5m1sk5DJrAF-fng82YqSHCv4N0xG9sIp89yFMpOmNpaUU8mb8AwlvrQcwT9JWx-51bCt";

            // See documentation on defining a message payload.
            var message = new Message()
            {
                Data = new Dictionary<string, string>()
                    {
                        { "title", "850" },
                        { "message", "2:45" },
                    },
                Token = registrationToken,
            };

            // Send a message to the device corresponding to the provided
            // registration token.
            string response = await FirebaseMessaging.DefaultInstance.SendAsync(message);
            // Response is a message ID string.
            Console.WriteLine("Successfully sent message: " + response);
        }


    }
}