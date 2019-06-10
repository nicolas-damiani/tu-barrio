using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TuBarrio.Entities;
using TuBarrio.BusinessLogic;
using TuBarrio.EntityModels;

namespace TuBarrio.BusinessLogic.Test
{
    
    [TestClass]
    public class EncodedImageLogicTest
    {
        private IEncodedImageLogic imagesLogic;
        [TestInitialize]
        public void SetUp()
        {
            imagesLogic = new EncodedImageLogic();
        }

        [TestMethod]
        public void GetEncodedImageFromModelTest()
        {
            EncodedImageModel model = new EncodedImageModel();
            model.Image = "Image";
           
            EncodedImage imageResult = imagesLogic.getEncodedImageFromModel(model);
            Assert.AreEqual(model.Image, imageResult.Image);
        }
    }
}
