using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TuBarrio.Exceptions
{
    public class PublicationException : Exception
    {
        public PublicationException(string message) : base(message)
        {

        }
    }

}
