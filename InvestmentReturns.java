import java.util.PriorityQueue;

public class InvestmentReturns {

    // Function to find the Kth lowest combined investment return
    public static int findKthLowest(int[] returns1, int[] returns2, int k) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0] * a[1], b[0] * b[1])
        );

        // Insert all possible pairs into the minHeap
        for (int num1 : returns1) {
            for (int num2 : returns2) {
                minHeap.offer(new int[]{num1, num2});
            }
        }

        // Extract k times to get the k-th smallest product
        int result = 0;
        while (k-- > 0) {
            int[] pair = minHeap.poll();
            result = pair[0] * pair[1];
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Kth Lowest Combined Investment Returns:");

        // Example test cases
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
