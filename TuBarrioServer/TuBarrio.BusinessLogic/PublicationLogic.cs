using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.EntityModels;
using TuBarrio.Repository;

namespace TuBarrio.BusinessLogic
{
    public class PublicationLogic : IPublicationLogic
    {
        private IPublicationRepository publicationRepository;
        private IAuthenticationLogic authenticationLogic;

        public PublicationLogic(IPublicationRepository repository, IAuthenticationLogic authLogic)
        {
            publicationRepository = repository;
            authenticationLogic = authLogic;
        }

        public List<Publication> GetAllPublications()
        {
            return publicationRepository.GetAllPublications();
        }

        public void AddPublication(Publication publicationToAdd)
        {
            publicationRepository.AddPublication(publicationToAdd);
        }

        public void RemovePublication(int id, string token)
        {
            Publication publicationToRemove = publicationRepository.GetPublicationById(id);
            User author = authenticationLogic.GetUserWithToken(token);
            if (author.Equals(publicationToRemove.Author))
            {
                publicationRepository.RemovePublication(publicationToRemove);
            }
            else
            {
                throw new PublicationException("No tiene permiso para eliminar un evento");
            }

        }

        public void UpdatePublication(Publication publicationToUpdate, int id, string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            if (author.Equals(publicationToUpdate.Author))
            {
                publicationRepository.UpdatePublication(publicationToUpdate, id);
            }
            else
            {
                throw new PublicationException("No tiene permiso para eliminar un evento");
            }
        }

        public List<Publication> GetAllPublicationsFromUser(string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            return publicationRepository.GetAllPublicationsFromUser(author);
        }

        public void AddImageToPublication(List<EncodedImage> images, Publication publicationToUpdate, string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            if (author.Equals(publicationToUpdate.Author))
            {
                publicationRepository.AddImageToPublication(images, publicationToUpdate);
            }
            else
            {
                throw new PublicationException("No tiene permiso para eliminar un evento");
            }
        }

        public Publication GetPublicationById(int id, string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            Publication publicationToGet = publicationRepository.GetPublicationById(id);
            if (author.Equals(publicationToGet.Author))
            {
                return publicationToGet;
            }
            else
            {
                throw new PublicationException("No tiene permiso para eliminar un evento");
            }
        }





    }
}
