import java.awt.Color;
public class SeamCarver {
   private Picture mypicture;
   private double[] distTo;
   private int[] edgeTo;
   
   public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
   {
       mypicture = picture;     
   }
   
   public Picture picture()                          // current picture
   {
       return mypicture;
   }
       
   public int width()                            // width of current picture
   {
       return this.picture().width();
   }
   
   public int height()                           // height of current picture
   {
       return this.picture().height();
   }
       
   public double energy(int x, int y)               // energy of pixel at column x and row y
   {
       // first to look at whether the (x,y) are in the boundary
       int width = this.width();
       int height = this.height();
       if (x<0 || y<0 || x>width-1 || y>height-1)
           throw new IndexOutOfBoundsException();
       
       if (x==0||y==0||x==width-1||y==height-1)
           return 195075;
       int deltax = deltax(x,y);
       int deltay = deltay(x,y);
       return deltax+deltay;
   }
   
   private int deltax(int x,int y)
   {
       Color leftpixel = mypicture.get(x-1,y);
       Color rightpixel = mypicture.get(x+1,y);
       int diffRed = rightpixel.getRed()-leftpixel.getRed();   
       int diffGreen = rightpixel.getGreen()-leftpixel.getGreen();
       int diffBlue = rightpixel.getBlue()-leftpixel.getBlue();
       return diffRed*diffRed+diffGreen*diffGreen+diffBlue*diffBlue;
   }
   
   private int deltay(int x,int y)
   {
       Color leftpixel = mypicture.get(x,y-1);
       Color rightpixel = mypicture.get(x,y+1);
       int diffRed = rightpixel.getRed()-leftpixel.getRed();
       int diffGreen = rightpixel.getGreen()-leftpixel.getGreen();
       int diffBlue = rightpixel.getBlue()-leftpixel.getBlue();
       return diffRed*diffRed+diffGreen*diffGreen+diffBlue*diffBlue;
   }
   
   //public void energymaxtrix() //initialized the energy matrix
   //{
          
       
   //}
   
   
   private double[][] transpose(double [][]matrix) // return a trans matrix of the energy
   {
       int m = matrix.length;
       int n = matrix[0].length;

      double[][] trasposedMatrix = new double[n][m];

    for(int x = 0; x < n; x++)
    {
        for(int y = 0; y < m; y++)
        {
            trasposedMatrix[x][y] = matrix[y][x];
        }
    }

    return trasposedMatrix;
   }
   

   public int[] findVerticalSeam()                 // sequence of indices for vertical seam
   {
       //generate the energy matrix, and use it as arguments
       int width = this.width();
       int height = this.height();
       double [][] energy  = new double[height][width];
       for (int j=0;j<height;j++)   
         for (int i=0;i<width;i++)
           energy[j][i]=energy(i,j);       
       return findVpath(energy);
   }
   
   
   private int[] findVpath(double [][]energy)
   {
       int width = energy[0].length;
       int height = energy.length;
       distTo = new double [width*height+2];
       edgeTo = new int [width*height+2];  
       for (int v= 0; v<width*height+2;v++)
           distTo[v] = Double.POSITIVE_INFINITY;
       distTo[0] = 0.0; // node 0 is the souce, while node width*height+1 is the destnation.
       //  disTo[width*height+1] = 0.0; // the last node is with w
       // further, for node at pixel (i,j) its mapping index is 
       // the topological order is just the order from 0 to end
       for (int v = 0; v<width*height+1;v++)
       {
           int x = x(v,width);
           int y = y(v,width);
           // check whether the current one is the boundary
           if (v==0) // the firstlines node
           {
              
               for (int w=1; w<=width; w++)
               {// StdOut.println();
                  // StdOut.println("in the first line ("+x+","+y+")"); 
                   relax(v,w,energy);
               }
           }
           else if (y==height-1) // if the last node in the matrix, we only relax to the dest.
           { //StdOut.println("current in finishing the lastline to destination");
               if (distTo[width*height+1]>distTo[v])// the distance counts the current node's energy
               {
                   distTo[width*height+1] = distTo[v];
                   edgeTo[width*height+1] = v;
               }
           }         
           else if (x==0)// if leftmost node            
           {
              relax(v,id(x,y+1,width),energy);   
              relax(v,id(x+1,y+1,width),energy); 
           }
           else if (x==width-1)// if rightmost node 
           {
            relax(v,id(x-1,y+1,width),energy);   
            relax(v,id(x,y+1,width),energy); 
           }
           else 
           {
              relax(v,id(x-1,y+1,width),energy);   
              relax(v,id(x,y+1,width),energy); 
              relax(v,id(x+1,y+1,width),energy); 
           }
               
       }   
       // next we construct the shortest path from souce to destination.
       int [] path = new int [height];
       int [] index = new int [height];
       int postion = width*height+1; 
       for (int i=height-1; i>=0; i--)
       {
           path[i] = edgeTo[postion];
           postion = path[i];
           // test the invserve order shortest path
        //   StdOut.println(postion+","+i);
           index[i] = x(path[i],width);
       }
       
       return index;      
   }
   



   private void relax(int v, int w, double[][] energy) // conduct a similiar operation, to update the information in w.
   {    int width = energy[0].length;
       int wx = x(w,width);
       int wy = y(w,width);
         //StdOut.println("from ("+x(v,width)+","+y(v,width)+") to current relaxing posi"+wx+","+wy);
       if (distTo[w]>distTo[v]+energy[wy][wx])// the distance counts the current node's energy
       {
           distTo[w] = distTo[v]+energy[wy][wx];
           edgeTo[w] = v;
       }
   }
   
   
   
   private int id(int x, int y, int width) // return the index number from pos (i,j)
   {
       return y*width+x+1;
   }
   
   private int x(int N, int width)
   {
       return (N-1)%width;
   }
   
   private int y(int N, int width)
   {
       return (N-1)/width;
   }
   
   
    
  
   public int[] findHorizontalSeam()               // sequence of indices for horizontal seam
   {   int width = this.width();
       int height = this.height();
       double [][] energy  = new double[height][width];
       for (int j=0;j<height;j++)   
         for (int i=0;i<width;i++)
           energy[j][i]=energy(i,j);  
      double [][] transenergy = transpose(energy);
      int []result = findVpath(transenergy);
     // energy = transpose(energy);
     return result;
       
   }
   
      
   
   public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
   {    
       if (this.width()<=1 || this.height()<=1)
           throw new IllegalArgumentException();
       int lofs=seam.length;
          if (lofs>this.width())
            throw new IllegalArgumentException();
       for (int i = 0; i<lofs-1;i++)
       { 
           if (seam[i]-seam[i+1]<-1 || seam[i]-seam[i+1]>1)
           throw new IllegalArgumentException();
       }
        for (int i = 0; i<lofs;i++)
       { 
           if (seam[i]<0 || seam[i]>=this.height())
           throw new IllegalArgumentException();
       }
       
       
       
       
       Picture newpicture = new Picture(this.width(),this.height()-1);
        
           for (int i = 0; i < this.width(); i++)
               for (int j = 0; j <seam[i]; j++)
                   newpicture.set(i,j,this.mypicture.get(i,j));   
    
             for (int i = 0; i < this.width(); i++)
               for (int j = seam[i]+1; j < this.height(); j++)
                    newpicture.set(i,j-1,this.mypicture.get(i,j)); 
             
       this.mypicture = newpicture;
   }
       
       
   public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
   { 
        if (this.width()<=1 || this.height()<=1)
           throw new IllegalArgumentException();
       int lofs=seam.length;
       if (lofs>this.height())
            throw new IllegalArgumentException();
       for (int i = 0; i<lofs-1;i++)
       { 
           if (seam[i]-seam[i+1]<-1 || seam[i]-seam[i+1]>1)
           throw new IllegalArgumentException();
       }
        for (int i = 0; i<lofs;i++)
       { 
           if (seam[i]<0 || seam[i]>=this.width())
           throw new IllegalArgumentException();
       }
             
       Picture newpicture = new Picture(this.width()-1,this.height());   
       
           for (int j = 0; j < this.height() ; ++j)
               for (int i = 0; i < seam[j]; ++i)
                 newpicture.set(i,j,this.mypicture.get(i,j));   
     
           for (int j = 0; j < this.height() ; ++j)
                  for (int i = seam[j]+1; i < this.width(); ++i)
                    newpicture.set(i-1,j,this.mypicture.get(i,j)); 
             
       this.mypicture = newpicture;
       
   }
   
   public static void main(String []args)
   {
      Picture testpicture = new Picture("3x7.png"); 
      SeamCarver test = new SeamCarver(testpicture); 
       //test.energymaxtrix();
        
        int []path = test.findHorizontalSeam();
        for (int i=0;i<path.length;i++)
        {
         //   StdOut.println(path[i]);
        }
   }
}