package x.y.study.alg.recursion;

public class FirstRecursion {
   public static void reverseOrder(int index, char[] str) {
     if (str == null || index >= str.length) return;

     reverseOrder(index + 1, str);

     System.out.print(str[index]);
   }

   public static void main(String[] args) {
      reverseOrder(0, "123456".toCharArray());
   }
} 

