public class Outcast {
    private WordNet mynet;
   public Outcast(WordNet wordnet)         // constructor takes a WordNet object
   {
       mynet = wordnet;
   }
   public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
   {
      int length = nouns.length;
      int currentshortest = 0;
      String out=null;
      for (int i = 0 ; i<length; i++)
      {//Std
          int distance = 0 ;
          for (int j=0 ; j<length;j++)
          {
              distance = distance + mynet.distance(nouns[i],nouns[j]);
             // StdOut.println(mynet.distance(nouns[i],nouns[j]));
          }
         //  StdOut.println(nouns[i]+" is with"+distance);
          if (distance > currentshortest)
          {
              currentshortest= distance ;
              out = nouns[i];
          }
         
          
      }
      return out;
   }
       
       
       
   public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
    
}
}