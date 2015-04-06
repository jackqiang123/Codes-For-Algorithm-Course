import java.util.Arrays;
public class Fast {
   public static void main(String[] args)
       {  
       int flag = 0; //the length of the point
        //reat the input data;
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[]data = new Point [N];
        for (int i = 0; i < N; i++) 
        {
            int x = in.readInt();
            int y = in.readInt();
            data[i] = new Point(x, y); 
            data[i].draw();
        }
        
        //use the fast algorithm
        
        for (int p = 0; p<N; p++)
        {
             Point [] aux = new Point [N];
             for (int i = 0; i<N; i++)
                aux[i] = data[i];
             Arrays.sort(aux,data[p].SLOPE_ORDER);//already sort the aux according to SLOPE_ORDER
           // if (p==5)
            //{for (int i = 0; i<N; i ++)
              //  StdOut.println(aux[i].toString()+aux[i].slopeTo(data[p]));}
           
             
             for (int i = 1; i<N; i++)
             {   
                 
                 if (i==1) //intialize the data
                     flag = 2;
                 
                 if (i == N-1)
                 {
                      if (aux[N-1].slopeTo(data[p])==aux[N-2].slopeTo(data[p]))
                          flag++; 
                      if (flag>3) // find we the last line segment. draw it
                      { //StdOut.println("i find a line segment in last round, and the current base point is"+p );
                            Point [] linear = new Point [flag]; //put them into the linear box
                            linear[0] = data[p];
                            for (int j = 1; j<flag; j++)
                            { // StdOut.println(i-j);
                                 if (aux[N-1].slopeTo(data[p])==aux[N-2].slopeTo(data[p]))
                                     linear[j] = aux[N-j];
                                 else 
                                     linear[j] = aux[N-j-1];
                            } 
                             Arrays.sort(linear);                    
                             if (linear[0].compareTo(data[p])==0)
                             {  for (int pp = 0; pp<flag-1; pp++)
                                 StdOut.print(linear[pp].toString()+" -> ");
                               StdOut.println(linear[flag-1].toString());                            
                              linear[0].drawTo(linear[flag-1]);
                             }
                      }
                                            
                          
                 }
                 
                 
                 // next, i != 0 and i!=N-1
                 
                 else{
                  if (aux[i].slopeTo(data[p])==aux[i-1].slopeTo(data[p]))
                   {flag++; } // find a match, and continue to find the next match. out of loop when finishing find the same angle point
                 else if (flag >3)
                 {          //StdOut.println("flag="+flag);
                            Point [] linear = new Point [flag]; //put them into the linear box
                            linear[0] = data[p];
                            for (int j = 1; j<flag; j++)
                            {  //StdOut.println(i-j);
                             linear[j] = aux[i-j];
                            } 
                             Arrays.sort(linear);                    
                             if (linear[0].compareTo(data[p])==0)
                             {  for (int pp = 0; pp<flag-1; pp++)
                                 StdOut.print(linear[pp].toString()+" -> ");
                               StdOut.println(linear[flag-1].toString());                            
                               linear[0].drawTo(linear[flag-1]);
                             }
                     flag =2; // reset the flag to be 2
                 }
                 else 
                 {flag =2; }
                                 
                                
             } // StdOut.println(flag);
             }
        }
        
        
      
        
       StdDraw.show(0);
       StdDraw.setPenRadius(); 
    
    }
       
}