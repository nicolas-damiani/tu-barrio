using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.Entities
{
    public class Publication
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public int PublicationType { get; set; }
        public DateTime UpdatedOn { get; set; }
        public DateTime CreatedOn { get; set; }
        public int Deleted { get; set; }
        public List<User> Subsrcibers { get; set; }
        public User Author { get; set; }
        public List<Comment> Comments { get; set; }
        public string PublicationImage { get; set; }


        public Publication(User author,DateTime createdOn, int deleted, string description, double latitude, double longitude, string title, DateTime updatedOn, string publicationImage)
        {
            Author = author;
            CreatedOn = createdOn;
            Deleted = deleted;
            Description = description;
            Latitude = latitude;
            Longitude = longitude;
            Title = title;
            UpdatedOn = updatedOn;
            PublicationImage = publicationImage;


        }

        public Publication() {
            Author = null;
            Subsrcibers = new List<User>();
            Comments = new List<Comment>();
        }
    }

}

    

   
