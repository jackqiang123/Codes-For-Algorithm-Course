import java.util.Iterator;
public class TEST {

    public static void main(String[] args) {
        Integer startvalue [] = {96,79,68,45,57,13,60,18,29,11};
        MaxPQ<Integer> mypq = new MaxPQ<Integer>(startvalue);
        Iterator<Integer> mypqiter = mypq.iterator();
        while(mypqiter.hasNext()!=false)
          StdOut.println(mypqiter.next());
    }
    

}