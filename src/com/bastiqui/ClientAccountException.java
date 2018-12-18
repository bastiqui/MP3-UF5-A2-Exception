package com.bastiqui;

public class ClientAccountException extends Exception {
    //CLIENT MESSAGES
    public static final String WRONG_DNI = "DNI incorrecte";
    public static final String EMPTY_NOM = "El nom no pot estar buit";

    public ClientAccountException (String message) {
        super(message);
    }
}
