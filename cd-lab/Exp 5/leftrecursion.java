import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ip = "", op1 = "", op2 = "", temp;
        int[] sizes = new int[10];
        char c;
        int n, j, l;
       
        System.out.print("Enter the Parent Non-Terminal: ");
        c = scanner.next().charAt(0);
        ip += c;
        op1 += ip + "'->";
        ip += "->";
        op2 += ip;
       
        System.out.print("Enter the number of productions: ");
        n = scanner.nextInt();
       
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Production " + (i + 1) + ": ");
            temp = scanner.next();
            sizes[i] = temp.length();
            ip += temp;
            if (i != n - 1)
                ip += "|";
        }
       
        System.out.println("Production Rule: " + ip);
       
        for (int i = 0, k = 3; i < n; i++) {
            if (ip.charAt(0) == ip.charAt(k)) {
                System.out.println("Production " + (i + 1) + " has left recursion.");
                if (ip.charAt(k) != '#') {
                    for (l = k + 1; l < k + sizes[i]; l++)
                        op1 += ip.charAt(l);
                    k = l + 1;
                    op1 += ip.charAt(0) + "'|";
                }
            } else {
                System.out.println("Production " + (i + 1) + " does not have left recursion.");
                if (ip.charAt(k) != '#') {
                    for (j = k; j < k + sizes[i]; j++)
                        op2 += ip.charAt(j);
                    k = j + 1;
                    op2 += ip.charAt(0) + "'|";
                } else {
                    op2 += ip.charAt(0) + "'";
                }
            }
        }
       
        op1 += "#";
        System.out.println(op2);
        System.out.println(op1);
        scanner.close();
    }
}