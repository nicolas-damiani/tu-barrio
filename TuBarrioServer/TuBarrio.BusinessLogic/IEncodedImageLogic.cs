using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TuBarrio.Entities;
using TuBarrio.EntityModels;

namespace TuBarrio.BusinessLogic
{
    public interface IEncodedImageLogic
    {
        EncodedImage getEncodedImageFromModel(EncodedImageModel model);
    }
}
