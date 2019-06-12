using System;
using System.Collections.Generic;
using System.Text;
using System.Data.Entity;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.Data.Access;
using System.Linq;

namespace TuBarrio.Repository
{
   public class UserRepository : IUserRepository
    {
        public List<User> GetAllUsers()
        {
            List<User> returnList = new List<User>();
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnList = context.Users.ToList();
            }
            return returnList;

        }

        public void AddUser(User user)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                context.Users.Add(user);
                context.SaveChanges();
            }
        }


        public User GetUserWithToken(string token)
        {
            User returnUser = null;
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnUser = context.Users.Where(u => u.Token == token).FirstOrDefault();
                return returnUser;
            }
        }

        public string LogIn(string email, string password)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                User user = context.Users.Where(u => u.Email == email).FirstOrDefault();
                if (user != null)
                {
                    user.Token = Guid.NewGuid().ToString();
                    context.SaveChanges();
                    return user.Token;
                }
            }
            return "";
        }

        public User GetUserByEmail(string email)
        {
            User returnUser = null;
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnUser = context.Users.Where(u => u.Email == email).FirstOrDefault();
                return returnUser;
            }
        }

        public bool IsUserAlreadyExisting(User user)
        {
            bool returnValue = false;
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                User userToSearch = context.Users.Find(user.Email);
                returnValue = (userToSearch != null);
            }
            return returnValue;
        }

        public void UpdateUser(User user)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                User oldUser = context.Users.Find(user.Email);
                oldUser.Name = user.Name;
                oldUser.Surname = user.Surname;
                oldUser.Phone = user.Phone;
                oldUser.ProfileImage = user.ProfileImage;
                context.SaveChanges();
            }
        }

        public void SetDeviceNotificationToken(User user, string deviceToken)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                User oldUser = context.Users.Find(user.Email);
                oldUser.DeviceNotificationToken = deviceToken;
                context.SaveChanges();
            }
        }



    }
}
