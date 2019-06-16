using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;

namespace TuBarrio.EntityModels
{
    public class PublicationModel
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
        public UserModel Creator { get; set; }
        public string PublicationImage { get; set; }


        public PublicationModel()
        {
            Id = 0;
            Title = "";
            Description = "";
            Longitude = 0;
            Latitude = 0;
            PublicationType = 0;
            UpdatedOn = DateTime.Now;
            CreatedOn = DateTime.Now;
            Deleted = 0;
            Creator = null;
            PublicationImage = "";
        }


        public PublicationModel(Publication publication)
        {
            Id = publication.Id;
            Title = publication.Title;
            Description = publication.Description;
            Longitude = publication.Longitude;
            Latitude = publication.Latitude;
            PublicationType = publication.PublicationType;
            UpdatedOn = publication.UpdatedOn;
            CreatedOn = publication.CreatedOn;
            Deleted = publication.Deleted;
            Creator = new UserModel(publication.Author);
            PublicationImage = publication.PublicationImage;
        }
    }
}
