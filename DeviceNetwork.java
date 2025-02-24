import java.util.*;

public class DeviceNetwork {

    // Class to represent an edge in the graph
    static class Edge {
        int u, v, cost;

        public Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
    }

    // Disjoint Set Union (DSU) or Union-Find structure
    static class DSU {
        int[] parent, rank;

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        // Find the root of the set containing x
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        // Union two sets containing x and y
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }

    // Function to find the minimum cost to connect all devices
    public static int minimumCost(int n, int[] modules, int[][] connections) {
        // Create a list to store all edges (including module edges and connection edges)
        List<Edge> edges = new ArrayList<>();

        // Add edges representing installation of modules
        for (int i = 0; i < n; i++) {
            edges.add(new Edge(n, i, modules[i])); // virtual node is node 'n', cost is modules[i]
        }

        // Add the actual connections between devices
        for (int[] conn : connections) {
            edges.add(new Edge(conn[0] - 1, conn[1] - 1, conn[2])); // adjusting index for 0-based array
        }

        // Sort the edges by cost to use in Kruskal's algorithm
        edges.sort(Comparator.comparingInt(e -> e.cost));

        // Initialize Disjoint Set Union (DSU) to manage the sets
        DSU dsu = new DSU(n + 1); // +1 because of the virtual node

        int totalCost = 0;
        int edgeCount = 0;

        // Apply Kruskal's algorithm
        for (Edge edge : edges) {
            int u = edge.u;
            int v = edge.v;
            int cost = edge.cost;

            // If u and v are not in the same set, include this edge in the MST
            if (dsu.find(u) != dsu.find(v)) {
                dsu.union(u, v);
                totalCost += cost;
                edgeCount++;
            }

            // If we've connected all devices, we can stop
            if (edgeCount == n) {
                break;
            }
        }

        // Return the minimum total cost to connect all devices
        return totalCost;
    }

    public static void main(String[] args) {
        // Test case input
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};
        
        // Get the minimum total cost
        int result = minimumCost(n, modules, connections);

        // Output the result
        System.out.println("Minimum Total Cost to Connect All Devices: " + result);
    }
}
