using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace TuBarrio.Entities.Test
{
    
    [TestClass]
    public class CommentTest
    {

        private Comment comment;


        [TestInitialize]
        public void SetUp()
        {
            comment = new Comment("texto", new DateTime(2019, 3, 12));
        }


        [TestMethod]
        public void CommentConstructorNameNotEqualTest()
        {
            Assert.AreNotEqual(comment.Text, "hola");
        }

        [TestMethod]
        public void CommentConstructorNameTest()
        {
            Assert.AreEqual(comment.Text, "texto");
        }


        [TestMethod]
        public void CommentConstructorDateTest()
        {
            Assert.AreEqual(comment.CreatedOn, new DateTime(2019, 3, 12));
        }

        [TestMethod]
        public void CommentConstructorDateNotEqualTest()
        {
            Assert.AreNotEqual(comment.CreatedOn, new DateTime(2018, 4, 12));
        }

       

        [TestMethod]
        public void CommentConstructorAreDifferentTest()
        {
            Comment samecomment = new Comment("texto mal", new DateTime(2019, 3, 12));
            Assert.AreNotEqual(comment, samecomment);
        }




    }
}
