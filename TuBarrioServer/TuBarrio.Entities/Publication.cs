using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.Entities
{
    public class Publication
    {
        public int Id;
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



        public Publication()
        {

        }

    }

}
