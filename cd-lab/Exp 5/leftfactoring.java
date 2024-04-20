import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of productions: ");
        int p = scanner.nextInt();
        ArrayList<String> prodleft = new ArrayList<>(p);
        ArrayList<String> prodright = new ArrayList<>(p);
        System.out.println("Enter productions one by one: ");
        for (int i = 0; i < p; ++i) {
            System.out.print("\nLeft of production " + (i + 1) + ": ");
            prodleft.add(scanner.next());
            System.out.print("\nRight of production " + (i + 1) + ": ");
            prodright.add(scanner.next());
        }
        int e = 1;
        for (int i = 0; i < p; ++i) {
            for (int j = i + 1; j < p; ++j) {
                if (prodleft.get(j).equals(prodleft.get(i))) {
                    int k = 0;
                    StringBuilder com = new StringBuilder();
                    while (k < prodright.get(i).length() && k < prodright.get(j).length()
                            && prodright.get(i).charAt(k) == prodright.get(j).charAt(k)) {
                        com.append(prodright.get(i).charAt(k));
                        ++k;
                    }
                    if (k == 0)
                        continue;
                    String comleft = prodleft.get(i);
                    if (k == prodright.get(i).length()) {
                        prodleft.set(i, prodleft.get(i) + e);
                        prodleft.set(j, prodleft.get(j) + e);
                        prodright.set(i, "^");
                        prodright.set(j, prodright.get(j).substring(k));
                    } else if (k == prodright.get(j).length()) {
                        prodleft.set(i, prodleft.get(i) + e);
                        prodleft.set(j, prodleft.get(j) + e);
                        prodright.set(j, "^");
                        prodright.set(i, prodright.get(i).substring(k));
                    } else {
                        prodleft.set(i, prodleft.get(i) + e);
                        prodleft.set(j, prodleft.get(j) + e);
                        prodright.set(j, prodright.get(j).substring(k));
                        prodright.set(i, prodright.get(i).substring(k));
                    }
                    for (int l = j + 1; l < p; ++l) {
                        if (comleft.equals(prodleft.get(l))
                                && com.toString().equals(prodright.get(l).substring(0, Math.min(k, prodright.get(l).length())))) {
                            prodleft.set(l, prodleft.get(l) + e);
                            prodright.set(l, prodright.get(l).substring(k));
                        }
                    }
                    prodleft.add(comleft);
                    prodright.add(com.toString() + prodleft.get(i));
                    ++p;
                    ++e;
                }
            }
        }
        System.out.println("\n\nNew productions");
        for (int i = 0; i < p; ++i) {
            System.out.println(prodleft.get(i) + "->" + prodright.get(i));
        }
    }
}
_______________________________________________________________
