using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;
using TuBarrio.Entities;

namespace TuBarrio.Data.Access
{
  public class TuBarrioDbContext : DbContext
    {
        public TuBarrioDbContext() : base("TuBarrioDataBase")
        { }

        public DbSet<User> Users { get; set; }
        public DbSet<Publication> Publications { get; set; }
        public DbSet<Comment> Comments { get; set; }
        public DbSet<File> Files { get; set; }

      



    }


}

