//package hw1;
//import algs4.WeightedQuickUnionUF;
    
public class Percolation {
    private int grid[];
    private int N;
    private WeightedQuickUnionUF model;//a data struture which can be used to determine whether 
    
    public Percolation(int N)               // create N-by-N grid, with all sites blocked
   {  
       if (N<=0)
           throw new IllegalArgumentException();
       
       this.N = N;
       this.grid = new int[N*N+2];// grid[i] is closed if its value is 0.
       this.grid[0] = 1; //a vritual scouce (0) open
       this.grid[N*N+1] = 1;//virtual sink (N*N+1) open
       this.model = new WeightedQuickUnionUF(N*N+2); //a grid with a vritual scouce (N*N) and virtual sink (N*N+1)
    
    }
   
   
   public void open(int i, int j) // i ,j are in [1,N], otherwise throw an exception
   {     //current operating site index is (i-1)*N+j
       //  System.out.println("get into here");
        //System.out.println(model);
       if (i<1 || i>N || j<1 || j>N)
         throw new IndexOutOfBoundsException();    
       if(this.grid[(i-1)*N+j] != 1)  
       {
           this.grid[(i-1)*N+j] = 1; //set the current room to be open (1)
           if (i==1)
           model.union((i-1)*N+j,0); 
           if (i==N)
               model.union((i-1)*N+j,N*N+1);
           
           if (i>1) // connect the top one
               if (isOpen(i-1,j)==true)
               model.union((i-2)*N+j,(i-1)*N+j);
           if (i<N) // connect the bottom one
               if (isOpen(i+1,j)==true)
               model.union(i*N+j,(i-1)*N+j);
                  
             
           if (j>1) //connect the left one
           {
               if (isOpen(i,j-1)==true)
               {  model.union((i-1)*N+j-1,(i-1)*N+j);}}
           if (j<N) //connect the right one
               if (isOpen(i,j+1)==true)
               model.union((i-1)*N+j+1,(i-1)*N+j);
           
           
       }
                  // open site (row i, column j) if it is not open already
   }
   
   public boolean isOpen(int i, int j) 
   {
     //  System.out.println("is Open get into here");
        if (i<1 || i>N || j<1 || j>N)
           throw new IndexOutOfBoundsException();
        
       if (grid[(i-1)*N+j]==1)          
           return true;
       else
           return false;
   }     

   //is site (row i, column j) open?
   
   public boolean isFull(int i, int j)  
   { 
       if (i<1 || i>N || j<1 || j>N)
           throw new IndexOutOfBoundsException();
//System.out.println("get into here"+this.model);

      return this.model.connected(0,(i-1)*N+j); 
   }   
   
// is site (row i, column j) full?
   
   
   public boolean percolates() 
   {
       return model.connected(0,N*N+1);
   }            // does the system percolate?

  /* public static void main(String[] args) // test client (optional)
   {
       In test = new In("heart25.txt");
       
       int input[] =test.readAllInts();
       // System.out.println(input.length);
     Percolation Perco = new Percolation(input[0]);
     for (int i=1;i<input.length;i=i+2)
     {

           Perco.open(input[i],input[i+1]); 
     }                        
     test.close();
    System.out.println(Perco.percolates());
}*/
   }  

