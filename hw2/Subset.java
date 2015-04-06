import java.lang.Integer;
import java.util.Iterator;

public class Subset {
   public static void main(String[] args)
   {
       int k = Integer.parseInt(args[0]);
       RandomizedQueue<String> test = new RandomizedQueue<String> (); 
       while (!StdIn.isEmpty())
       {
           test.enqueue(StdIn.readString());
       }
       
       Iterator<String> output = test.iterator();
       
       for (int j = 0; j < k ; j++)
       {
             StdOut.println(output.next());
       }
       
   }
}