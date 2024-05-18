package src.main.graphe.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an arc already exists.
 * @author Corentin L. / Seweryn C.
 */

public class ArcExistantException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 3L;

    /**
     * Default constructor with predefined message
     */
    public ArcExistantException() {
        super("L'arc existe déjà.");
    }
}