using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.EntityModels;

namespace TuBarrio.BusinessLogic
{
    public class EncodedImageLogic : IEncodedImageLogic
    {
        public EncodedImage getEncodedImageFromModel(EncodedImageModel model)
        {
            return new EncodedImage(model.Image);
        }
    }
}
