package exercises_day_03;

import java.util.*;

public class Balls_n_Bins {
    // Balls and bins implement the power of one choice and the power of two choices.
    // The calculations for one choice, say that if the table size is N, The bins must be able to contain
    // ln(N)/ln(ln(N)).
    // For the two implementations of 10007 and later with 32749 the bins should contain
    // L1(10007) = ln(10007)/ln(ln(10007)) = 9,211/2,220 =4,148 so 4-5
    // L1(32749) = ln(32749)/ln(ln(32749)) = 10,397/2,341 = 4,440 so also 4-5 closer to 5
    // The calculations for two choices, say that if the table size is N, The bins must be able to contain
    // L2(10007) = ln(ln(10007)/ln(2) = 2,220/0,693 = 3,203
    // L2(32749) = ln(ln(32749)/ln(2) = 2,341/0,693 = 3,378

    int size1 = 10007;
    int size2 = 32749;

    int bin_amount1 = (int) (Math.log(size1) / Math.log(Math.log(size1)));
    int bin_amount2 = (int) (Math.log(size2) / Math.log(Math.log(size2)));

    int[] list1 = new int[size1];
    int[] list2 = new int[size2];

    public void populateByPowerOfOneChoice(int[] list) {
        Random r = new Random();
        Map<Integer, Integer> map1 = new HashMap<>();
        for (Integer i = 0; i < list.length; i++) {
            int num = r.nextInt(list.length);
            // Generating a random number from 0 to available slots
            // some numbers will be the same as a number previously found
            // and this resembles collision of a hash table.
            // The loop ensures the same amount of keys made,
            // as available slots.
            if (map1.get(num) == null) {
                map1.put(num, 1);
            } else {
                int m = map1.get(num) + 1;
                map1.put(num, m);
            }

        }
        int maxBin = 0;
        for (int j = 0; j < list.length; j++) {
            if (map1.get(j) != null) {
                if (map1.get(j) > maxBin) {
                    maxBin = map1.get(j);
                }
            }

        }
        System.out.println(maxBin);
    }

    public void populateByPowerOfTwoChoices(int[] list) {
        Random r = new Random();
        Map<Integer, Integer> map1 = new HashMap<>();
        for (Integer i = 0; i < list.length; i++) {
            int num1 = r.nextInt(list.length);
            int num2 = r.nextInt(list.length);
            Integer m1 = 0,
                    m2 = 0;
            if (map1.get(num1) == null) {
                m1 = 0;
            } else {
                m1 = map1.get(num1);
            }
            if (map1.get(num2) == null) {
                m2 = 0;
            } else {
                m2 = map1.get(num2);
            }
            if (m1 > m2) {
                map1.put(num2, m2 + 1);
            } else {
                map1.put(num1, m1 + 1);
            }


        }
        int maxBin = 0;
        for (int j = 0; j < list.length; j++) {
            if (map1.get(j) != null) {
                if (map1.get(j) > maxBin) {
                    maxBin = map1.get(j);
                }
            }

        }
        System.out.println(maxBin);
    }

    public int testSquaredSize(int[] list) {
        Random r = new Random();
        int size = list.length * list.length;
        int[] longList = new int[size];
        for (Integer i = 0; i < list.length; i++) {
            int num = r.nextInt(size);
            longList[num] += 1;
        }
        int maxBin = 0;
        for (int j = 0; j < size; j++) {
            if (longList[j] > maxBin) {
                maxBin = longList[j];
            }

        }
        return maxBin;
    }

    public static void main(String[] args) {
        Balls_n_Bins ballsNBins = new Balls_n_Bins();


        System.out.println("Testing power of one choice with list of length " + ballsNBins.list1.length);
        ballsNBins.populateByPowerOfOneChoice(ballsNBins.list1);
        System.out.println("Testing power of two choices with list of length " + ballsNBins.list1.length);
        ballsNBins.populateByPowerOfTwoChoices(ballsNBins.list1);
        System.out.println("Testing power of one choice with list of length " + ballsNBins.list2.length);
        ballsNBins.populateByPowerOfOneChoice(ballsNBins.list2);
        System.out.println("Testing power of two choices with list of length " + ballsNBins.list2.length);
        ballsNBins.populateByPowerOfTwoChoices(ballsNBins.list2);

        System.out.println("Test");
        int p = 0;
        for (int i = 0; i < 100; i++) {
            if (ballsNBins.testSquaredSize(ballsNBins.list1) > 1) {
                p += 1;
            }
        }

        System.out.println("Average = " + (p) + "% of executions needed more than one bin");


    }
}

