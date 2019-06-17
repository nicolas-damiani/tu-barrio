using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TuBarrio.Entities;
using TuBarrio.Repository;
using TuBarrio.Data.Access;
using TuBarrio.Exceptions;
using TuBarrio.EntityModels;




namespace TuBarrio.BusinessLogic.Test
{
    
    [TestClass]
    public class PublicationLogicTest
    {
        private IPublicationLogic publicationLogic;
        private IPublicationRepository publicationRepository;
        private Publication publicationTest;
        private IUserLogic userLogic;
        private IUserRepository userRepository;
        private IAuthenticationLogic authlogic;
        private User userSeba;
        private User author;
        public string sebaToken;
        public string authorToken;


        [TestInitialize]
        public void SetUp()
        {
            TuBarrioDbContext context = new TuBarrioDbContext();
            context.Clear();
            userRepository = new UserRepository();
            userLogic = new UserLogic(userRepository);
            publicationRepository = new PublicationRepository();
            authlogic = new AuthenticationLogic(userRepository);
            publicationLogic = new PublicationLogic(publicationRepository,authlogic);

            author = new User("nico", "damiani", "nico@dami.com", "", "099121212");
            userSeba = new User("seba", "rodri", "seba@rodi.com", "", "099233332");
            publicationTest = new Publication(author, new DateTime(2019, 3, 12), 0, "publi test", 34.2222, 54.3333, "Titulo", new DateTime(2019, 3, 12), "");
            userRepository.AddUser(author);
            userRepository.AddUser(userSeba);
            publicationLogic.AddPublication(publicationTest);
            //authorToken = authlogic.LogIn(author.Email);
           // sebaToken = authlogic.LogIn(userSeba.Email);
            publicationLogic.FollowPublication(userSeba, publicationTest.Id);
        }

        

        [TestMethod]
        public void AddOnePublicationTest()
        {
            List<Publication> publications = publicationLogic.GetAllPublications();
            Assert.IsTrue(publications.Contains(publicationTest));
        }

        [TestMethod]
        public void AddTwoPublicationsTest()
        {
            Publication publiTest2 = new Publication(userSeba, new DateTime(2019, 3, 12), 0, "publi test 2", 54.2222, 54.3333, "Titulo 2", new DateTime(2019, 3, 12), "");
            publicationLogic.AddPublication(publiTest2);
            List<Publication> publications = publicationLogic.GetAllPublications();
            int expectedPublicationsRegistered = 2;
            Assert.AreEqual(expectedPublicationsRegistered, publications.Count);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void ModifyPublicationTest()
        {
            publicationTest.Title = "Titulo nuevo";
            publicationLogic.UpdatePublication(publicationTest, publicationTest.Id, authorToken);
            List<Publication> publications = publicationLogic.GetAllPublications();
            Publication publiModified = publications[0];
            Assert.AreEqual("Titulo nuevo", publiModified.Title);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void UpdatePublicationNotCreatorTest()
        {
            publicationTest.Title = "Titulo nuevo";
            publicationLogic.UpdatePublication(publicationTest, publicationTest.Id, sebaToken);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void RemovePublicationTest()
        {
            publicationLogic.RemovePublication(publicationTest.Id, authorToken);
            List<Publication> publications = publicationLogic.GetAllPublications();
            Assert.IsFalse(publications.Contains(publicationTest));
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void RemovePublicationNotAuthorTest()
        {
            publicationLogic.RemovePublication(publicationTest.Id, sebaToken);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void AddSubscriberToPublicationTest()
        {   
            List<User> subscribers = publicationLogic.GetPublicationById(publicationTest.Id, authorToken).Subsrcibers;
            Assert.IsTrue(subscribers.Contains(userSeba));
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void AddSubscriberToPublicationAlreadyFollowTest()
        {
            publicationTest = publicationLogic.GetPublicationById(publicationTest.Id, authorToken);
            publicationLogic.FollowPublication(userSeba, publicationTest.Id);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void RemoveSubscriberFromPublicationTest()
        {
            publicationLogic.UnfollowPublication(userSeba, publicationTest.Id);
            List<User> subscribers = publicationLogic.GetPublicationById(publicationTest.Id, authorToken).Subsrcibers;
            Assert.IsFalse(subscribers.Contains(userSeba));
        }

        [TestMethod]

        public void GetPublicationFromModelTest()
        {
            PublicationModel model = new PublicationModel();
            model.CreatedOn = publicationTest.CreatedOn;
            model.Title = publicationTest.Title;
            model.Latitude = publicationTest.Latitude;
            model.Longitude = publicationTest.Longitude;
            model.PublicationImage = publicationTest.PublicationImage;
            Publication publiResult = publicationLogic.GetPublicationFromModel(model);
            Assert.AreEqual(publicationTest.Title, publiResult.Title);
            
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void GetAllFromUserTest()
        {
            List<Publication> userPublications = publicationLogic.GetAllPublicationsFromUser(sebaToken);
            Assert.IsTrue(userPublications.Contains(publicationTest));
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCredentialException))]
        public void GetPublicationByIdTest()
        {
            Publication p = publicationLogic.GetPublicationById(publicationTest.Id, authorToken);
            Assert.AreEqual(publicationTest, p);

        }
        
        [TestMethod]
        public void EqualsComparePublicationToNullTest()
        {
            Assert.AreNotEqual(publicationTest, null);
        }

        [TestMethod]
        public void EqualsComparePublicationToAnotherObjectTest()
        {
            Assert.AreNotEqual(publicationTest, userSeba);
        }

        [TestMethod]
        public void EqualsComparePublicationToNullPublicationTest()
        {
            Publication newPublication = null;
            Assert.AreNotEqual(publicationTest, newPublication);
        }

        









    }
}
