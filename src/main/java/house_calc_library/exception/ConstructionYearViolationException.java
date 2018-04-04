package house_calc_library.exception;

/**
 * <p>ConstructionYearViolationException class.</p>
 *
 * @author Kamil Cieślik
 * @version $Id: $Id
 */
public class ConstructionYearViolationException extends Exception {
    /**
     * <p>Constructor for ConstructionYearViolationException.</p>
     *
     * @param message a {@link String} object.
     * @param cause a {@link Throwable} object.
     */
    public ConstructionYearViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
