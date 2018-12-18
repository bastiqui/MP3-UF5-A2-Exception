package com.bastiqui;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Client client = null;

    public static void main(String[] args) {
        Bank bank = new Bank();
        int count = 0;
        boolean sortir = false;

        do {
            System.out.println("|------ Menu -------|");
            System.out.println("| 1 - Donar d'alta  |");
            System.out.println("| 2 - Access client |");
            System.out.println("| 5 - Sortir        |");
            System.out.println("|-------------------|");
            System.out.print("Escull: ");
            int opcio = scanner.nextInt();
            scanner.nextLine();

            switch (opcio) {
                case 1:
                    altaClient(bank, count);
                    count++;
                    break;
                case 2:
                    // Comprovacio que n'hi hagin clients en el banc
                    if (bank.getClients().isEmpty()) {
                        System.out.println("No hi ha cap client en aquest banc.");
                    } else {
                        accessClient(bank);
                    }
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

    private static void altaClient(Bank bank, int count) {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        //System.out.print("DNI: ");
        //String dni = scanner.nextLine();

        try { // Comprovacio del DNI del client
            client = new Client(nom, "12345678A");
            bank.setClients(client, count);
            System.out.println("Client afegit correctament, el seu numero de compte es: " + bank.getNumCompte(count));
        } catch (ClientAccountException e) {
            e.printStackTrace();
        }
    }

    private static void accessClient(Bank bank) {
        boolean trobat = false;
        int posicioClient;

        System.out.print("Introdueix el numero de compte: ");
        String compteIntroduit = scanner.nextLine();
        for (int i = 0; i < bank.getClients().size(); i++) {
            // Es mira si existeix el compte
            if (compteIntroduit.equals(bank.getNumCompte(i))) {
                trobat = true;
            }

            if (trobat) {
                posicioClient = i;
                areaClient(bank, posicioClient);
                break;
            }
        }

        if (!trobat) {
            System.out.println(BankAccountException.ACCOUNT_NOT_FOUND);
        }
    }

    private static void areaClient (Bank bank, int posicioOrigen) {
        boolean sortir = false;
        int posicioCompteDesti;

        do {
            // Es inicia el booleà en cada bucle, ja que sino es fa, la transferencia desprès de funcionar un cop, sempre farà
            //la proxima transferencia al compte 0000
            boolean trobat = false;

            System.out.println("|----- Area Client -----|");
            System.out.println("Hola de nou " + bank.getClients().get(posicioOrigen).getNom() + "!");
            System.out.println("El seu saldo es de: " + bank.getSaldo(posicioOrigen) + " €");
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
                    try { // Comprovar si quant es retira
                        bank.retirar(retirar, posicioOrigen);
                        System.out.println("S'ha retirat: " + retirar + " € del compte " + bank.getNumCompte(posicioOrigen));
                    } catch (BankAccountException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.print("Escriu la quantitat: ");
                    Double ingres = scanner.nextDouble();
                    scanner.nextLine();
                    bank.setSaldo(ingres, posicioOrigen);
                    System.out.println("S'ha ingresat " + ingres + " € al compte " + bank.getNumCompte(posicioOrigen));
                    break;
                case 3:
                    System.out.print("Escrigui el compte de desti: ");
                    String desti = scanner.nextLine();

                    for (int i = 0; i < bank.getClients().size(); i++) {
                        // Es mira si existeix el compte
                        if (desti.equals(bank.getNumCompte(i))) {
                            trobat = true;
                        }

                        if (trobat) {
                            posicioCompteDesti = i;
                            System.out.print("Escrigui la quantitat a transferir: ");
                            Double transferir = scanner.nextDouble();
                            scanner.nextLine();
                            try { // Es comprova la quantitat
                                bank.transferencia(transferir, posicioOrigen, posicioCompteDesti);
                                System.out.println("S'ha complert la transferencia de " + transferir + " € al compte " + bank.getNumCompte(posicioCompteDesti));
                            } catch (BankAccountException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }

                    if (!trobat) {
                        System.out.println(BankAccountException.ACCOUNT_NOT_FOUND);
                        break;
                    }

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
}