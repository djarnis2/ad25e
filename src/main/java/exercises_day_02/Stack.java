package exercises_day_02;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    List<Object> arr = new ArrayList<Object>();

    public void push(Object obj) {
        arr.add(obj);
    }

    public void pop() {
        int length = arr.size();
        if (length > 0) {
            arr.remove(length - 1);
        } else {
            System.out.println("Empty stack");
        }
    }

    public void show() {
        if (!arr.isEmpty()) {
            for (int i=0; i < arr.size(); i++) {
                System.out.println(arr.get(i));
            }

        }
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push("(");
        stack.push(")");
        stack.show();

        System.out.println("Check balPar with [()()(){] (should be false): " + stack.balancedParentheses("[()()(){]"));
        System.out.println("Check balPar with [()()(){]) (should be false): " + stack.balancedParentheses("[()()(){])"));
        System.out.println("Check balPar with [()()(){}] (should be true): " + stack.balancedParentheses("[()()(){}]"));

    }

    public boolean balancedParentheses(String text) {
        Stack stack1 = new Stack();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '('|| text.charAt(i) == '{' || text.charAt(i) == '[') {
                stack1.push(text.charAt(i));
            }
            if (text.charAt(i) == ')') {
                if (!stack1.arr.isEmpty() && stack1.arr.get(stack1.arr.size() - 1).equals('(')) {
                    stack1.pop();
                } else return false;
            }
            else if (text.charAt(i) == '}') {
                if (!stack1.arr.isEmpty() && stack1.arr.get(stack1.arr.size() - 1).equals('{')) {
                    stack1.pop();
                } else return false;
            }
            else if (text.charAt(i) == ']') {
                if (!stack1.arr.isEmpty() && stack1.arr.get(stack1.arr.size() - 1).equals('[')) {
                    stack1.pop();
                } else return false;
            }
        }

        return (stack1.arr.isEmpty());
    }
}



