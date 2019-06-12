﻿using System;
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
        void DeleteCommentFromPublication(Comment commentToDelete, Publication publicationToUpdate);
        Comment GetCommentById(Publication publication, int id);
    }
}
