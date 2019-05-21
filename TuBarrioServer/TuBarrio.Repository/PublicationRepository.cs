using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;
using TuBarrio.Entities;
using TuBarrio.Data.Access;

namespace TuBarrio.Repository
{
   public class PublicationRepository : IPublicationRepository
    {
        public List<Publication> GetAllPublications()
        {
            List<Publication> returnList = new List<Publication>();
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnList = context.Publications.ToList();
            }
            return returnList;
        }

        public void AddPublication(Publication publicationToAdd)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                context.Users.Attach(publicationToAdd.Author);
                context.Publications.Add(publicationToAdd);
                context.SaveChanges();
            }
        }

        public void RemovePublication(Publication publicationToRemove)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                publicationToRemove = context.Publications.Include(g => g.Author).Where(g => g.Id == publicationToRemove.Id).FirstOrDefault();
                foreach(var image in publicationToRemove.Images.ToList())
                {
                    publicationToRemove.Images.Remove(image);
                }
                context.Publications.Remove(publicationToRemove);
                context.SaveChanges();
            }
        }

        public Publication GetPublicationById(int id)
        {
            Publication publicationReturn = null;
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                publicationReturn = context.Publications.Include(p => p.PublicationImage).Include(p => p.Author).Where(p => p.Id == id).FirstOrDefault();
                return publicationReturn;
            }
        }

        public void UpdatePublication(Publication publicationToUpdate, int id)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                Publication olderPublication = context.Publications.Find(id);
                olderPublication.Title = publicationToUpdate.Title;
                olderPublication.Description = publicationToUpdate.Description;
                olderPublication.Longitude = publicationToUpdate.Longitude;
                olderPublication.Latitude = publicationToUpdate.Latitude;
                olderPublication.UpdatedOn = publicationToUpdate.UpdatedOn;
                context.SaveChanges();
            }
        }

        public void AddImageToPublication(List<EncodedImage> images, Publication publicationToUpdate)
        {
            using(TuBarrioDbContext context = new TuBarrioDbContext())
            {
                Publication publicationToAddImages = context.Publications.Include(p => p.Images).Where(p => p.Id == publicationToUpdate.Id).FirstOrDefault();
                foreach (EncodedImage i in images)
                {
                    publicationToAddImages.Images.Add(i);
                
                }
                context.SaveChanges();
            }
        }

        public List<Publication> GetAllPublicationsFromUser(User user)
        {
            List<Publication> returnList = new List<Publication>();
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnList = context.Publications.Include(p => p.PublicationImage).Include(p => p.Author).Where(p => p.Author.Id == user.Id).ToList();

            }
            return returnList;
        }

        public void AddCommentToPublication(Comment newComment, Publication publicationToUpdate)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                Publication toUpdate = context.Publications.Include(p => p.Comments).Where(u => u.Id == publicationToUpdate.Id).FirstOrDefault();
                toUpdate.Comments.Add(newComment);
                context.SaveChanges();
            }
        }

        public void DeleteCommentFromPublication(Comment commentToDelete, Publication publicationToUpdate)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                publicationToUpdate = context.Publications.Include(p => p.Comments).Where(u => u.Id == publicationToUpdate.Id).FirstOrDefault();
                publicationToUpdate.Comments.Remove(commentToDelete);
                context.SaveChanges();
            }
        }

        public Comment GetCommentById(Publication publicationToSearch,int id)
        {
            using(TuBarrioDbContext context = new TuBarrioDbContext())
            {
                Comment returnComment = new Comment();
                publicationToSearch = context.Publications.Include(p => p.Comments).Where(u => u.Id == publicationToSearch.Id).FirstOrDefault();
                foreach (var com in publicationToSearch.Comments.ToList())
                {
                    if(com.Id == id)
                    {
                        returnComment = com;
                    }
                }
                return returnComment;
            }
        }








    }
}
