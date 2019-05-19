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
        public List<ImageTag> Tags { get; set; }

        public EncodedImage() { }
        public EncodedImage(string image, List<String> tags)
        {
            Image = image;
            Tags = new List<ImageTag>();
            foreach (var tag in tags)
            {
                ImageTag imageTag = new ImageTag(tag);
                Tags.Add(imageTag);
            }
        }

    }
}
