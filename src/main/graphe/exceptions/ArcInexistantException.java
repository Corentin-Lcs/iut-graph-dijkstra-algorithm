package src.main.graphe.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an arc does not exist, and it is expected to.
 * @author Corentin L. / Seweryn C.
 */

public class ArcInexistantException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 4L;

    /**
     * Default constructor with predefined message
     */
    public ArcInexistantException() {
        super("Arc inexistant.");
    }
}