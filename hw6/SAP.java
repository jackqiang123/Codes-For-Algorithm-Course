public class SAP {

   // constructor takes a digraph (not necessarily a DAG)
    private Digraph G;
  //  private BreadthFirstDirectedPaths fromv;
    //private BreadthFirstDirectedPaths fromw;
    private int distance;
    private int ancestor;
    private int lastW = -1;
    private int lastV = -1;
    private Iterable<Integer> lastWI = null;
    private Iterable<Integer> lastVI = null;
    
   public SAP(Digraph G)
   {
       this.G=G;   
       this.distance=-1;
       this.ancestor=-1;
   }
      
   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)

   {    
       if (v==-1||w==-1||v>=G.V()||w>=G.V())
              throw new IndexOutOfBoundsException();  
       
       if ((lastW==w && lastV==v) ||(lastW==v && lastV==w))
          return this.distance;
       
       lastW = w;
       lastV = v;
       this.distance=-1;
       this.ancestor=-1;
       int lastdistance=Integer.MAX_VALUE;
       
       BreadthFirstDirectedPaths fromv = new BreadthFirstDirectedPaths(G,v); 
       BreadthFirstDirectedPaths fromw = new BreadthFirstDirectedPaths(G,w);
       for (int i = 0 ; i<G.V(); i++)
       {
        if (fromv.hasPathTo(i) && fromw.hasPathTo(i)) // if there is a path
        {
            int tempdistance = fromv.distTo(i)+fromw.distTo(i);
            if (tempdistance < lastdistance)
            {
                this.ancestor = i;
                lastdistance=tempdistance;  
                distance = tempdistance;
            }
        }
       }
       return this.distance;
       
   }
   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
  {
        if (v==-1||w==-1||v>=G.V()||w>=G.V())
              throw new IndexOutOfBoundsException();  
       
       if ((lastW==w && lastV==v) ||(lastW==v && lastV==w))
          return this.ancestor;
       //distance=Integer.MAX_VALUE;// intial length is infinite
       this.distance=-1;
       this.ancestor=-1;
       int lastdistance=Integer.MAX_VALUE;
       BreadthFirstDirectedPaths fromv = new BreadthFirstDirectedPaths(G,v); 
       BreadthFirstDirectedPaths fromw = new BreadthFirstDirectedPaths(G,w);
       for (int i = 0 ; i<G.V(); i++)
       {
        if (fromv.hasPathTo(i) && fromw.hasPathTo(i)) // if there is a path
        {
           int tempdistance = fromv.distTo(i)+fromw.distTo(i);
            if (tempdistance < lastdistance)
            {
                this.ancestor = i;
                lastdistance=tempdistance;  
                distance = tempdistance;
            }
        }
       }
       return this.ancestor;
       
   }
 
   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       if (lastWI!=null && lastVI!=null)
        if ((lastWI.equals(w) && lastVI.equals(v))|| (lastWI.equals(v) && lastVI.equals(w)))
          return this.distance;
       
       this.distance=-1;
       this.ancestor=-1;
       
       BreadthFirstDirectedPaths fromv = new BreadthFirstDirectedPaths(G,v); 
       BreadthFirstDirectedPaths fromw = new BreadthFirstDirectedPaths(G,w);
        int lastdistance=Integer.MAX_VALUE;
       for (int i = 0 ; i<G.V(); i++)
       {
           
        if (fromv.hasPathTo(i) && fromw.hasPathTo(i)) // if there is a path
        {
            int tempdistance = fromv.distTo(i)+fromw.distTo(i);
            if (tempdistance < lastdistance)
            {
                this.ancestor = i;
                lastdistance=tempdistance;  
                distance = tempdistance;
            }
        }
       }
       return this.distance;
        
   }
   
   
       

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
 
public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       if (lastWI!=null && lastVI!=null)
        if ((lastWI.equals(w) && lastVI.equals(v))|| (lastWI.equals(v) && lastVI.equals(w)))
          return this.distance;
       this.distance=-1;
       this.ancestor=-1;
       
        int lastdistance=Integer.MAX_VALUE;
       
       BreadthFirstDirectedPaths fromv = new BreadthFirstDirectedPaths(G,v); 
       BreadthFirstDirectedPaths fromw = new BreadthFirstDirectedPaths(G,w);
       for (int i = 0 ; i<G.V(); i++)
       {
        if (fromv.hasPathTo(i) && fromw.hasPathTo(i)) // if there is a path
        {
            int tempdistance = fromv.distTo(i)+fromw.distTo(i);
            if (tempdistance < lastdistance)
            {
                this.ancestor = i;
                lastdistance=tempdistance;  
                distance = tempdistance;
            }
        }
       }
       return this.ancestor;
        
   }
   
   // do unit testing of this class
   
       
   private void bfs(Iterable<Integer> v, Iterable<Integer> w)
   {
       
   }
       
   public static void main(String[] args) 
   {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    StdOut.println(G.toString());
     
   /* while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
    */
    
    Stack<Integer> p = new Stack<Integer>();
    p.push(5);
    Stack<Integer> q = new Stack<Integer>();
    q.push(7);q.push(2);
     int length   = sap.length(p, q);
     int ancestor = sap.ancestor(p, q);
     StdOut.println(length);
      StdOut.println(ancestor);
   }
   
}

