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
        void UpdateUser(User user);
        bool IsUserAlreadyExisting(User user);
        User GetUserFromModel(UserModel user);
        void SetDeviceNotificationToken(string token, string deviceToken);
        int GetCantPublications(User user);
        int GetCantFollowed(User user);
        int GetCantComments(User user);
    }
}
