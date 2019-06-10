using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace TuBarrio.Entities.Test
{
    
    [TestClass]
    public class ImageTagTestcs
    {
        private ImageTag tag;

        [TestInitialize]
        public void SetUp()
        {
            tag = new ImageTag("tag");
        }


        [TestMethod]
        public void ImageTagTextConstructorTest()
        {
            ImageTag secondTag = new ImageTag();
            secondTag.Text = "tag";
            Assert.AreEqual(secondTag.Text, tag.Text);
        }

        [TestMethod]
        public void ImageTagIdConstructorTest()
        {
            tag.Id = 1;
            Assert.AreEqual(1, tag.Id);
        }
    }
}
