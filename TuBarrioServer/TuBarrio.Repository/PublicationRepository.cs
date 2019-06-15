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
                returnList = context.Publications.Include(r => r.Author).ToList();
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
                context.Publications.Remove(publicationToRemove);
                context.SaveChanges();
            }
        }

        public Publication GetPublicationById(int id)
        {
            Publication publicationReturn = null;
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                publicationReturn = context.Publications.Include(p => p.Author).Where(p => p.Id == id).FirstOrDefault();
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

        public void AddImageToPublication(string image, Publication publicationToUpdate, int id)
        {
            using(TuBarrioDbContext context = new TuBarrioDbContext())
            {
                Publication olderPublication = context.Publications.Find(id);
                olderPublication.PublicationImage = publicationToUpdate.PublicationImage;
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


        public List<Comment> GetCommentsFromPublication(Publication publication)
        {
            List<Comment> returnList = new List<Comment>();
            List<Comment> comments = new List<Comment>();
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnList = context.Publications.Include(p => p.Comments).Where(p => p.Id == publication.Id).FirstOrDefault().Comments;
                foreach (Comment comment in returnList)
                {
                    Comment comment2 = context.Comments.Where(c => c.Id == comment.Id).Include(c => c.Creator).FirstOrDefault();
                    comments.Add(comment2);
                }
            }
            return comments;
        }


        public List<Publication> GetFollowedPublications(User user)
        {
            List<Publication> returnList = new List<Publication>();
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                returnList = context.Publications.Include(p => p.Subsrcibers.FindAll(s => s.Equals(user))).ToList();
            }
            return returnList;
        }


        public void AddCommentToPublication(Comment newComment, Publication publicationToUpdate)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                User user =  context.Users.Where(u => u.Email == newComment.Creator.Email).FirstOrDefault();
                Publication toUpdate = context.Publications.Include(p => p.Author).Include(p => p.Comments).Where(p => p.Id == publicationToUpdate.Id).FirstOrDefault();
                context.Users.Attach(user);
                context.Entry(user).State = EntityState.Modified;
                newComment.Creator = user;
                newComment =  context.Comments.Add(newComment);

               

                toUpdate.Comments.Add(newComment);
                context.SaveChanges();
            }
        }

        public void DeleteCommentFromPublication(Comment commentToDelete, Publication publicationToUpdate)
        {
            using (TuBarrioDbContext context = new TuBarrioDbContext())
            {
                publicationToUpdate = context.Publications.Include(p => p.Comments).Where(p => p.Id == publicationToUpdate.Id).FirstOrDefault();
                publicationToUpdate.Comments.Remove(commentToDelete);
                context.SaveChanges();
            }
        }

        public Comment GetCommentById(Publication publicationToSearch,int id)
        {
            using(TuBarrioDbContext context = new TuBarrioDbContext())
            {
                Comment returnComment = new Comment();
                publicationToSearch = context.Publications.Include(p => p.Comments).Where(p => p.Id == publicationToSearch.Id).FirstOrDefault();
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
