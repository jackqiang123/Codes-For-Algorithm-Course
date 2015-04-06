import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N = 0;               //current queue length
      
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       a = (Item[]) new Object[2]; 
   }
   
   public boolean isEmpty()                 // is the queue empty?
   {
       return N == 0;
   }
   
   public int size()                        // return the number of items on the queue
   {
       return N;
   }
   
   private void resize(int capacity) 
   {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
     
   public void enqueue(Item item)           // add the item
   { 
        if (item == null)
          throw new NullPointerException();   
        if (N == a.length) resize(2*a.length);    // double size of array if necessary
        a[N++] = item;                            // add item
   }
   
   public Item dequeue()                    // delete and return a random item
   {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(N);
        Item item = a[index];
        a[index] = a[N-1];
        a[N-1] = null;                              // to avoid loitering
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
   }
   
   public Item sample()                     // return (but do not delete) a random item
   {
       if (N==0)
           throw new NoSuchElementException();
       return a[StdRandom.uniform(N)];
   }
       
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandQueueIterator();
   }    
    
   private class RandQueueIterator implements Iterator<Item>
   {
       private int i;
       private int index[];
       
       public RandQueueIterator()
       {
           this.i = N;
           this.index = new int [N];
           for (int j=0; j<N; j++)
               index[j]=j;  
           StdRandom.shuffle(index);
       }
       
       public boolean hasNext()
       {
           return i>0;
       }
       
       public void remove() 
       {
            throw new UnsupportedOperationException();
        }
       
       public Item next() 
       {
            if (!hasNext()) throw new NoSuchElementException();
            return a[index[--i]];
        }     
   }
       
      
   public static void main(String[] args)   // unit testing
   {
       RandomizedQueue<String> test = new RandomizedQueue<String> (); 
       test.enqueue("tsinghua");
       test.enqueue("univ");
       test.enqueue("is");
       test.enqueue("top");
       Iterator<String> i = test.iterator();
       while (i.hasNext()) {    String s = i.next();    StdOut.println(s); }
       
   }
}