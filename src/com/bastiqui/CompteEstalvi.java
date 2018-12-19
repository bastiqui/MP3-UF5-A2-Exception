package com.bastiqui;

import java.util.ArrayList;
import java.util.List;

class CompteEstalvi {
    private String numCompte;
    private Double saldo;

    private List<Client> clients = new ArrayList<>();

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(Client clients) {
        this.clients.add(clients);
    }

    public CompteEstalvi(int position) {
        this.numCompte = String.format("%04d", position);
        this.saldo = 0.0;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void retirar (Double retirar) throws BankAccountException {
        if ((getSaldo() - retirar) < 0) {
            throw new BankAccountException(BankAccountException.INSUFICIENT_CREDITS);
        } else {
            setSaldo(getSaldo() - retirar);
        }
    }
}
