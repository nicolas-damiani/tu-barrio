using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;

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
        void AddImageToPublication(List<EncodedImage> images, Publication publicationToUpdate, string token);
        void AddCommentToPublication(Comment newComment, Publication publicationToUpdate);
        void DeleteCommentFromPublication(Comment commentToDelete, Publication publicationToUpdate,string token);

    }
}
