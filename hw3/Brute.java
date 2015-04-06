import java.util.Arrays;
public class Brute {
    public static void main(String[] args){
        //reat the input data;
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        //StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
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
        
        //use the brute method to find the lines
        for (int i = 0; i<N-3; i++)
        {    for (int j = i+1; j<N-2; j++)
            {   
                for (int k = j+1; k<N-1; k++)
                {                      
                    for (int l = k+1; l<N; l++)
                    {
                         double slopeij = data[i].slopeTo(data[j]);                         
                         double slopeik = data[i].slopeTo(data[k]);  
                         double slopeil = data[i].slopeTo(data[l]); 
                         if ((slopeij==slopeik)&&(slopeij==slopeil))
                         {                   
                             Point [] linear = new Point [4]; //put them into the linear box
                             linear[0] = data[i];
                             linear[1] = data[j];
                             linear[2] = data[k];
                             linear[3] = data[l];
                            
                             // we need to sort the linear vector
                            Arrays.sort(linear);
                            for (int pp = 0; pp<3; pp++)
                              StdOut.print(linear[pp].toString()+" -> ");
                            StdOut.println(linear[3].toString());
                           // if (slopeij != )
                               linear[0].drawTo(linear[3]);
                            
                         }
                    }
                 }
            }        
        }          
        
       StdDraw.show(0);
       StdDraw.setPenRadius(); 
    
    }
       
    
}