import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
   private Node first;
   private Node last;
   private int queuelength;
   
   private class Node
   {
       Node next;
       Node previous;
       Item item;
   }
   
   public Deque()   // construct an empty deque
   {
       first = null;
       last = null;
       queuelength = 0;
   }
       
   public boolean isEmpty()                 // is the deque empty?
   { 
       return queuelength == 0;
   }
   
   public int size()                        // return the number of items on the deque
   {
       return queuelength;
   }
   
   public void addFirst(Item item)          // insert the item at the front
   {
       if (item == null)
           throw new NullPointerException();
       Node oldfirst = first;
       first = new Node ();
       if (last == null)
           last = first;
       first.item = item;
       first.next = oldfirst;
       if (oldfirst != null)
           oldfirst.previous = first;
       queuelength++;
   }    
       
   public void addLast(Item item)           // insert the item at the end
   {
       if (item == null)
           throw new NullPointerException();
       Node oldlast = last;
       last = new Node();
       if (first == null)
           first = last;
       last.item = item;
       if (oldlast != null)
            oldlast.next = last;
       last.previous = oldlast;
       queuelength++;
   }
   
   public Item removeFirst()                // delete and return the item at the front
   {
       if (first == null)
           throw new NoSuchElementException();
       Item item = first.item;
       first = first.next;
       if (first != null)
           first.previous = null;
       queuelength--;
       return item;
   }
   
   public Item removeLast()                 // delete and return the item at the end
   {
       if (first == null)
           throw new NoSuchElementException();
       Item item = last.item;
       last = last.previous;
       if (last != null)
           last.next = null;
       queuelength--;
       return item;
   }
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator();
   }
   
   private class DequeIterator implements Iterator<Item>
   {
       private Node current = first;
       public boolean hasNext() { return current != null;}
       public void remove() {throw new UnsupportedOperationException();}
       public Item next()
       {
            if (!hasNext())
                throw new NoSuchElementException();
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
   
   public static void main(String[] args)   // unit testing
   {
        
   }
}