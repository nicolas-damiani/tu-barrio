using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;

namespace TuBarrio.BusinessLogic
{
    public interface IAuthenticationLogic
    {
        string LogIn(string email, string password);
        string HandleGoogleSignIn(string email, string name, string surname);
        User GetUserWithToken(string tokenRecieved);
        User GetUserByEmail(string email);
    }
}
