using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TuBarrio.Data.Access;
using TuBarrio.Entities;
using TuBarrio.Repository;
using TuBarrio.Exceptions;



namespace TuBarrio.BusinessLogic.Test
{
    
    [TestClass]
    public class AuthenticationLogicTest
    {

        private IUserLogic userLogic;
        private IAuthenticationLogic authenticationLogic;
        private User user;

        [TestInitialize]
        public void SetUp()
        {
            TuBarrioDbContext context = new TuBarrioDbContext();
            context.Clear();
            IUserRepository userRepository = new UserRepository();
            userLogic = new UserLogic(userRepository);
            authenticationLogic = new AuthenticationLogic(userRepository);
            user = new User("Sebas", "Rod", "sebrod@hotmail.com", "099", "095123123","");
            userRepository.AddUser(user);
        }

        [TestMethod]
     
        public void LogInCorrectTest()
        {
            string tokenRecieved = authenticationLogic.LogIn(user.Email);
            Assert.IsNotNull(tokenRecieved);
        }

        [TestMethod]
        
        public void GetUserWithTokenCorrectTest()
        {
            string tokenRecieved = authenticationLogic.LogIn(user.Email);
            User userRecieved = authenticationLogic.GetUserWithToken(tokenRecieved);
            Assert.AreEqual(user, userRecieved);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void GetUserWithTokenIncorrectTest()
        {
            User userRecieved = authenticationLogic.GetUserWithToken("invalid token");
        }

        [TestMethod]
        public void GetUserByUsernameTest()
        {
            User userRecieved = authenticationLogic.GetUserByEmail(user.Email);
            Assert.AreEqual(user, userRecieved);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void GetUserByUsernameIncorrectTest()
        {
            User userRecieved = authenticationLogic.GetUserByEmail("invalid email");
        }

        [TestMethod]
     
        public void HandleGoogleSignInRegisteredUserTest()
        {
            string token = authenticationLogic.HandleGoogleSignIn(user.Email, user.Name, user.Surname,"");
            User userReceived = authenticationLogic.GetUserByEmail(user.Email);
            Assert.AreEqual(token, userReceived.Token);
        }

        [TestMethod]
        
        public void HandleGoogleSignInNotRegisteredUserTest()
        {
            User newUser = new User("Sebastian", "Rodriguez", "srod95@hotmail.com", "123", "123","");
            string token = authenticationLogic.HandleGoogleSignIn(newUser.Email, newUser.Name, newUser.Surname,"");
            User userReceived = authenticationLogic.GetUserWithToken(token);
            Assert.AreEqual(newUser.Email, userReceived.Email);
        }

    }
}
