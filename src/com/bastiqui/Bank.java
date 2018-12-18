package com.bastiqui;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Client> clients = new ArrayList<>();
    private String numCompte[] = new String[20];
    private double saldo[] = new double[20];

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(Client clients, int count) {
        this.clients.add(clients);
        setNumCompte(count);
    }

    public String getNumCompte(int position) {
        return numCompte[position];
    }

    public void setNumCompte(int position) {
        this.numCompte[position] = String.format("%04d", position);
        // S'inicia amb saldo de 0 €
        setSaldo(0, position);
    }

    public double getSaldo(int position) {
        return saldo[position];
    }

    public void setSaldo(double saldo, int position) {
        this.saldo[position] = saldo;
    }

    public void transferencia(double quantitat, int origenPosition, int destiPosition) throws BankAccountException {
        if (quantitat > getSaldo(origenPosition)) throw new BankAccountException(BankAccountException.INSUFICIENT_CREDITS); // Si la quantitat es major que la que te la persona
        else if (quantitat < 0) throw new BankAccountException(BankAccountException.NEGATIVE_TRANSFER); // Si la quantitat es negativa
        else  if (quantitat == 0) throw new BankAccountException(BankAccountException.TRANSFER_ERROR); // Si la quantitat es 0
        else if (numCompte[origenPosition].equals(numCompte[destiPosition])) throw new BankAccountException(BankAccountException.SAME_ACCOUNT); // Si el compte desti i l'origen es el mateix
        else {
            setSaldo((getSaldo(origenPosition) - quantitat), origenPosition);
            setSaldo((getSaldo(destiPosition) + quantitat), destiPosition);
        }
    }

    public void retirar(double a, int position) throws BankAccountException{
        if((saldo[position] - a) >= 0) saldo[position] -= a;
        else throw new BankAccountException(BankAccountException.INSUFICIENT_CREDITS);
    }
}