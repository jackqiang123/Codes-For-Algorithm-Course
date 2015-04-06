import java.util.Iterator;
public class KdTree {
  
    public KdTree()                               // construct an empty set of points 
   {
   }
   
   // member variable
   private Node root; //root node
   private int size = 0; // size of the tree
        
   private static class Node //search node for KDTREE, a static class
   {
   public Node (Point2D p, RectHV rect, boolean iseven) //construct function
   {
       this.p = p;
       this.rect = rect;
       //this.lb = lb;
       //this.rt = rt;
       this.iseven = iseven;
       //this.father = father; 
   }
   public int CompareByLevel(Point2D that)
   {
       if (this.iseven == true)  //compare by x
       {
           if (this.p.x() > that.x())
               return 1;
           else if (this.p.x() < that.x())
               return -1;
           else if (this.p.y() > that.y())
               return 1;
           else if (this.p.y() < that.y())
               return -1;
           else return 0;
       }
       
       else //compare by x
       {
           if (this.p.y() > that.y())
               return 1;
           else if (this.p.y() < that.y())
               return -1;
           else if (this.p.x() > that.x())
               return 1;
           else if (this.p.x() < that.x())
               return -1;
           else return 0;
       }
       
   }
   
   //private Node father; // the current node's father node;
   private Point2D p;      // the point
   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
   // this rect can be obtained by adjust its parents rect
   private Node lb;        // the left/bottom subtree
   private Node rt;        // the right/top subtree
   private boolean iseven;  // to indicate whether the current node is even or not? 
  } // end definition of node
   
   
   public boolean isEmpty()                      // is the set empty? 
   {
       return (this.size==0);
   }
   
   public int size()                         // number of points in the set 
   {
       return this.size;
   }
   
   
   // the insert function
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {  // StdOut.println("i am in the insert");
       root = put(root,p,0,0,1,1,true);// for the root, the rect is the entire space, and the current dim is true;
   }
   
   private Node put(Node x, Point2D p, double xmin, double ymin, double xmax, double ymax, boolean xdim) // rect is something to initial the  rect, xdim is show whether 
       //the current dim is hirizonal, and 
   {
       if (x == null) // how can we construct the rectHV?
       {  // StdOut.println("i am in the null case");
           size = size +1;
           return new Node(p, new RectHV(xmin,ymin,xmax,ymax), xdim);  // put the point value p, the rect and dim down.    
       }
       int cmp = x.CompareByLevel(p);
       if (cmp < 0)  // p is larger than x, insert the right tree
       {  
           //the next dim is oppsite tot he current x.iseven
           //rect is the form of xmin, ymin, xmax, ymax
           if (x.iseven == true) // p >x.rect. the right half
           {  
              // RectHV myrect = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
           x.rt = put(x.rt,p,x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax(),!x.iseven);}
           else  // the top half
           {    //RectHV myrect = new RectHV(x.rect.xmin(),x.p.y(), x.rect.xmax(), x.rect.ymax());   
           x.rt = put(x.rt,p,x.rect.xmin(),x.p.y(), x.rect.xmax(), x.rect.ymax(),!x.iseven);} 
           
       }
       else if (cmp > 0)  // p < x.p
       {   
           if (x.iseven == true) // p >x.rect. the left half
           {//RectHV myrect = new RectHV(x.rect.xmin(),x.rect.ymin(), x.p.x(), x.rect.ymax());
            x.lb = put(x.lb,p,x.rect.xmin(),x.rect.ymin(), x.p.x(), x.rect.ymax(),!x.iseven);}
           else  // the bottom half
           {  //RectHV myrect = new RectHV(x.rect.xmin(),x.rect.ymin(), x.rect.xmax(), x.p.y());    
            x.lb = put(x.lb,p,x.rect.xmin(),x.rect.ymin(), x.rect.xmax(), x.p.y(),!x.iseven);} 
          
       
       }
       else { x.p = p; }// do nonthing in fact
      
       return x;
   }
   
   
   // the contain function
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       return get(p) != null;
   }  
   
   private Point2D get(Point2D p)
   {
       return get(root,p);
   }
   
   private Point2D get(Node x, Point2D p)
   {
       if (x == null) return null;
      
       int cmp = x.CompareByLevel(p);
       if  (cmp < 0) return get(x.rt, p);
       else if (cmp > 0) return get(x.lb, p);
       else return x.p;  // cmp == 0 , find the key 
   }
   // end defintion of get
   
    private Iterable<Node> levelOrder()  //level order, for drawing
    {
        Queue<Node> queue = new Queue<Node>();
        Queue<Node> result = new Queue<Node>();      
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
               result.enqueue(x);
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return result;
    }
   
   
       
   public void draw()                         // draw all points to standard draw 
   {
       Iterator<Node> myiterator = levelOrder().iterator();
       while (myiterator.hasNext() == true)
       {   
          // StdOut.println("i am in the iterator");
           Node currentnode = myiterator.next();
           StdDraw.setPenColor(StdDraw.BLACK);
           StdDraw.setPenRadius(.01);
           currentnode.p.draw();
         //  StdOut.println(currentnode.p.toString());
           
           if (currentnode.iseven == true)
           {
               StdDraw.setPenColor(StdDraw.RED);
               StdDraw.setPenRadius();
               Point2D ptop = new Point2D(currentnode.p.x(),currentnode.rect.ymax());
               Point2D pbottom = new Point2D(currentnode.p.x(),currentnode.rect.ymin());
               ptop.drawTo(pbottom);
           }
           else 
           {
               StdDraw.setPenColor(StdDraw.BLUE);
               StdDraw.setPenRadius();
               Point2D pleft =  new Point2D(currentnode.rect.xmin(),currentnode.p.y());
               Point2D pright = new Point2D(currentnode.rect.xmax(),currentnode.p.y());
               pleft.drawTo(pright);
                
           }
       }
   }
   
   
   
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   //if the query rectangle does not intersect the rectangle corresponding to a node, there is no need to explore that node (or its subtrees). 
   //A subtree is searched only if it might contain a point contained in the query rectangle.
   {
       
       Stack<Node> search = new Stack<Node>(); // use to trasveal
       Stack<Point2D> result = new Stack<Point2D>();  // use to store the result.
        if (isEmpty()==true) return result;
       search.push(root);
       while (search.isEmpty() == false)
       {
         Node x =  search.pop();
         if (rect.contains(x.p) == true) result.push(x.p); // if x is in the rect, then push it into the rect
         
         if (x.lb != null)
             if (rect.intersects(x.lb.rect) == true) 
                 search.push(x.lb);
         if (x.rt != null)
             if (rect.intersects(x.rt.rect) == true) 
                 search.push(x.rt); 
        
       }                 
       return result;
       
       
   }
   public Point2D nearest(Point2D queryPoint)     
   { 
       if (isEmpty() == true) return null;
       return doNearest(root, queryPoint, root.p); // we initalize the bestsofar node as root.p
   }
       // a nearest neighbor in the set to point p; null if the set is empty 
       //To find a closest point to a given query point, start at the root and recursively search 
       //in both subtrees using the following pruning rule: if the closest point discovered so far is closer than the distance 
       //between the query point and the rectangle corresponding to a node, 
       //there is no need to explore that node (or its subtrees).
   private Point2D doNearest (Node node, Point2D queryPoint, Point2D BestSoFarPoint)
   {
       if (node == null) return BestSoFarPoint;
       
      double bestdistance = queryPoint.distanceSquaredTo(BestSoFarPoint); // best-so-far distance
      double currentdistance = queryPoint.distanceSquaredTo(node.p);    
      
      if (currentdistance<bestdistance) //update the distance if it is small
      {
          //BestSoFarDistance = currentdistance;
          BestSoFarPoint = node.p;  
      }
           
     // StdOut.println(BestSoFarDistance);
      //StdOut.println(BestSoFarPoint.toString());
      
       if (node.rect.distanceSquaredTo(queryPoint) < bestdistance)
       {
           if (node.CompareByLevel(queryPoint)<0) // first lb, then rt
           { 
             BestSoFarPoint = doNearest(node.lb,queryPoint,BestSoFarPoint);
             BestSoFarPoint = doNearest(node.rt,queryPoint,BestSoFarPoint);
           }
           else if (node.CompareByLevel(queryPoint)>0)
          { 
             BestSoFarPoint= doNearest(node.rt,queryPoint,BestSoFarPoint);
             BestSoFarPoint= doNearest(node.lb,queryPoint,BestSoFarPoint);
           }
           else return node.p;
       }
       return BestSoFarPoint;
   }
       
    
        
    
    
    /*Stack<Node> search = new Stack<Node>(); // use to trasveal
    search.push(root);
    distance = p.distanceSquaredTo(root.p);
    double goalx = p.x();
    double goaly = p.y();
       while (search.isEmpty() == false)
       {
         Node x =  search.pop();
         Point2D currentpoint = x.p;
         double currentx = x.p.x();
         double currenty = x.p.y();
         
        
             
         
         if (x.rt != null)
          if (distance > x.rect.distanceSquaredTo(p))   
                 search.push(x.rt); 
         
         if (x.lb != null)
            if (distance > x.rect.distanceSquaredTo(p))          
                 search.push(x.lb);
        
          if ( (goalx-currentx)*(goalx-currentx)+(goaly-currenty)*(goaly-currenty) < distance)             
         {
             distance = p.distanceSquaredTo(x.p); 
             bestsofar = currentpoint;
           //  StdOut.println("current distance"+ distance);
         }
        
       }                 
       
    return bestsofar;*/ 

       
       

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
    KdTree kdtree = new KdTree();
    Point2D p0 = new Point2D(0.5,0.5);
    Point2D p1 = new Point2D(0.4,0.6);
    Point2D p2 = new Point2D(0.1,0.6);
    kdtree.insert(p0);
    kdtree.insert(p1);
    kdtree.insert(p2);
    StdOut.println(kdtree.nearest(new Point2D(0.1,0.6)).toString());
   }
}