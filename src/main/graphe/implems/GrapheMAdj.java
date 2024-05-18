package src.main.graphe.implems;

import src.main.graphe.core.IGraphe;
import src.main.graphe.core.IGrapheConst;
import src.main.graphe.exceptions.*;

import java.util.*;

/**
 * Represents a graph with an adjacency matrix.
 * Uses a vector approach to extend the matrix when it is full resulting in faster execution times.
 * Implementation prioritizes speed over memory usage.
 * @author Corentin L. / Seweryn C.
 */

public class GrapheMAdj implements IGraphe {
    /**
     * Map of the indexes of the nodes in the adjacency matrix, matches a name to an index
     */
    private final Map<String, Integer> indices;

    /**
     * Adjacency matrix representation of the graph
     */
    private int[][] matrice;

    /**
     * Number of nodes in the graph
     */
    private int nbNodes;

    /**
     * Extension of the adjacency matrix when it is full
     * The new size is the old size times this value
     */
    private static final int EXTENSION_STEP = 2;

    /**
     * Default constructor of the class
     * Creates an empty graph
     */
    public GrapheMAdj() {
        indices = new HashMap<>();
        matrice = new int[0][0];
        nbNodes = 0;
    }

    /**
     * Creates a graph from a string representation
     * @param str The string representation of the graph
     * @see IGraphe#peupler(String)
     */
    public GrapheMAdj(String str) {
        this();
        peupler(str);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            indices.put(noeud, nbNodes++);
            if (matrice.length < nbNodes)
                extendMatrix();
        }
    }

    /**
     * Extends the adjacency matrix by {@link #EXTENSION_STEP} times its size
     * Copies the old matrix into the new one
     * The new matrix is filled with {@link IGrapheConst#NO_EDGE} values
     */
    private void extendMatrix() {
        int newSize = matrice.length == 0 ? 1 : matrice.length * EXTENSION_STEP;
        int[][] newMatrice = new int[newSize][newSize];
        int extensionLimit = newSize / EXTENSION_STEP;
        for (int i = 0; i < newSize; i++)
            for (int j = 0; j < newSize; j++)
                newMatrice[i][j] = (i < extensionLimit && j < extensionLimit) ? matrice[i][j] : IGrapheConst.NO_EDGE;
        matrice = newMatrice;
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination))
            throw new ArcExistantException();
        if (source.isEmpty() || destination.isEmpty())
            throw new EmptySommetException();
        if (0 > valeur)
            throw new ArcValuationNegativeException();
        if (!contientSommet(source))
            ajouterSommet(source);
        if (!contientSommet(destination))
            ajouterSommet(destination);
        matrice[indices.get(source)][indices.get(destination)] = valeur;
    }

    @Override
    public void oterSommet(String noeud) {
        if (contientSommet(noeud)) {
            removeIndexFromMatrix(indices.get(noeud));
            indices.remove(noeud);
        }
    }

    /**
     * Removes the index from the adjacency matrix
     * Doesn't refactor the matrix (speed over memory usage)
     * @param index The index to remove
     */
    private void removeIndexFromMatrix(int index) {
        for (int i = 0; i < matrice.length; i++) {
            matrice[i][index] = IGrapheConst.NO_EDGE;
            matrice[index][i] = IGrapheConst.NO_EDGE;
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (contientArc(source, destination))
            matrice[indices.get(source)][indices.get(destination)] = IGrapheConst.NO_EDGE;
        else
            throw new ArcInexistantException();
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        if (null == this.indices)
            return sommets;
        sommets.addAll(indices.keySet());
        Collections.sort(sommets);
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        if (!contientSommet(sommet))
            throw new SommetInexistantException();
        List<String> succ = new ArrayList<>();
        for (int i = 0; i < nbNodes; ++i)
            if (IGrapheConst.NO_EDGE != this.matrice[indices.get(sommet)][i])
                succ.add(getKeyFromValue(i));
        Collections.sort(succ);
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (!contientSommet(src) || !contientSommet(dest))
            throw new SommetInexistantException();
        if (!contientArc(src, dest))
            return IGrapheConst.NO_EDGE;
        return matrice[indices.get(src)][indices.get(dest)];
    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (!contientSommet(src) || !contientSommet(dest))
            return false;
        return IGrapheConst.NO_EDGE != this.matrice[this.indices.get(src)][this.indices.get(dest)];
    }

    /**
     * Utility method to get the key from a value in a map
     * @param value The value to search for
     * @return The key associated with the value, or null if the value is not found
     */
    private String getKeyFromValue(int value) {
        for (Map.Entry<String, Integer> entry : indices.entrySet()) {
            if (entry.getValue() == (value)) {
                return entry.getKey();
            }
        }
        throw new SommetInexistantException();
    }

    @Override
    public String toString() {
        return this.toAString();
    }
}