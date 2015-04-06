import java.util.Iterator;
//import java.util.NoSuchElementException;
public class Board {
    private LinkedQueue<Integer> head = new LinkedQueue<Integer>();
    private int N; // dim of block
    private int x0;//locations of zeros
    private int y0;
    
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        this.N = blocks.length;   // initial the dim
        for (int i = 0; i<N; i++)     // initial the blocks
            for (int j=0; j<N; j++)
        {       
               head.enqueue(blocks[i][j]);    
               if (blocks[i][j] == 0)
               {this.x0 = i; this.y0 = j;}
        }
    }
    
    
    public int dimension()                 // board dimension N
    {
        return this.N;
    }
       
    private int[][] LinktoArray()  // transfer the linklist to array
    {
       
        int [][]blocks = new int[N][N];
        Iterator<Integer> blockiter = head.iterator();
        for (int  i = 0; i<N; i++)     // initial the blocks
            for (int j=0; j<N; j++)
              {blocks[i][j] = blockiter.next(); }
        return blocks;

    }
    
    public int hamming()                   // number of blocks out of place
    {
         int [][]blocks = LinktoArray();
        
         int distance = 0;
         for (int  i = 0; i<N; i++)     // initial the blocks
            for (int j=0; j<N; j++)
         {
             if (blocks[i][j] != 0 )
                 if (blocks[i][j] != j+1+i*this.N)
                    distance = distance + 1;
         }
         return distance;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        
        int [][]blocks = LinktoArray();
        
        int distance = 0;
        
          for (int i = 0; i<N; i++)     // initial the blocks
            for (int j=0; j<N; j++)
         {
              if (blocks[i][j]!=0) // to omit the zero to compute the others
              {
                //  StdOut.println("x diif is "+Math.abs((blocks[i][j]-1)%this.N - j));//+"x diff is "+Math.abs(blocks[i][j]%this.N - (j+1)));
                 distance = distance + Math.abs((blocks[i][j]-1)/this.N - i)+Math.abs((blocks[i][j]-1)%this.N - j); 
              }
         }
         return distance;
    }
    
    public boolean isGoal()                // is this board the goal board?
    { 
         if (this.hamming() == 0)
             return true;
         else
             return false;
        
    }
    
    public Board twin()                    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    {
       int [][]blocks = LinktoArray();
       
       int [][]twin = new int [N][N];
       
        int swap;
          for (int i = 0; i<N; i++)     // initial the blocks
            for (int j=0; j<N; j++)
               twin[i][j] = blocks[i][j];    
          if (twin[0][0]!=0 && twin[0][1]!=0)
           {swap = twin[0][0]; twin[0][0] = twin[0][1]; twin[0][1] = swap;}
          else
           {swap = twin[1][0]; twin[1][0] = twin[1][1]; twin[1][1] = swap;}
              
        return new Board (twin);
    }
        
        
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
       
        if (this.dimension() != that.dimension())
            return false;
            
       int [][]blocks = this.LinktoArray();
       
       int [][]thatblock = that.LinktoArray();
     
       for (int i = 0; i<N; i++)    
           for (int j=0; j<N; j++)
           {
            if (blocks[i][j] != thatblock[i][j])  
                return false;
           }
        return true;
    }
    
        
        
    public Iterable<Board> neighbors()     // all neighboring boards, return an object of class Iterable
    {  
        LinkedQueue<Board> Boarditerator = new LinkedQueue<Board>();
        
         if (x0 != 0) // let the left enqueue            
         {
         int [][] left = LinktoArray();
         left[x0][y0] = left[x0-1][y0];
         left[x0-1][y0] = 0;           
          Boarditerator.enqueue(new Board(left));     
         }
          if (x0 != N-1) // let the right enqueue            
         {
         int [][] right = LinktoArray();
         right[x0][y0] = right[x0+1][y0];
         right[x0+1][y0] = 0;              
          Boarditerator.enqueue(new Board(right));     
         }
           if (y0 != 0) // let the left enqueue            
         {
         int [][] top = LinktoArray();
         top[x0][y0] = top[x0][y0-1];
         top[x0][y0-1] = 0;              
          Boarditerator.enqueue(new Board(top));     
         }
            if (y0 != N-1) // let the left enqueue            
         {
         int [][] bottom = LinktoArray();
         bottom[x0][y0] = bottom[x0][y0+1];
         bottom[x0][y0+1] = 0;              
          Boarditerator.enqueue(new Board(bottom));     
         }
     return Boarditerator;       
    }   
         
            
    public String toString() // string representation of this board (in the output format specified below)
    {
         int [][]blocks = LinktoArray();
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            s.append(String.format("%2d ", blocks[i][j]));
        }
        s.append("\n");
    }
    return s.toString();
}

    public static void main(String[] args) // unit tests (not graded)
    {
    
    }
}
