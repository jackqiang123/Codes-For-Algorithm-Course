/* Your data type should use space linear in the input size (size of synsets and hypernyms files). 
 * The constructor should take time linearithmic (or better) in the input size.
 * The method isNoun() should run in time logarithmic (or better) in the number of nouns. 
 * The methods distance() and sap() should run in time linear in the size of the WordNet digraph. 
 * For the analysis, assume that the number of nouns per synset is bounded by a constant.
*/
//import java.util.*;
public class WordNet {

   // constructor takes the name of the two input files
   //private RedBlackBST<String,Integer> syntree;
   private RedBlackBST<String,LinkedQueue<Integer>> syntree;
   private RedBlackBST<Integer,String> id2string;
   private Digraph hypergraph;
   private SAP mysap;
   public WordNet(String synsets, String hypernyms)

   {
       syntree = new RedBlackBST<String,LinkedQueue<Integer>>();
       id2string = new RedBlackBST<Integer,String>();
       In sys = new In(synsets); // store the id and words
       String []synset = sys.readAllLines();
       int syslength=synset.length;
       for (int i=0;i<syslength;i++)
       {
          
           String[] currentline=synset[i].split(","); // in the format of #,sysset,gloss
           // however, we also need to split the string.
             id2string.put(i,currentline[1]);// put the id and string into a tree for ancesotr finding
           String[] splitstring=currentline[1].split(" ");// doing this will overwrite some previous information!!! but how to keep some thing in the different places?
         
           for (int j=0;j<splitstring.length;j++)
           { 
               if (syntree.contains(splitstring[j])==false)
               {
                   LinkedQueue<Integer> id = new LinkedQueue<Integer> ();
                   id.enqueue(Integer.parseInt(currentline[0]));
                   syntree.put(splitstring[j],id);
               } 
                   else
               {
                 LinkedQueue<Integer> id = syntree.get(splitstring[j]);
                id.enqueue(Integer.parseInt(currentline[0]));
                   syntree.put(splitstring[j],id);
               }
           }
       }
     
      // int v[]= new int [syslength]; // number of vertices
      // for (int i=0;i<syslength;i++) //initial the vertices
     //      v[i]=i;
       
       hypergraph = new Digraph(syslength);// constructure a digraph
                                
       In hyper = new In(hypernyms); //build the structure of the graph
       String []structure = hyper.readAllLines();
       int sturlength=structure.length;
    
       for (int i=0;i<sturlength;i++)
       {
             
           String[] currentline=structure[i].split(","); // in the format of #,#,...
           int source = Integer.parseInt(currentline[0]);
          // StdOut.println(currentline.length);
           for (int j=1;j<currentline.length;j++)
           { 
           hypergraph.addEdge(source,Integer.parseInt(currentline[j]));
           //StdOut.println("connect"+source+"->"+Integer.parseInt(currentline[j]));
           }
       }
    //   StdOut.println(hypergraph.V());
       
       if (!isRootedDAG(hypergraph))
          throw new java.lang.IllegalArgumentException();
       
       
      mysap = new SAP(hypergraph);
       //structure=structure[0].split(",");
       
    //   StdOut.println(structureSize);
      // String [] mediawords = 
   }
   
   // returns all WordNet nouns
   private boolean isRootedDAG(Digraph G)
   {
       DirectedCycle finder = new DirectedCycle(G);
       if (finder.hasCycle())  // has a cycle
       {//StdOut.println("cycle found");
           return false;}
       // next, check how many root does G have.
       int i = 0;
       while (G.outdegree(i)!=0)
           i=G.adj(i).iterator().next();
       int root = i;
       Digraph temp = G.reverse();
       int rootnumber = new DirectedDFS(temp,root).count();
       //for test
       if (rootnumber<G.V())
           {//StdOut.println(rootnumber+"roots found, but ver is "+hypergraph.V()); 
           return false;}
       return true;
   }
   
   
   public Iterable<String> nouns()
   {
            return syntree.keys();
   }
       
   

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
       return syntree.contains(word);
   }

   // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB)
  {
      //we first find the key value
       if (!isNoun(nounA)||!isNoun(nounB))
          throw new java.lang.IllegalArgumentException();
      LinkedQueue<Integer> keyA = syntree.get(nounA); 
      LinkedQueue<Integer> keyB = syntree.get(nounB);
      int currentdis = Integer.MAX_VALUE;
      for (int i:keyA)
      {
          for (int j:keyB)
          {
              int d = mysap.length(i,j);
              if (d!=-1)
              {
                  if (d<currentdis)
                  {
                      currentdis = d;
                  }
              }
          }
      }
     
     
      return currentdis;
      
  }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB)
       {
      //we first find the key value
        if (!isNoun(nounA)||!isNoun(nounB))
          throw new java.lang.IllegalArgumentException();
      LinkedQueue<Integer> keyA = syntree.get(nounA); 
      LinkedQueue<Integer> keyB = syntree.get(nounB);
      int currentdis = Integer.MAX_VALUE;
      String ance = null;
      for (int i:keyA)
      {
          for (int j:keyB)
          {
              int d = mysap.length(i,j);
              if (d!=-1)
              {
                  if (d<currentdis)
                  {
                      ance = id2string.get(mysap.ancestor(i,j));
                      currentdis = d;
                  }
              }
          }
      }
      return ance;
      
  }

   // do unit testing of this class
   public static void main(String[] args)
   { 
       WordNet mynet = new WordNet("synsets.txt","hypernyms.txt");
                 StdOut.println(mynet.distance("ten_dollar_bill","change")+mynet.sap("ten_dollar_bill","change"));
   }
}