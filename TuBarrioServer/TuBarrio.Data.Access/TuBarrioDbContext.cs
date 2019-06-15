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
        public DbSet<EncodedImage> EncodedImages { get; set; }


        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Publication>().HasMany(m => m.Comments).WithMany();
            modelBuilder.Entity<Publication>().HasMany(m => m.Subsrcibers).WithMany();
        }


    }


}

