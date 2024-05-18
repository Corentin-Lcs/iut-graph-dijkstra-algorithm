package src.main.graphe.algos;

import src.main.graphe.core.IGrapheConst;

import java.util.*;

/**
 * Class that computes the shortest path between the source and the other vertices using the Dijkstra algorithm.
 * @author Corentin L. / Seweryn C.
 */

public final class Dijkstra {
    /**
     * Graph that will be used to compute the shortest path
     */
    private final IGrapheConst graph;

    /**
     * Distance between the source and the other vertices
     */
    private final Map<String, Integer> distance;

    /**
     * Previous nodes for each node, in order to go back to the source
     */
    private final Map<String, String> previous;

    /**
     * Priority queue to get the next node to visit
     */
    private final PriorityQueue<String> queue;

    /**
     * Set of nodes that have been visited
     */
    private final Set<String> visited;

    /**
     * Set of nodes that have been visited and processed
     */
    private final Set<String> processed;

    /**
     * Constructor of the Dijkstra class that initialize the data structures
     * @param graph Graph that will be used to compute the shortest path
     */
    private Dijkstra(IGrapheConst graph, String source) {
        this.graph = graph;
        int nbSommets = graph.getSommets().size();
        distance = new HashMap<>(nbSommets);
        previous = new HashMap<>(nbSommets);
        queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        visited = new HashSet<>(nbSommets);
        processed = new HashSet<>(nbSommets);
        dijkstra(source);
    }

    /**
     * Compute the shortest path between the source and the other vertices
     * @param graphe Graph that will be used to compute the shortest path
     * @param source Source node
     * @param dist [out] Distance between the source and the other vertices
     * @param pred [out] Previous nodes for each node, in order to go back to the source
     */
    public static void dijkstra(IGrapheConst graphe, String source, Map<String, Integer> dist, Map<String, String> pred) {
        Dijkstra pcc = new Dijkstra(graphe, source);
        dist.putAll(pcc.distance);
        pred.putAll(pcc.previous);
    }

    /**
     * Decrease the key of the node u in the priority queue
     * @param u Node to decrease the key
     */
    private void decreaseKey(String u) {
        queue.remove(u);
        queue.add(u);
    }

    /**
     * Compute the shortest path between the source and the other vertices using the Dijkstra algorithm
     * Inspired by "https://www.boost.org/doc/libs/1_39_0/libs/graph/doc/dijkstra_shortest_paths.html"
     * @param source Source node
     */
    private void dijkstra(String source) {
        distance.put(source, 0);
        queue.offer(source);
        while (!queue.isEmpty()) {
            String currentNode = queue.poll();
            if (processed.contains(currentNode))
                continue;
            for (String neighbourNode : graph.getSucc(currentNode)) {
                distance.putIfAbsent(neighbourNode, IGrapheConst.NO_EDGE);
                previous.putIfAbsent(neighbourNode, null);
                int newLength = graph.getValuation(currentNode, neighbourNode) + distance.get(currentNode);
                if (Objects.equals(distance.get(neighbourNode), IGrapheConst.NO_EDGE)) {
                    distance.put(neighbourNode, newLength);
                    previous.put(neighbourNode, currentNode);
                    visited.add(neighbourNode);
                    queue.add(neighbourNode);
                } else if (visited.contains(neighbourNode) && newLength < distance.get(neighbourNode)) {
                    distance.put(neighbourNode, newLength);
                    previous.put(neighbourNode, currentNode);
                    decreaseKey(neighbourNode);
                }
            }
            processed.add(currentNode);
        }
    }
}