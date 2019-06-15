using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.EntityModels
{
   public class StatisticModel
    {
        public int CantPublications { get; set; }
        public int CantFollowedPublicatios { get; set; }
        public int CantComments { get; set; }

        public StatisticModel(int publications, int followed, int comments)
        {
            CantComments = comments;
            CantFollowedPublicatios = followed;
            CantPublications = publications;
        }

    }
}
