using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TuBarrio.BusinessLogic;
using TuBarrio.Repository;
using TuBarrio.Data.Access;
using TuBarrio.Entities;
using TuBarrio.EntityModels;


namespace TuBarrio.BusinessLogic.Test
{
    
    [TestClass]
    public class UserLogicTest
    {
        private IUserLogic userLogic;
        private IUserRepository userRepository;
        private IAuthenticationLogic authLogic;
        private User userSebastian;
        private User userFederico;
        private string tokenFederico;

        [TestInitialize]
        public void SetUp()
        {
            TuBarrioDbContext context = new TuBarrioDbContext();
            userRepository = new UserRepository();
            userLogic = new UserLogic(userRepository);
            authLogic = new AuthenticationLogic(userRepository);
            userSebastian = new User("sebastian", "rodriguez", "seba@hotmail.com", "123", "123");
            userRepository.AddUser(userSebastian);
            userFederico = new User("federico", "engel", "federico@hotmail.com", "123", "123");
            userRepository.AddUser(userFederico);
           
        }

        [TestMethod]
        public void AddOneUserTest()
        {
            List<User> users = userRepository.GetAllUsers();
            Assert.IsTrue(users.Contains(userFederico));
        }

        [TestMethod]
        public void AddMultipleUserTest()
        {
            List<User> users = userRepository.GetAllUsers();
            int expectedUsersRegistered = 2;
            Assert.AreEqual(expectedUsersRegistered, users.Count);
        }

        [TestMethod]
        public void IsUserAlreadyExistingTrueTest()
        {
            Assert.IsTrue(userLogic.IsUserAlreadyExisting(userFederico));
        }

        [TestMethod]
        public void IsUserAlreadyExistingFalseTest()
        {
            User notExistingUser = new User("Juan", "Perez", "jp@gmail.com", "123", "123");
            Assert.IsFalse(userLogic.IsUserAlreadyExisting(notExistingUser));
        }

        [TestMethod]
        public void ModifyUserTest()
        {
            userFederico.Name = "Martin";
            userLogic.UpdateUser(userFederico);
            List<User> users = userRepository.GetAllUsers();
            User userModified = users[0];
            Assert.AreEqual("Martin", userModified.Name);
        }

        [TestMethod]
        public void GetUserFromModelTest()
        {
            UserModel model = new UserModel();
            model.Name = userSebastian.Name;
            model.Surname = userSebastian.Surname;
            model.Phone = userSebastian.Phone;
            model.Email = userSebastian.Email;
            model.ProfileImage = userSebastian.ProfileImage;

            User userFromModel = new User(model.Name, model.Surname, model.Email,"",model.Phone);
            User userResult = userLogic.GetUserFromModel(model);
            Assert.AreEqual(userFromModel, userResult);
        }

        [TestMethod]
        public void SetDeviceNotificationTest()
        {
            Guid deviceToken = new Guid();
            userLogic.SetDeviceNotificationToken(tokenFederico, deviceToken.ToString());
            User guille = authLogic.GetUserByEmail(userFederico.Email);
            Assert.AreEqual(deviceToken.ToString(), guille.DeviceNotificationToken);
        }


    }
}
