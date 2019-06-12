using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.EntityModels;
using TuBarrio.Repository;
using TuBarrio.Exceptions;

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
                throw new PublicationException("No tiene permiso para eliminar una publicacion");
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
                throw new PublicationException("No tiene permiso para editar una publicacion");
            }
        }

        public List<Publication> GetAllPublicationsFromUser(string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            return publicationRepository.GetAllPublicationsFromUser(author);
        }

        public void AddImageToPublication(string image, Publication publicationToUpdate,int id,string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            if (author.Equals(publicationToUpdate.Author))
            {
                publicationRepository.AddImageToPublication(image, publicationToUpdate,id);
            }
            else
            {
                throw new PublicationException("No tiene permiso para agregar una imagen");
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
                throw new PublicationException("No tiene permiso para llamar a la publicacion");
            }
        }

        public void AddCommentToPublication(Comment newComment, Publication publicationToUpdate)
        {
            Publication publicationToAddComment = publicationRepository.GetPublicationById(publicationToUpdate.Id);
            publicationToAddComment.Comments.Add(newComment);
        }

        public void DeleteCommentFromPublication(Comment commentToDelete, Publication publicationToUpdate, string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            if (author.Equals(publicationToUpdate.Author))
            {
                publicationRepository.DeleteCommentFromPublication(commentToDelete, publicationToUpdate);
            }
            else
            {
                throw new PublicationException("No tiene permiso para eliminar un comentario");
            }
        }

        public Publication GetPublicationFromModel(PublicationModel model)
        {
            User author = authenticationLogic.GetUserByEmail(model.AuthorEmail);
            return new Publication(author, model.CreatedOn, model.Deleted, model.Description, model.Latitude, model.Longitude, model.Title, model.UpdatedOn);
        }

        public void DeleteCommentFromPublication(int id, Publication publicationToUpdate, string token)
        {
            throw new NotImplementedException();
        }

        public Comment GetCommentFromModel(CommentModel model)
        {
            throw new NotImplementedException();
        }
    }
}
