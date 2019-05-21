using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.EntityModels
{
    public class PublicationModel
    {
        public string Title { get; set; }
        public string Description { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public int PublicationType { get; set; }
        public DateTime UpdatedOn { get; set; }
        public DateTime CreatedOn { get; set; }
        public int Deleted { get; set; }
        public List<UserModel> Subsrcibers { get; set; }
        public UserModel Author { get; set; }
        public List<CommentModel> Comments { get; set; }
        public List<EncodedImageModel> Images { get; set; }


        public PublicationModel()
        {
            Title = "";
            Description = "";
            Longitude = 0;
            Latitude = 0;
            PublicationType = 0;
            UpdatedOn = DateTime.Now;
            CreatedOn = DateTime.Now;
            Deleted = 0;
            Subsrcibers = new List<UserModel>();
            Author = new UserModel();
            Comments = new List<CommentModel>();
            Images = new List<EncodedImageModel>();


        }


    }
}
