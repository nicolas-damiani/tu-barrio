using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace TuBarrio.Entities.Test
{
    
    [TestClass]
    public class EncodedImageTest
    {
        private EncodedImage image;

        [TestInitialize]
        public void SetUp()
        {
            image = new EncodedImage("image");
        }

        [TestMethod]
        public void EncodedImageConstructorTest()
        {
            Assert.AreEqual("image", image.Image);
        }

  

    }
}
