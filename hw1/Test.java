public class Test {
 public static void main(String args[]) {
  long time = System.currentTimeMillis();
  Pi pi = new Pi(200000000000000000);
  pi.comput();
  System.out.println(pi.getPi());
  System.out.println("????>>" + (System.currentTimeMillis() - time));
 }
}

class Pi {
 private double pi;
 private int count;

 public Pi() {

 }

 public Pi(int count) {
  this.count = count;
 }

 public int getCount() {
  return count;
 }

 public void setCount(int count) {
  this.count = count;
 }

 public double getPi() {
  return pi;
 }

 public void comput() {
  pi = 1;
  int max = count * 2 + 1;

  for (int i = 3; i <= max; i = i + 4) {
   pi -= 1d / i;
  }
  for (int i = 5; i <= max; i = i + 4) {
   pi += 1d / i;
  }

  pi *= 4;
 }
}