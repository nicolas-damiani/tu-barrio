using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.EntityModels
{
    public class CommentModel
    {
        public string Text { get; set; }
        public DateTime CreatedOn { get; set; }
        public UserModel Creator { get; set; }

        public CommentModel()
        {
            Text = "";
            CreatedOn = DateTime.Now;
            Creator = new UserModel();
        }

    }
}
