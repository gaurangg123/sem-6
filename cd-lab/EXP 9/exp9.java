import java.util.*;

public class CodeGenerationForIf {
    public static void main(String[] args) {
        String condition = "x > 5";
        String action = "y = x * 2;";
        String generatedCode = generateIfCode(condition, action);
        System.out.println("Generated code:");
        System.out.println(generatedCode);
    }

    public static String generateIfCode(String condition, String action) {
        return "if (" + condition + ") {\n\t" + action + "\n}";
    }
}