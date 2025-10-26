package portfolio_1;

import java.util.HashMap;
import java.util.Map;

public class Portfolio1 {
    public static boolean additive(String s) {
        int length = s.length();
        if (length < 3) {
            return false;
        }
        int a = s.charAt(0) - '0';
        int b = s.charAt(1) - '0';
        int c = s.charAt(2) - '0';
        if (c == a + b) {
            return true;
        } return additive(s.substring(1));
     }

    public static void main(String[] args) {
        System.out.println(additive("54682455555564598"));
        System.out.println(myMethod(30));
        System.out.println(logTo(1024));
        System.out.println(candidate(new int[]{2,2,3,9,9,9,9,9,5}));
    }

    public static long myMethod(int n)
    {
        if (n <= 1)
            return 1;
        else
            return myMethod(n-1) + myMethod(n-2);
    }

    public static int logTo(int N) {
        if (N == 1) {
            return 0;
        }
        return 1 + logTo(N/2);
    }

//    public static int candidate(int[] votes) {
//        Map<Integer, Integer> candidates = new HashMap();
//        int majority = (votes.length/2);
//        for (int v: votes) {
//            if (candidates.get(v) == null) {
//                candidates.put(v, 1);
//            } else {
//                int vote = candidates.get(v) + 1;
//                candidates.put(v, vote);
//                if (vote > majority) {
//                    System.out.println("v: " + v + ", votes: " + candidates.get(v) + ", majority: " + majority);
//                    return v;
//                }
//            }
//        } return -1;
//    }

    public static int candidate(int[] votes) {
        Map<Integer, Integer> candidates = new HashMap<>();
        int majority = votes.length / 2;

        for (int v : votes) {
            int count = candidates.getOrDefault(v, 0) + 1;
            candidates.put(v, count);

            // Tjek her hver gang
            if (count > majority) {
                System.out.println("v: " + v + ", votes: " + count + ", majority: " + majority);
                return v;
            }
        }
        return -1;
    }
}
