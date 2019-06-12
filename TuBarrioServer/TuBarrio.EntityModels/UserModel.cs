using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Text;
using TuBarrio.Entities;

namespace TuBarrio.EntityModels
{
    public class UserModel
    {
        private User user;

        public string Name { get; set; }
        public string Surname { get; set; }
        public string Email { get; set; }
        public string ProfileImage { get; set; }
        public string Phone { get; set; }

        public UserModel()
        {
            Name = "";
            Surname = "";
            Email = "";
            ProfileImage = "";
            Phone = "";
        }

        public UserModel(User user)
        {
            Name = user.Name;
            Surname = user.Surname;
            Email = user.Email;
            ProfileImage = user.ProfileImage;
            Phone = user.Phone;
        }
    }
}
