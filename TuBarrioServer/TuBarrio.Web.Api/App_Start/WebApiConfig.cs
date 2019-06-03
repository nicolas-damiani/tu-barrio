using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using TuBarrio.BusinessLogic;
using TuBarrio.Repository;
using TuBarrio.Web.Api.Controllers;

namespace TuBarrio.Web.Api
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API configuration and services

            // Web API routes
            //var cors = new EnableCorsAttribute("*", "*", "GET, HEAD, OPTIONS, POST, PUT, DELETE");
            //config.EnableCors(cors);
            config.MapHttpAttributeRoutes();

            IPublicationRepository publicationRepository = new PublicationRepository();
            IUserRepository userRepository = new UserRepository();

            IAuthenticationLogic authenticationLogic = new AuthenticationLogic(userRepository);
            
            IPublicationLogic publicationLogic = new PublicationLogic(publicationRepository, authenticationLogic);
            IEncodedImageLogic encodedImageLogic = new EncodedImageLogic();
            IUserLogic userLogic = new UserLogic(userRepository);



            PublicationController publicationController = new PublicationController(authenticationLogic, publicationLogic, userLogic, encodedImageLogic);
            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
        }
    }
}
