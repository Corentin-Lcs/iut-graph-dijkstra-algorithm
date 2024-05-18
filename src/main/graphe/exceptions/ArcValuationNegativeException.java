package src.main.graphe.exceptions;

import java.io.Serial;

/**
 * Exception thrown when we try to add an arc with a negative valuation.
 * @author Corentin L. / Seweryn C.
 */

public class ArcValuationNegativeException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor with predefined message
     */
    public ArcValuationNegativeException() {
        super("Un arc ne peut pas avoir une valuation négative");
    }
}