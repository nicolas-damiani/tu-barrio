using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TuBarrio.Entities;
using TuBarrio.Exceptions;

namespace TuBarrio.Entities.Test
{
    [TestClass]
    public class UserTest
    {
        private User user;
        
        [TestInitialize]
        public void SetUp()
        {
            user = new User("Felipe", "Diaz", "felipe@hotmail.com","token","099112233" );
        }

        [TestMethod]
        public void UserConstructorNameTest()
        {
            Assert.AreEqual(user.Name, "Felipe");
        }

        [TestMethod]
        public void UserConstructorSurnameTest()
        {
            Assert.AreEqual(user.Surname, "Diaz");
        }

        [TestMethod]
        public void UserConstructorUserEmailTest()
        {
            Assert.AreEqual(user.Email, "felipe@hotmail.com");
        }

        [TestMethod]
        public void UserConstructorPhoneTest()
        {
            Assert.AreEqual(user.Phone, "099112233");
        }

        [TestMethod]
        [ExpectedException(typeof(UserException))]
        public void UserSetNameInvalidTest()
        {
            user.Name = "";
        }

        [TestMethod]
        [ExpectedException(typeof(UserException))]
        public void UserSetSurnameInvalidTest()
        {
            user.Surname = "";
        }

        [TestMethod]
        [ExpectedException(typeof(UserException))]
        public void UserSetUserEmailInvalidNoFirstPartTest()
        {
            user.Email = "@gmail.com";
        }

        [TestMethod]
        [ExpectedException(typeof(UserException))]
        public void UserSetUserEmailInvalidNoSecondPartTest()
        {
            user.Email = "felipe@.com";
        }

        [TestMethod]
        [ExpectedException(typeof(UserException))]
        public void UserSetUserEmailInvalidNoAtTest()
        {
            user.Email = "felipegmail.com";
        }

        
        

        [TestMethod]
        public void UserConstructorTokenTest()
        {
            Assert.AreEqual(user.DeviceNotificationToken, "");
        }

      

        [TestMethod]
        public void UserEqualsWithAnotherObjectTest()
        {
            string text = "This is a test string.";
            Assert.IsFalse(user.Equals(text));
        }

        [TestMethod]
        public void UserNotEqualsWithNullTest()
        {
            User nullUser = null;
            Assert.IsFalse(user.Equals(nullUser));
        }

        [TestMethod]
        public void UserEqualsTest()
        {
            User sameUser = new User("Felipe", "Diaz", "felipe@hotmail.com","token","099112233");
            Assert.AreEqual(user, sameUser);
        }

        [TestMethod]
        public void UserNotEqualsTest()
        {
            User differentUser = new User("Felipe", "Diaz", "guille@hotmail.com", "token", "099112233");
            Assert.AreNotEqual(user, differentUser);
        }





    }

}
