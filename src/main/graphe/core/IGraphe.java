package src.main.graphe.core;

/**
 * Interface for graphs.
 * @author Corentin L. / Seweryn C.
 */

public interface IGraphe extends IGrapheConst {
    /**
     * Adds a node to the graph
     * If the node already exists, nothing happens
     * @param noeud The node to add
     */
    void ajouterSommet(String noeud);

    /**
     * Adds a directed edge to the graph
     * @param source The source node
     * @param destination The destination node
     * @param valeur The value of the edge
     * @throws src.main.graphe.exceptions.ArcExistantException If the edge already exists
     * @throws src.main.graphe.exceptions.ArcValuationNegativeException If the edge value is negative
     * @throws src.main.graphe.exceptions.EmptySommetException If one of the nodes (source or destination) is empty
     */
    void ajouterArc(String source, String destination, Integer valeur);

    /**
     * Removes a node from the graph
     * If the node does not exist, nothing happens
     * @param noeud The node to remove
     */
    void oterSommet(String noeud);

    /**
     * Removes a directed edge from the graph
     * If the edge does not exist, nothing happens
     * @param source The source node
     * @param destination The destination node
     * @throws src.main.graphe.exceptions.ArcInexistantException If the edge does not exist
     */
    void oterArc(String source, String destination);

    /**
     * Builds a graph from a string
     * @param str The string to parse in format "A-B(5), A-C(10), B-C(3), C-D(8), E:"
     */
    default void peupler(String str) {
        assert this.getSommets().isEmpty();
        String[] arcs = str.split(",\\s*");
        for (String arc : arcs) {
            String[] elements = arc.trim().split("-");
            // Extract the source node and remove ':' if necessary in the name
            String src = elements[0].replaceAll(":", "");
            ajouterSommet(src);
            if (1 < elements.length && !elements[1].isEmpty()) {
                String[] targets = elements[1].split(",\\s*");
                for (String target : targets) {
                    String dest = target.substring(0, target.indexOf('('));
                    int val = Integer.parseInt(target.substring(target.indexOf('(') + 1, target.indexOf(')')));
                    ajouterArc(src, dest, val);
                }
            }
        }
    }
}