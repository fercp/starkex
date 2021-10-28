package com.fersoft.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Base exception class for all exceptions
 * I11N supported
 *
 * @author ferat capar
 */
public class StarkException extends Exception {
    private final String messageKey;
    private final Locale locale;
    private final Object[] params;

    public StarkException(String messageKey, Object... params) {
        this(messageKey, params, Locale.getDefault());
    }

    public StarkException(String messageKey, Object[] params, Locale locale) {
        this.messageKey = messageKey;
        this.locale = locale;
        this.params = params;
    }

    /**
     * returns localized exception message
     * reads from  <b>exceptions</b> bundle using keys in format exception.{messagekey}
     *
     * @return localized message
     */
    @Override
    public String getLocalizedMessage() {
        String message = ResourceBundle.getBundle("exceptions", locale)
                .getString(messageKey);
        return MessageFormat.format(message, params);
    }
}
