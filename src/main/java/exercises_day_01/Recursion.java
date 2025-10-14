package exercises_day_01;

import java.sql.Array;

class Recursion {

    public static int sum(int n) {
        if (n <= 0) {
            return 0;
        } else {
            return n*2 - 1 + sum(n - 1);
        }
    }

    public static int evenSquares(int n) {
        if (n <= 0) {
            return 0;
        } else {
            return 4*n*n + evenSquares(n - 1);
        }
    }

    public static int sumOfQubes(int n) {
        if (n <= 0) {
            return 0;
        } else {
            return n*n*n + sumOfQubes(n-1);
        }
    }

    public static int fib(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    //returns true if string s with the length l contains
    //char c, otherwise false.
    public static boolean linear(String s, char c, int l) {
        if (l <= 0) {
            return false;
        } else {
            if (s.charAt(l -1) == c) {
                return true;
            } else {
                return linear(s, c, l - 1);
            }
        }
    }
    //returns true if value is in arr, otherwise false
    //the elements in arr are sorted

    public static boolean binarySearch(int[] arr, int value) {
        if (arr.length <= 0) {
            return false;
        } else {
            int mid = arr.length/2;


            // This part is for the low end of the array
            if (value < arr[mid]) {
                int newarr[] = new int[mid];
                for (int i = 0; i < mid; i++) {
                    newarr[i] = arr[i];
                }

                return binarySearch(newarr, value);
            }
            // This part is for the high end of the array
            if (value > arr[mid]) {
                int newarr[] = new int[arr.length - (mid + 1)];
                for (int i = mid + 1; i < arr.length; i++) {
                    newarr[i - (mid + 1)] = arr[i];
                }
                return binarySearch(newarr, value);

            }
            if (value == arr[mid]) {
                return true;
            }
            return false;
        }
    }

    public static int binaryIndex(int[] arr, int target) {
        int l = 0;
        int r = arr.length - 1;
        while (l <= r) {
            int mid = (r - l)/2;
            if (target < arr[mid]) {
                r = mid -1;
            } if (target > arr[mid]) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Checking sum: "+sum(5));
        System.out.println("Checking sumOfQubes: "+sumOfQubes(3));
        System.out.println("Checking evenSquares: "+evenSquares(7));
        System.out.println("Checking fib: "+fib(7));
        System.out.println("Checking linear: "+linear("johnny ", ' ', 7));
        int[] arr= {1,2,5,9,15,25,27,31,55,101};
        System.out.println("Checking binarySearch: "+binarySearch(arr, 102));
        System.out.println("Checking binaryIndex: "+binaryIndex(arr, 9));
    }
}


