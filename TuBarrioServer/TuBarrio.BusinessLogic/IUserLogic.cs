using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.EntityModels;


namespace TuBarrio.BusinessLogic
{
    public interface IUserLogic
    {
        void AddUser(User user);
        void UpdateUser(User user);
        void AddUserToFriends(string token, User friend);
        bool IsUserAlreadyExisting(User user);
        User GetUserFromModel(UserModel user);
        void SetDeviceNotificationToken(string token, string deviceToken);
    }
}
