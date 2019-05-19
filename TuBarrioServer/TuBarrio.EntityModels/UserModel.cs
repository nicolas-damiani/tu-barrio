using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Text;

namespace TuBarrio.EntityModels
{
    public class UserModel
    {
        public string Name { get; set; }
        public string Surname { get; set; }
        public string Email { get; set; }
        public string Phone { get; set; }
        public string Password { get; set; }

        public UserModel()
        {
            Name = "";
            Surname = "";
            Email = "";
            Phone = "";
            Password = "";
        }
    }
}
