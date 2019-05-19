using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.Data.Access;

namespace TuBarrio.Repository
{
   public class UserRepository : IUserRepository
    {
        public List<User> GetAllUsers()
        {
            List<User> returnList = new List<User>();
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnList = context.Users;
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


    }
}
