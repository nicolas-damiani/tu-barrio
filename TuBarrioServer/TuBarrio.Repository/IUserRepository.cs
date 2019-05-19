using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;

namespace TuBarrio.Repository
{
    public interface IUserRepository
    {
        List<User> GetAllUsers();
        void AddUser(User user);
        string LogIn(string email, string password);
        User GetUserWithToken(string token);
        bool IsUserAlreadyExisting(User user);
        void UpdateUser(User user);
        User GetUserByEmail(string username);
        void SetDeviceNotificationToken(User user, string deviceToken);

    }
}
