using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.EntityModels;
using TuBarrio.Repository;

namespace TuBarrio.BusinessLogic
{
    public class UserLogic : IUserLogic
    {
        private IUserRepository userRepository;

        public void AddUser(User user)
        {
            throw new NotImplementedException();
        }

        public void AddUserToFriends(string token, User friend)
        {
            throw new NotImplementedException();
        }

        public List<User> GetFriends(string token)
        {
            throw new NotImplementedException();
        }

        public User GetUserFromModel(UserModel user)
        {
            User newUser = new User(user.Name, user.Surname, user.Email, user.Phone, user.Password, new EncodedImage(user.ProfileImage));
            return newUser;
        }

        public bool IsUserAlreadyExisting(User user)
        {
            throw new NotImplementedException();
        }

        public void SetDeviceNotificationToken(string token, string deviceToken)
        {
            throw new NotImplementedException();
        }

        public void UpdateUser(User user)
        {
            userRepository.UpdateUser(user);
        }
    }
}
