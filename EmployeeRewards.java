public class EmployeeRewards {
    // Function to determine the minimum number of rewards needed
    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        int[] rewards = new int[n];
        
        // Initially give 1 reward to each employee
        for (int i = 0; i < n; i++) {
            rewards[i] = 1;
        }       
        // First pass (left to right): Ensure that employees with higher ratings than their left neighbors get more rewards
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }
        
        // Second pass (right to left): Ensure that employees with higher ratings than their right neighbors get more rewards
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }       
        // Sum up the rewards
        int totalRewards = 0;
        for (int i = 0; i < n; i++) {
            totalRewards += rewards[i];
        }
        
        return totalRewards;
    }
    public static void main(String[] args) {
        // Example test cases for employee rewards
        System.out.println("Minimum Rewards Needed:");

        // Test Case 1
        int[] ratings1 = {1, 0, 2};
        System.out.println("Test Case 1: ratings = [1, 0, 2] -> " + minRewards(ratings1));  // Output: 5

        // Test Case 2
        int[] ratings2 = {1, 2, 2};
        System.out.println("Test Case 2: ratings = [1, 2, 2] -> " + minRewards(ratings2));  // Output: 4
    }
}
