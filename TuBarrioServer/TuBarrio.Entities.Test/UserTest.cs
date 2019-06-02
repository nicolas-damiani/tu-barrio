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
        private EncodedImage image;

        [TestInitialize]
        public void SetUp()
        {
            image = new EncodedImage("asd");
            user = new User("Felipe", "Diaz", "felipe@hotmail.com", "myPass", image);
        }

        [TestMethod]
        public void UserConstructorNameTest()
        {
            Assert.AreEqual(user.Name, "Felipe");
        }









    }

}
