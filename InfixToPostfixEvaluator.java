import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfixEvaluator {

    // Method to define operator precedence
    private static int getPrecedence(char ch) {
        switch (ch) {
            case '+': case '-': return 1;
            case '*': case '/': case '%': return 2;
            case '^': return 3; // Exponentiation has the highest precedence
            default: return -1;
        }
    }

    // Method to check if a character is an operator
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^';
    }

    // Method to convert infix to postfix with step-by-step display
    public static String infixToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        System.out.printf("%-15s %-15s %-20s%n", "Token (X)", "Stack (Y)", "Output (Postfix)");

        for (char token : infix.toCharArray()) {
            if (Character.isDigit(token)) { // If token is an operand
                postfix.append(token).append(" ");
            } else if (token == '(') {
                stack.push(token);
            } else if (token == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.pop(); // Remove '(' from stack
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(token)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }

            // Display step-by-step process
            System.out.printf("%-15s %-15s %-20s%n", token, stack, postfix);
        }

        // Pop all remaining operators in stack
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
            System.out.printf("%-15s %-15s %-20s%n", "(end)", stack, postfix);
        }

        return postfix.toString().trim();
    }

    // Method to evaluate the postfix expression
    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        System.out.println("\nPostfix Evaluation Steps:");

        for (String token : tokens) {
            if (token.matches("\\d+")) { // If it's a number, push to stack
                stack.push(Integer.parseInt(token));
            } else { // It's an operator, perform operation
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;

                switch (token) {
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "*": result = a * b; break;
                    case "/": result = a / b; break;
                    case "%": result = a % b; break;
                    case "^": result = (int) Math.pow(a, b); break;
                }
                stack.push(result);

                // Display step-by-step evaluation
                System.out.println(stack);
            }
        }

        return stack.pop(); // Final result
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter an infix expression: ");
            String infixExpression = scanner.nextLine().replaceAll("\\s+", ""); // Remove spaces

            // Convert to postfix and display step-by-step process
            String postfixExpression = infixToPostfix(infixExpression);
            System.out.println("\nFinal Postfix Expression: " + postfixExpression);

            // Evaluate and show step-by-step solution
            int result = evaluatePostfix(postfixExpression);
            System.out.println("\nFinal Answer: " + result);

            // Ask user if they want to try again
            System.out.print("\nDo you want to try again? (yes/no): ");
            String choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("yes")) {
                break;
            }
        }

        scanner.close();
    }
}
