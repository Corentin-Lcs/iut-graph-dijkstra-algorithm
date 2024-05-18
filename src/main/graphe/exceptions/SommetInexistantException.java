package src.main.graphe.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a node does not exist, and it is expected to.
 * @author Corentin L. / Seweryn C.
 */

public class SommetInexistantException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 5L;

    /**
     * Default constructor with predefined message
     */
    public SommetInexistantException() {
        super("Le sommet n'existe pas.");
    }
}