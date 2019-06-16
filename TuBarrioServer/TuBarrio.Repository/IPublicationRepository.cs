using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;

namespace TuBarrio.Repository
{
    public interface IPublicationRepository
    {
        List<Publication> GetAllPublications();
        List<Publication> GetAllPublicationsFromUser(User user);
        void AddPublication(Publication publicationToAdd);
        void RemovePublication(Publication publicationToRemove);
        Publication GetPublicationById(int id);
        void UpdatePublication(Publication publicationToUpdate, int id);
        void AddImageToPublication(string image, Publication publicationToUpdate,int id);
        void AddCommentToPublication(Comment newComment, Publication publicationToUpdate);
        void DeleteCommentFromPublication(int commentToDeleteId, int publicationToUpdateId);
        Comment GetCommentById(Publication publication, int id);
        List<Comment> GetCommentsFromPublication(Publication publication);
        List<Publication> GetFollowedPublications(User user);
        void FollowPublication(User user, Publication publication);
        void UnfollowPublication(User user, int publicationId);
    }
}
