//import .Percolation;

public class PercolationStats {
    
   private int T;//number of experiments
   private double x[]; //fraction
   private int opensite[];//open site number till full
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
       if (N<=0 || T<=0)
           throw new IllegalArgumentException();
       
      this.T = T; 
      this.x = new double [T];
      this.opensite = new int[T];
      for (int i = 0; i<this.T; i++)
      {   
          Percolation Pecr = new Percolation(N);
          while (Pecr.percolates()==false)
          {
              
            //  Random rownum = new Random();
              int row = StdRandom.uniform(N)+1;
             // Random colnum = new Random();
              int col = StdRandom.uniform(N)+1;
              if (Pecr.isOpen(row,col)==false)
              { 
                  Pecr.open(row,col);
                  opensite[i]=opensite[i]+1;
              }
              
          }
          this.x[i]=(double)this.opensite[i]/(N*N);
         
      }
   
      
   }
   
   public double mean()                      // sample mean of percolation threshold
   {
       
       return StdStats.mean(x);
   }
   
   public double stddev()                    // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(x);
   
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   { return this.mean()-1.96*this.stddev()/Math.sqrt(T);}
   public double confidenceHi()              // high endpoint of 95% confidence interval
 { return this.mean()+1.96*this.stddev()/Math.sqrt(T);}
   
   
   public static void main(String[] args)  {
       int N;
       int T;
         N= Integer.parseInt(args[0]);
         T= Integer.parseInt(args[1]);
   PercolationStats test = new PercolationStats(N,T);
   System.out.println("mean                    = "+test.mean());
    System.out.println("stddev                  = "+test.stddev());
     System.out.println("95% confidence interval = "+test.confidenceLo()+", "+test.confidenceHi()) ;
   }  // test client (described below)
}