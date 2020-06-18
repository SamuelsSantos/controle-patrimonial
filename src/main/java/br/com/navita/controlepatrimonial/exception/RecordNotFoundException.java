package br.com.navita.controlepatrimonial.exception;

/**
 * The type Record not found exception.
 */
public class RecordNotFoundException extends Throwable {
    /**
     * The constant MSG_RECORD_NAO_EXISTE.
     */
    public static final String MSG_RECORD_NAO_EXISTE = "Erro: O registro %d não existe.";

    /**
     * Instantiates a new Record not found exception.
     *
     * @param msg the msg
     */
    public RecordNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Record not found exception.
     *
     * @param id the id
     */
    public RecordNotFoundException(Long id) {
        super(String.format(MSG_RECORD_NAO_EXISTE, id));
    }
}
