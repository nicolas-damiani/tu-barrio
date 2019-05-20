using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.Entities
{
    public class EncodedImage
    {

        public int Id { get; set; }
        public string Image { get; set; }

        public EncodedImage() { }
        public EncodedImage(string image)
        {
            Image = image;
        }

    }
}
