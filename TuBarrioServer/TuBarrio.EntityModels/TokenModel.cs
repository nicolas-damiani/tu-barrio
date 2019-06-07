using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.EntityModels
{
    public class TokenModel
    {
        public string Token { get; set; }
        public UserModel User { get; set; }

        public TokenModel(string token, UserModel user)
        {
            this.Token = token;
            this.User = user;
        }
    }
}
