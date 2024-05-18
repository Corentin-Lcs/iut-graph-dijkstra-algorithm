package src.main.graphe.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a node is empty, but shouldn't.
 * @author Corentin L. / Seweryn C.
 */

public class EmptySommetException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 2L;

    /**
     * Default constructor with predefined message
     */
    public EmptySommetException() {
        super("Un arc ne peut pas avoir une source ou une destination vide");
    }
}