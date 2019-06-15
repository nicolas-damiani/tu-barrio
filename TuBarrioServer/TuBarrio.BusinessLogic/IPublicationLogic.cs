using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.EntityModels;


namespace TuBarrio.BusinessLogic
{
    public interface IPublicationLogic
    {
        List<Publication> GetAllPublications();
        List<Publication> GetAllPublicationsFromUser(string token);
        void AddPublication(Publication publicationToAdd);
        void RemovePublication(int id, string token);
        Publication GetPublicationById(int id,string token);
        void UpdatePublication(Publication publicationToUpdate, int id, string token);
        void AddImageToPublication(string image, Publication publicationToUpdate,int id,string token);
        void AddCommentToPublication(Comment newComment, Publication publicationToUpdate);
        void DeleteCommentFromPublication(Comment commentToDelete, Publication publicationToUpdate,string token);
        Publication GetPublicationFromModel(PublicationModel model);
        Comment GetCommentFromModel(CommentModel model);
        List<Comment> GetCommentsFromPublication(Publication publicationToGetComments);
        List<Publication> GetFollowedPublications(User user);
        void FollowPublication(User user, int publicationId);
        void UnfollowPublication(User user, int publicationId);
    }
}
