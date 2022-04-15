package MonotoneStack;

import java.util.*;

public class MonotoneStack {

    public static int[][] getNearLessNoRepeat(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[][] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int popIndex = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int popIndex = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            res[popIndex][0] = leftLessIndex;
            res[popIndex][1] = -1;
        }
        return res;
    }

    public static int[][] getNearLess(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            // System.out.println("==" + i);
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popList = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer curr : popList) {
                    res[curr][0] = leftLessIndex;
                    res[curr][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(Integer.valueOf(i));
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }

        while (!stack.isEmpty()) {
            List<Integer> popList = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer curr : popList) {
                res[curr][0] = leftLessIndex;
                res[curr][1] = -1;
            }
        }
        return res;
    }

    public static int maxIndicator(int[] arr) {
        int[] sumArr = new int[arr.length];
        sumArr[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sumArr[i] = sumArr[i - 1] + arr[i];
        }
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int curr = stack.pop();
                max = Math.max(max,
                        (stack.isEmpty()
                                ? sumArr[i - 1]
                                : (sumArr[i - 1] - sumArr[stack.peek()])) * arr[curr]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int curr = stack.pop();
            max = Math.max(max,
                    (stack.isEmpty()
                            ? sumArr[arr.length - 1]
                            : (sumArr[arr.length - 1] - sumArr[stack.peek()])) * arr[curr]);
        }
        return max;
    }

    public static void main(String[] args) {

        int[] noRepeat = new int[] { 6, 5, 7, 2, 3, 4, 9, 2, 8 };
        int[] repeat = new int[] { 4, 6, 2, 7, 5, 5, 7, 4, 3 };
        int[] arr = new int[] { 3, 7, 5, 2, 8, 7, 6, 3, 9 };

        int[][] noRepeatAns = getNearLessNoRepeat(noRepeat);
        int[][] repeatAns = getNearLess(repeat);

        System.out.println("no repeat answer:");
        for (int[] row : noRepeatAns) {
            System.out.print("[ ");
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.print("]");
        }
        System.out.println();

        System.out.println("repeat answer:");
        for (int[] row : repeatAns) {
            System.out.print("[ ");
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.print("]");
        }
        System.out.println();

        System.out.println("max indicator: " + maxIndicator(arr));
    }
}
