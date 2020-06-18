package br.com.navita.controlepatrimonial.exception;

/**
 * The type Record already exists exception.
 */
public class RecordAlreadyExistsException extends Throwable {
    /**
     * Instantiates a new Record already exists exception.
     */
    public RecordAlreadyExistsException() {
    }

    /**
     * Instantiates a new Record already exists exception.
     *
     * @param msg the msg
     */
    public RecordAlreadyExistsException(String msg) {
        super(msg);
    }
}
