package graphs;

import java.util.*;

/**
 * Author: Alexis Englebert
 * Context: You are operating a power plant in the new city of Louvain-La-Neuve,
 * but lack plans for the city's electrical network.
 * Your goal is to minimize the cost of electrical wires ensuring the city is connected with just one wire.
 *
 * The method 'minimumSpanningTreeCost' is designed to find the minimum cost to connect all cities in a given electrical network.
 * The network is represented as a graph where the nodes are the buildings, the edges are the possible connections
 * and their associated cost.
 *
 * Example:
 * Given a network with three buildings (nodes) and the cost of wires (edges) between them:
 * 0 - 1 (5), 1 - 2 (10), 0 - 2 (20)
 * The minimum cost to connect all the buildings is 15 (5 + 10).
 *
 * Note: The method assumes that the input graph is connected and the input is valid.
 */
public class Electricity {

    /**
     * @param n       The number of buildings (nodes) in the network.
     * @param edges   A 2D array where each row represents an edge in the form [building1, building2, cost].
     *                The edges are undirected so (building2, building1, cost) is equivalent to (building1, building2, cost).
     * @return       The minimum cost to connect all cities.
     */
    public static int minimumSpanningCost(int n, int [][] edges) {
        int[] parent = new int[n];
        int totalcost = 0;
        Arrays.sort(edges, Comparator.comparingInt(a -> a[2]));

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int cpm = 0;
        for(int[] edge : edges){
            if(find(edge[0],parent) != find(edge[1],parent)){
                union(edge[0], edge[1],parent);
                totalcost += edge[2];
                cpm ++;
                if(cpm == n - 1){
                    return totalcost;
                }
            }
        }

         return totalcost;


    }

    static int find(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = find(parent[x], parent);
        }
        return parent[x];
    }

    static void union(int x, int y, int[] parent ) {
        int px = find(x, parent);
        int py = find(y, parent);
        if (px != py) {
            parent[px] = py;
        }
    }


}
