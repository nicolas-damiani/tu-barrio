﻿using System;
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

        public User GetUserFromModel(UserModel user)
        {
            User newUser = new User(user.Name, user.Surname, user.Email, user.Password, new EncodedImage(user.ProfileImage));
            return newUser;
        }

        public bool IsUserAlreadyExisting(User user)
        {
            return userRepository.IsUserAlreadyExisting(user);
        }

        public void SetDeviceNotificationToken(string token, string deviceToken)
        {
            User user = userRepository.GetUserWithToken(token);
            userRepository.SetDeviceNotificationToken(user, deviceToken);
        }

        public void UpdateUser(User user)
        {
            userRepository.UpdateUser(user);
        }
    }
}
