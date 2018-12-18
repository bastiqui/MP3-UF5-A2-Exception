package com.bastiqui;

public class BankAccountException extends Exception {
    //BANK ACCOUNT MESSAGES
    public static final String ACCOUNT_NOT_FOUND = "Compte inexistent";
    public static final String ACCOUNT_OVERDRAFT = "Compte al descobert";
    public static final String ACCOUNT_ZERO_USER = "Compte sense usuari";

    //OPERATIONS
    public static final String TRANSFER_ERROR = "Error en la transferència";
    public static final String NEGATIVE_TRANSFER = "La transferència not por ser negativa";
    public static final String SAME_ACCOUNT = "No es pot transferir al mateix compte";
    public static final String INSUFICIENT_CREDITS= "No tens suficients diners";

    public BankAccountException(String message) {
        super(message);
    }
}