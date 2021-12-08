import java.util.Scanner;
/**
 * @author Hyperion
 * @date 2021/11/28
 */
public class Main {
public static void main(String[] args) {
String name = "Hyperion";
Scanner in = new Scanner(System.in);
int t = 0, a = 0, b = 0;
System.out.println(name);
t = in.nextInt();
while(t-- > 0){
a = in.nextInt();
b = in.nextInt();
System.out.println(a + b);
}
System.out.println(name);
}
}