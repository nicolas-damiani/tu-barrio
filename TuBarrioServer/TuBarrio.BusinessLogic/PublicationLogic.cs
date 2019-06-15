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
            publicationRepository.AddCommentToPublication(newComment, publicationToUpdate);   
        }

        public void DeleteCommentFromPublication(int commentToDeleteId, int publicationToUpdateId, string token)
        {
            User author = authenticationLogic.GetUserWithToken(token);
            Publication publicationToUpdate = this.GetPublicationById(publicationToUpdateId, token);
            if (author.Equals(publicationToUpdate.Author))
            {
                publicationRepository.DeleteCommentFromPublication(commentToDeleteId, publicationToUpdateId);
            }
            else
            {
                throw new PublicationException("No tiene permiso para eliminar un comentario");
            }
        }

        public Publication GetPublicationFromModel(PublicationModel model)
        {
            User author = authenticationLogic.GetUserByEmail(model.AuthorEmail);
            return new Publication(author, model.CreatedOn, model.Deleted, model.Description, model.Latitude, model.Longitude, model.Title, model.UpdatedOn, model.PublicationImage);
        }

    

        public Comment GetCommentFromModel(CommentModel model)
        {
            Comment newComment = new Comment(model.Text, model.CreatedOn);
            return newComment;
        }

        public List<Comment> GetCommentsFromPublication(Publication publication)
        {
            return publicationRepository.GetCommentsFromPublication(publication);
        }

        public List<Publication> GetFollowedPublications(User user)
        {
            return publicationRepository.GetFollowedPublications(user);
        }

        public void FollowPublication(User user, int publicationId)
        {
            Publication publicationToFollow = publicationRepository.GetPublicationById(publicationId);
            publicationRepository.FollowPublication(user, publicationToFollow);
        }

        public void UnfollowPublication(User user, int publicationId)
        {
            publicationRepository.UnfollowPublication(user, publicationId);
        }
    }
}
