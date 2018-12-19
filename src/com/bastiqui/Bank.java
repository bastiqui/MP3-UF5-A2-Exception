package com.bastiqui;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<CompteEstalvi> llistaComptes = new ArrayList<>();

    public void removeClient (String dni, int position) throws BankAccountException {
        if (llistaComptes.get(position).getClients().size() > 1) {
            llistaComptes.removeIf(compte -> dni.equals(llistaComptes.get(position).getClients().get(0).getDni()));
        } else {
            throw new BankAccountException(BankAccountException.ACCOUNT_ZERO_USER);
        }
    }

    public List<CompteEstalvi> getLlistaComptes() {
        return llistaComptes;
    }

    public void setLlistaComptes(CompteEstalvi llistaComptes) {
        this.llistaComptes.add(llistaComptes);
    }

    public void transferencia(double quantitat, int compteOrigen, int compteDesti) throws BankAccountException {
        if (quantitat > llistaComptes.get(compteOrigen).getSaldo()) throw new BankAccountException(BankAccountException.INSUFICIENT_CREDITS); // Si la quantitat es major que la que te la persona
        else if (quantitat < 0) throw new BankAccountException(BankAccountException.NEGATIVE_TRANSFER); // Si la quantitat es negativa
        else  if (quantitat == 0) throw new BankAccountException(BankAccountException.TRANSFER_ERROR); // Si la quantitat es 0
        else if (llistaComptes.equals(llistaComptes.get(compteOrigen).getNumCompte())) throw new BankAccountException(BankAccountException.SAME_ACCOUNT); // Si el compte desti i l'origen es el mateix
        else {
            llistaComptes.get(compteOrigen).setSaldo((llistaComptes.get(compteOrigen).getSaldo()) - quantitat);
            llistaComptes.get(compteDesti).setSaldo((llistaComptes.get(compteDesti).getSaldo()) + quantitat);
        }
    }

    public int trobarCompte (String buscarCompte) {
        boolean trobat = false;

        for (int i = 0; i < llistaComptes.size(); i++) {
            // Es mira si existeix el compte
            if (buscarCompte.equals(llistaComptes.get(i).getNumCompte())) {
                trobat = true;
            }

            if (trobat) {
                return i;
            }
        }
        return -1;
    }

    public int donarBaixaClient (String dni) {
        boolean trobat = false;

        for (int i = 0; i < llistaComptes.size(); i++) {
            if (dni.equals(llistaComptes.get(i).getClients().get(0).getDni())) {
                trobat = true;
            }

            if (trobat) {
                return i;
            }
        }
        return -1;
    }
}