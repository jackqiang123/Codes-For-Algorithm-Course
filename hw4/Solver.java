import java.util.Comparator;
import java.util.Iterator;
public class Solver {   
    private class SearchNode implements Comparable<SearchNode>// an inner class called search node
    {
        public Board block; //current search board
        public SearchNode previous;  // previous search node
        public int move; //distance
        SearchNode(Board block, SearchNode previous, int move)
        {
            this.block = block;
            this.previous = previous;
            this.move = move;
        }
        public int compareTo (SearchNode that)
        {
            int d1 = this.move + this.block.manhattan();
            int d2 = that.move + that.block.manhattan();
            if (d1 > d2) 
                return 1;
            if (d1 == d2)
                return 0;
            else return -1;
        }                              
    }
    
    private SearchNode finalnode;
    private SearchNode initalsearch;        
    private boolean isSolve = true;
    //private SearchNode twininitalnode;
    
    //private MinPQ<SearchNode> gametreetwin = new MinPQ<SearchNode>(); // a class member with genitirc type SearchNode
    
    public Solver(Board initial)  // find a solution to the initial board (using the A* algorithm)
    {
        MinPQ<SearchNode> gametree = new MinPQ<SearchNode>(); // a class member with genitirc type SearchNode
        MinPQ<SearchNode> gametreetwin = new MinPQ<SearchNode>(); 
        initalsearch = new SearchNode (initial, null ,0);
        Board twinnode = initial.twin();
        SearchNode twininitalnode = new SearchNode (twinnode, null ,0);
        
        gametree.insert(initalsearch); // insert the beginning
        gametreetwin.insert(twininitalnode);
        // now we begin the main logic
        
        while (true)
        {
            SearchNode deque = gametree.delMin();
            SearchNode dequetwin = gametreetwin.delMin();
            if (deque.block.isGoal() == true || dequetwin.block.isGoal() == true) //dequeue the minimum, and if it is goal, then we stop
            {
                finalnode = deque;
                if (deque.block.isGoal() == true)
                    isSolve = true;
                else 
                    isSolve = false;               
                break;
            }
            
            Iterator<Board> currentnb = deque.block.neighbors().iterator();// an iterator of the current deque node
            Iterator<Board> currentnbtwin = dequetwin.block.neighbors().iterator();// an iterator of the current deque node
            
            while (currentnb.hasNext() == true) // put the cuurent neigh into the gametree, note that we need SeachNode
            {
            SearchNode enque = new SearchNode(currentnb.next(), deque, deque.move+1);
              if(deque.previous == null)
                   gametree.insert(enque);
             else if (enque.block.equals(deque.previous.block) == false) //critial optimization
                   gametree.insert(enque);
            }
            
             while (currentnbtwin.hasNext() == true) // put the cuurent neigh into the gametree, note that we need SeachNode
            {
            SearchNode enquetwin = new SearchNode(currentnbtwin.next(), dequetwin, dequetwin.move+1);
            if(dequetwin.previous == null)
                   gametreetwin.insert(enquetwin);
           else if (enquetwin.block.equals(dequetwin.previous.block) == false) //critial optimization
                gametreetwin.insert(enquetwin);
            }
                      
            
        }
        
        
    }         
    public boolean isSolvable()   
    {
        
       return isSolve;
 
    }        // is the initial board solvable?
    
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (isSolve == true)
            return finalnode.move;
        else 
            return -1;
    }
        
        
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (isSolve == false) return null;
        
        Stack<Board> result = new Stack<Board>();      
        SearchNode node = finalnode;
        while (node.block.equals(initalsearch.block) == false)
        {
            result.push(node.block);
            node = node.previous;
        }
        return result;
    }
    
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
} // solve a slider puzzle (given below)
}