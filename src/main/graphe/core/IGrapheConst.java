package src.main.graphe.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Interface for constant graphs.
 * @author Corentin L. / Seweryn C.
 */

public interface IGrapheConst {
    /**
     * Represents that there is no edge between nodes
     */
    final int NO_EDGE = -1;

    /**
     * Returns the list of nodes
     * @return The list of nodes
     */
    List<String> getSommets();

    /**
     * Returns the list of edges
     * @param sommet The node
     * @return The list of edges
     * @throws src.main.graphe.exceptions.SommetInexistantException If the node is not in the graph
     */
    List<String> getSucc(String sommet);

    /**
     * Returns the value of an edge
     * @param src  The source node
     * @param dest The destination node
     * @return The value of the edge, -1 if the edge is not in the graph
     * @see IGrapheConst#NO_EDGE
     */
    int getValuation(String src, String dest);

    /**
     * Verifies if a node is in the graph
     * @param sommet The node
     * @return true if the node is in the graph, false otherwise
     */
    boolean contientSommet(String sommet);

    /**
     * Verifies if an edge is in the graph
     * @param src  The source node
     * @param dest The destination node
     * @return true if the edge is in the graph, false otherwise
     */
    boolean contientArc(String src, String dest);

    /**
     * Returns a string representation of the graph
     */
    default String toAString() {
        List<String> sommetsTries = new ArrayList<>(getSommets());
        Collections.sort(sommetsTries);
        List<String> descriptionsArcs = new ArrayList<>();
        for (String sommet : sommetsTries) {
            List<String> successeurs = getSucc(sommet);
            if (successeurs.isEmpty()) {
                descriptionsArcs.add(sommet + ":");
            } else {
                List<String> successeursTries = new ArrayList<>(successeurs);
                Collections.sort(successeursTries);
                for (String successeur : successeursTries) {
                    int poids = getValuation(sommet, successeur);
                    descriptionsArcs.add(sommet + "-" + successeur + "(" + poids + ")");
                }
            }
        }
        return String.join(", ", descriptionsArcs);
    }
}