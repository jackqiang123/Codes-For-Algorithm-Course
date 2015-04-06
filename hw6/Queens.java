class Queens
{
    private int N; // number of queues;
    //
   
    public Queens(int N){this.N=N;}
    private boolean isconsis(int q[], int n)
    {
        for (int i = 0 ; i<n ; i++)
        {
            if (q[i] == q[n]) return false;
            if (q[i] - q[n] == i-n) return false;
            if (q[i] - q[n] == n-i) return false;
           
        }
         return true;
    }
    public void getsolution()
    {
        int []q = new int[N];
        getsolution(q,0);
    }
    
    private void  printsolution(int []q)
    {
        for (int i = 0; i< N; i++)
        {
            for (int j = 0; j< N ;j++)
            {    if (q[i]!=j)
                  StdOut.print("x");
                else  StdOut.print("Q");
            
                 
                    
            } 
            System.out.println();
        }
        System.out.println();
    }
    
    
    public void getsolution(int []q, int n) // n means how many have been put
    {
    
      if (n<N) 
      {
         // StdOut.println("current n is "+ n);
          for (int i=0;i<N;i++)
          {
              q[n]=i;
              if (isconsis(q,n))
                  getsolution(q, n+1);
          }
      }
      else 
      {
          printsolution(q);
      }
       
    }
    public static void main(String[] arg)
    {
        Queens myq = new Queens(4);
        myq.getsolution();
    }
}