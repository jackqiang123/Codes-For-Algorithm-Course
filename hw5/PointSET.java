import java.util.Iterator;
public class PointSET {
   private SET<Point2D> rbtree;
   public PointSET()                               // construct an empty set of points 
   {
       rbtree = new SET<Point2D>();
   }
   
   public boolean isEmpty()                      // is the set empty? 
   {
       return rbtree.isEmpty();
   }
   
   public int size()                         // number of points in the set 
   {
    return rbtree.size();   
   }
   
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       rbtree.add(p);
   }
   
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       return rbtree.contains(p);
   }
   
   public void draw()                         // draw all points to standard draw 
   {
       Iterator<Point2D> rbtreeiterator = rbtree.iterator();
       while (rbtreeiterator.hasNext() == true)
           rbtreeiterator.next().draw();
        
   }
   
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
       LinkedStack<Point2D> rangeresult = new LinkedStack<Point2D>(); //generat a iterator;
       
       Iterator<Point2D> rbtreeiterator = rbtree.iterator();
       while (rbtreeiterator.hasNext() == true)
       {
           Point2D p = rbtreeiterator.next();
           if (p.x()<= rect.xmax()&& p.x() >= rect.xmin() && p.y()<= rect.ymax() && p.y()>= rect.ymin())
                  rangeresult.push(p);  
       } 
       return rangeresult;     
   }
   
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {    
       if (isEmpty() == true) return null;
       Point2D currentbest = new Point2D(0,0);
       Iterator<Point2D> rbtreeiterator = rbtree.iterator();
       double distance = Double.POSITIVE_INFINITY;
       while (rbtreeiterator.hasNext() == true)
       {
           Point2D currentpoint = rbtreeiterator.next();        
           if (distance > p.distanceTo(currentpoint))
           {  
               distance = p.distanceTo(currentpoint);
               currentbest = currentpoint;
           }
       }
       return currentbest;
   }

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
    PointSET kdtree = new PointSET();
    Point2D p0 = new Point2D(0.5,0.5);
    kdtree.insert(p0);
    kdtree.draw();
   }
}