package com.bastiqui;

import static com.bastiqui.ClientAccountException.*;

public class Client {
    private String nom;
    private String dni;
    private String check_dni = "^[0-9]{8}+[A-z]";

    Client(String nom, String dni) throws ClientAccountException {
        if (nom.isEmpty()) {
            throw new ClientAccountException(EMPTY_NOM);
        } else {
            this.nom = nom;
        }
        if (dni.matches(check_dni)) {
            this.dni = dni;
        } else {
            throw new ClientAccountException(WRONG_DNI);
        }
    }

    public String getNom() {
        return nom;
    }

    public String getDni() {
        return dni;
    }
}