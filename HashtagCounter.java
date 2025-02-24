import java.util.*;

public class HashtagCounter {
    public static class HashtagCount implements Comparable<HashtagCount> {
        String hashtag;
        int count;
        
        public HashtagCount(String hashtag, int count) {
            this.hashtag = hashtag;
            this.count = count;
        }
        
        @Override
        public int compareTo(HashtagCount other) {
            // Sort by count descending, then by hashtag alphabetically
            if (this.count != other.count) {
                return Integer.compare(other.count, this.count); // Descending order
            }
            return this.hashtag.compareTo(other.hashtag); // Ascending order for hashtags
        }
    }
    
    public static List<HashtagCount> countHashtags(List<String> tweets) {
        // Map to store hashtag counts
        Map<String, Integer> hashtagCounts = new HashMap<>();
        
        // Process each tweet
        for (String tweet : tweets) {
            // Split tweet into words (simple split by spaces)
            String[] words = tweet.split("\\s+");
            
            for (String word : words) {
                // Check if word is a hashtag (starts with #)
                if (word.startsWith("#")) {
                    // Remove any punctuation and get just the hashtag
                    String hashtag = word.replaceAll("[^a-zA-Z0-9#]", "");
                    if (!hashtag.isEmpty() && hashtag.length() > 1) { // Ensure valid hashtag
                        hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                    }
                }
            }
        }
        
        // Convert to list of HashtagCount objects
        List<HashtagCount> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : hashtagCounts.entrySet()) {
            result.add(new HashtagCount(entry.getKey(), entry.getValue()));
        }
        
        // Sort according to the requirements
        Collections.sort(result);
        
        return result;
    }
    
    public static void printHashtagTable(List<HashtagCount> hashtags) {
        System.out.println("Output:");
        System.out.println("| hashtag    | count |");
        System.out.println("|------------|-------|");
        for (HashtagCount hc : hashtags) {
            System.out.printf("| %-10s | %5d |\n", hc.hashtag, hc.count);
        }
    }
    
    public static void main(String[] args) {
        // Example tweets based on the description
        List<String> tweets = new ArrayList<>();
        // Assuming tweets IDs 13,14,17 have #HappyDay and tweets 15,18 have #TechLife
        tweets.add("Tweet 13 #HappyDay");
        tweets.add("Tweet 14 #HappyDay");
        tweets.add("Tweet 17 #HappyDay");
        tweets.add("Tweet 15 #TechLife");
        tweets.add("Tweet 18 #TechLife");
        
        List<HashtagCount> result = countHashtags(tweets);
        printHashtagTable(result);
    }
}
