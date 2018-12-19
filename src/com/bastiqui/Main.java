package com.bastiqui;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Client client = null;
    private static CompteEstalvi compte = null;

    public static void main(String[] args) {
        Bank bank = new Bank();

        int count = 0;
        boolean sortir = false;

        do {
            System.out.println("|------ Menu -------|");
            System.out.println("| 1 - Donar d'alta  |");
            System.out.println("| 2 - Access client |");
            System.out.println("| 3 - Donar baixa   |");
            System.out.println("| 5 - Sortir        |");
            System.out.println("|-------------------|");
            System.out.print("Escull: ");
            int opcio = scanner.nextInt();
            scanner.nextLine();

            switch (opcio) {
                case 1:
                    count = (altaClient(bank, count));
                    break;
                case 2:
                    // Comprovacio que n'hi hagin clients en el banc
                    if (bank.getLlistaComptes().isEmpty()) {
                        System.out.println("No hi ha cap client en aquest banc.");
                    } else {
                        accessClient(bank);
                    }
                    break;
                case 3:
                    baixaClient(bank);
                    break;
                case 5:
                    sortir = true;
                    System.out.println("Gracies per la seva visita.");
                    break;
                default:
                    System.out.println("Opcio no valida...");
                    break;
            }
        } while (!sortir);
    }

    private static void baixaClient (Bank bank) {
        int posicioClient;

        System.out.print("Escriu el dni per donar de baixa el compte associat: ");
        String dniClient = scanner.nextLine();

        posicioClient = bank.donarBaixaClient(dniClient);
        if (posicioClient == -1) {
            System.out.println(BankAccountException.ACCOUNT_NOT_FOUND);
        } else {
            try {
                bank.removeClient(dniClient, posicioClient);
                System.out.println("Compte donat de baixa.");
            } catch (BankAccountException e) {
                e.printStackTrace();
            }
        }
    }

    private static int altaClient(Bank bank, int position) {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        try { // Comprovacio del DNI del client
            client = new Client(nom, dni);
            compte = new CompteEstalvi(position);
            bank.setLlistaComptes(compte);
            bank.getLlistaComptes().get(position).setClients(client);
            System.out.println("Client afegit correctament, el seu numero de compte es: " + bank.getLlistaComptes().get(position).getNumCompte());
            // Si s'afegeix correctament, fem que COUNT = COUNT + 1
            return position = position + 1;
        } catch (ClientAccountException e) {
            e.printStackTrace();
            // Si no, que no li faci res
            return  position;
        }
    }

    private static void accessClient(Bank bank) {
        int posicioClient;

        System.out.print("Introdueix el numero de compte: ");
        String compteIntroduit = scanner.nextLine();

        posicioClient = bank.trobarCompte(compteIntroduit);
        if (posicioClient == -1) {
            System.out.println(BankAccountException.ACCOUNT_NOT_FOUND);
        } else {
            areaClient(bank, posicioClient);
        }
    }

    private static void areaClient (Bank bank, int posicio) {
        boolean sortir = false;

        do {
            // Es inicia el booleà en cada bucle, ja que sino es fa, la transferencia desprès de funcionar un cop, sempre farà
            //la proxima transferencia al compte 0000
            boolean trobat = false;

            System.out.println("|----- Area Client -----|");
            System.out.println("Hola de nou " + bank.getLlistaComptes().get(posicio).getClients().get(0).getNom() + "!");
            System.out.println("El seu saldo es de: " + bank.getLlistaComptes().get(posicio).getSaldo() + " €");
            System.out.println("|  1 - Retirar diners   |");
            System.out.println("|  2 - Ingresar diners  |");
            System.out.println("|  3 - Transferencia    |");
            System.out.println("|  5 - Sortir           |");
            System.out.println("|-----------------------|");
            System.out.print("Que vol fer: ");
            int opcio = scanner.nextInt();
            scanner.nextLine();

            switch (opcio) {
                case 1:
                    System.out.print("Escriu la quantitat: ");
                    Double retirar = scanner.nextDouble();
                    scanner.nextLine();
                    // Comprovar si quant es retira
                    try {
                        bank.getLlistaComptes().get(posicio).retirar(retirar);
                        System.out.println("S'ha retirat: " + retirar + " € del compte " + bank.getLlistaComptes().get(posicio).getNumCompte());
                    } catch (BankAccountException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.print("Escriu la quantitat: ");
                    Double ingres = scanner.nextDouble();
                    scanner.nextLine();
                    bank.getLlistaComptes().get(posicio).setSaldo(ingres);
                    System.out.println("S'ha ingresat " + ingres + " € al compte " + bank.getLlistaComptes().get(posicio).getNumCompte());
                    break;
                case 3:
                    areaClientTransferencia(bank, posicio);
                    break;
                case 5:
                    sortir = true;
                    break;
                default:
                    System.out.println("Opcio no valida");
                    break;
            }
        } while (!sortir);
    }

    private static void areaClientTransferencia(Bank bank, int posicioOrigen) {
        int posicioCompteDesti;

        System.out.print("Escrigui el compte de desti: ");
        String desti = scanner.nextLine();

        posicioCompteDesti = bank.trobarCompte(desti);
        if (posicioCompteDesti == -1) {
            System.out.println(BankAccountException.ACCOUNT_NOT_FOUND);
        } else {
            System.out.print("Escrigui la quantitat a transferir: ");
            Double transferir = scanner.nextDouble();
            scanner.nextLine();
            try { // Es comprova la quantitat
                bank.transferencia(transferir, posicioOrigen, posicioCompteDesti);
                System.out.println("S'ha complert la transferencia de " + transferir + " € al compte " + bank.getLlistaComptes().get(posicioCompteDesti).getNumCompte());
            } catch (BankAccountException e) {
                e.printStackTrace();
            }
        }
    }
}