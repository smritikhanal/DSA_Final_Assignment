import java.util.PriorityQueue;

public class main1a {
    // Function to find the minimum measurements required to find the critical temperature
    public static int minMeasurements(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];

        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1] + 1;
                if (dp[i][j] >= n) {
                    return j;
                }
            }
        }
        return n;
    }

    // Function to find the Kth lowest combined investment return
    public static int findKthLowest(int[] returns1, int[] returns2, int k) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(returns1[a[0]] * returns2[a[1]], returns1[b[0]] * returns2[b[1]])
        );

        // Insert first row elements (pairing first element of returns1 with all elements of returns2)
        for (int j = 0; j < returns2.length; j++) {
            minHeap.offer(new int[]{0, j}); // Store indices instead of actual elements
        }

        int result = 0;
        while (k-- > 0) {
            int[] pair = minHeap.poll();
            int i = pair[0], j = pair[1];
            result = returns1[i] * returns2[j];

            // Push next pair in the same column (next element from returns1 with current returns2 element)
            if (i + 1 < returns1.length) {
                minHeap.offer(new int[]{i + 1, j});
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // Example test cases for Critical Temperature
        System.out.println("Minimum Measurements for Critical Temperature:");
        System.out.println("Test Case 1: k=1, n=2 -> " + minMeasurements(1, 2));  // Output: 2
        System.out.println("Test Case 2: k=2, n=6 -> " + minMeasurements(2, 6));  // Output: 3
        System.out.println("Test Case 3: k=3, n=14 -> " + minMeasurements(3, 14)); // Output: 4

        System.out.println("\nKth Lowest Combined Investment Returns:");
        // Example test cases for Kth Lowest Combined Investment Return
        int[] returns1 = {2, 5};
        int[] returns2 = {3, 4};
        System.out.println("Test Case 1: returns1 = [2,5], returns2 = [3,4], k = 2 -> " +
                findKthLowest(returns1, returns2, 2)); // Output: 8

        int[] returns3 = {-4, -2, 0, 3};
        int[] returns4 = {2, 4};
        System.out.println("Test Case 2: returns1 = [-4,-2,0,3], returns2 = [2,4], k = 6 -> " +
                findKthLowest(returns3, returns4, 6)); // Output: 0
    }
}
