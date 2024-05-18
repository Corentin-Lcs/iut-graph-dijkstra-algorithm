package src.main.graphe.algos;

import src.main.graphe.core.IGrapheConst;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class used for timing Dijkstra's algorithm and reconstructing the shortest path.
 * @author Corentin L. / Seweryn C.
 */

public enum DijkstraTools {
    ;

    public static long time(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {
        long debut = System.nanoTime();
        Dijkstra.dijkstra(g, source, dist, prev);
        long fin = System.nanoTime();
        return (fin - debut) / 1000000;
    }

    // Reconstruction of the shortest path (in prev) from source to dest starting from dest
    public static List<String> getPath(String source, String dest, Map<String, String> prev) {
        LinkedList<String> path = new LinkedList<>();
        for (String sommet = dest; null != sommet; sommet = prev.get(sommet))
            path.addFirst(sommet);
        return path;
    }
}