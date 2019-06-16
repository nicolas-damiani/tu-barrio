using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Exceptions;

namespace TuBarrio.Entities
{
    public class User
    {
        public int Id { get; set; }
        private string name;
        private string surname;
        private string email;
      
        public string Surname { get { return surname; } set { if (IsNotEmptySurname(value)) surname = value; } }
        public string Name { get { return name; } set { if (IsNotEmptyName(value)) name = value; } }

        [Key]
        public string Email { get { return email; } set { if (IsValidEmail(value)) email = value; } }
        public string Phone { get; set; }
        public string Token { get; set; }
        public string DeviceNotificationToken { get; set; }
        public DateTime CreatedOn { get; set; }
        public string ProfileImage { get; set; }

        public User()
        {
           
        }



        public User(string name, string surname, string email, string profileImage, string phone, string fcmToken)
        {
            Name = name;
            Surname = surname;
            Email = email;
            Token = "";
            Phone = phone;
            DeviceNotificationToken = fcmToken;
            ProfileImage = profileImage;
            CreatedOn = DateTime.Now;
        }

        public override bool Equals(object obj)
        {
            bool returnValue = false;
            if (obj != null && obj is User)
                returnValue = Email.Equals(((User)obj).Email);
            return returnValue;
        }

        private bool IsValidEmail(string value)
        {
            try
            {
                var addr = new System.Net.Mail.MailAddress(value);
                return addr.Address == value;
            }
            catch
            {
                throw new UserException("La direccion de correo debe ser valida");
            }
        }

        private bool IsNotEmptySurname(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                throw new UserException("El apellido de un usuario no debe ser vacio");
            }
            else
            {
                return true;
            }
        }

        private bool IsNotEmptyName(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                throw new UserException("El nombre de un usuario no debe ser vacio");
            }
            else
            {
                return true;
            }
        }

       
    }
}
