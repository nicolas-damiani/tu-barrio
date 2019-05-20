using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.Exceptions;
using TuBarrio.Repository;

namespace TuBarrio.BusinessLogic
{
    public class AuthenticationLogic : IAuthenticationLogic
    {
        private IUserRepository userRepository;

        public User GetUserByEmail(string email)
        {
            User user = userRepository.GetUserByEmail(email);
            if (user != null)
            {
                return user;
            }
            else
            {
                throw new InvalidCredentialException("No se encontro un usuario con ese email");
            }
        }

        public User GetUserWithToken(string tokenRecieved)
        {
            throw new NotImplementedException();
        }

        public string HandleGoogleSignIn(string email, string name, string surname)
        {
            User user = userRepository.GetUserByEmail(email);
            if (user == null)
            {
                user = new User(name, surname, email, "123", new Guid().ToString(), new EncodedImage(""));
                userRepository.AddUser(user);
            }
            return LogIn(user.Email, user.Password);
        }

        public string LogIn(string email, string password)
        {
            string returnToken = userRepository.LogIn(email, password);
            if (returnToken == "")
            {
                throw new InvalidCredentialException("No se encontro un usuario con esas credenciales");
            }
            else
            {
                return returnToken;
            }
        }
    }
}
