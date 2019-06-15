using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.Entities
{
    public class Comment
    {
        public int Id { get; set; }
        public string Text { get; set; }
        public DateTime CreatedOn { get; set; }
        public User Creator { get; set; }


        public Comment()
        {
            Creator = null;
        }

        public Comment(string text, DateTime createdOn)
        {
            Creator = null;
            Text = text;
            CreatedOn = createdOn;
        }
    }
}
