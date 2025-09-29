import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import model.*;
import service.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ServiceWallet walletService = null;
        TransactionService transactionService = null;
        MempoolService mempoolService  ;
         UUID lastUserTxUuid =null;


        try {
            walletService = new ServiceWallet();
            transactionService = new TransactionService();
            mempoolService = new MempoolService(transactionService);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion à la base de données !");
            return;
        }

        boolean running = true;

        while (running) {
            System.out.println("\n=== CRYPTO WALLET SIMULATOR ===");
            System.out.println("1. Créer un wallet");
            System.out.println("2. Créer une transaction");
            System.out.println("3. Voir les transactions d'un wallet");
            System.out.println("4. Comparer les niveaux de frais");
            System.out.println("5. Consulter l'état du mempool");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choice) {

                case 1:
                    System.out.print("Type de wallet (BITCOIN/ETHEREUM) : ");
                    String typeStr = scanner.nextLine().toUpperCase();
                    WalletType type = WalletType.valueOf(typeStr);
                    wallet wallet = walletService.createWallet(type);
                    System.out.println("✅ Wallet créé !");
                    System.out.println("Adresse: " + wallet.getAddress() + " | UUID: " + wallet.getWalletUuid());
                    break;

                case 2:
                    System.out.print("UUID du wallet source : ");
                    UUID walletId = UUID.fromString(scanner.nextLine());
                    System.out.print("Adresse destination : ");
                    String dest = scanner.nextLine();
                    System.out.print("Montant : ");
                    double amount = scanner.nextDouble();
                    System.out.print("Frais : ");
                    double fees = scanner.nextDouble();
                    scanner.nextLine();
                    
                    
                    System.out.print("Priorité (ECONOMIQUE/STANDARD/RAPIDE) : ");
                    int p = scanner.nextInt();
                    scanner.nextLine();

                    Priority priority = (p == 1) ? Priority.ECONOMIQUE :
                                        (p == 2) ? Priority.STANDARD :
                                                   Priority.RAPIDE;
                    Transaction tx = transactionService.createTransaction(walletId, dest, amount, fees, priority);
                    mempoolService.addTransaction(tx);

                    lastUserTxUuid = tx.getTxUuid();
                    System.out.println("✅ Transaction créée et ajoutée au mempool !");
                    System.out.println("ID TX : " + tx.getTxUuid());
                    break;

                case 3:
                    System.out.print("UUID du wallet : ");
                    UUID wId = UUID.fromString(scanner.nextLine());
                    List<Transaction> txList = transactionService.getTransactionsByWallet(wId);
                    System.out.println("Transactions du wallet : " + txList.size());
                    for (Transaction t : txList) {
                        System.out.println(t.getTxUuid() + " | Montant: " + t.getAmount() + " | Status: " + t.getStatus());
                    }
                    
                    break;

                case 4:
                    System.out.print("Montant à comparer : ");
                    double amt = scanner.nextDouble();
                    scanner.nextLine();
                    List<String> results = mempoolService.compareFeeLevels(amt); // cette méthode retourne List<String>
                    System.out.println("\n--- COMPARAISON DES NIVEAUX DE FRAIS ---");
                    System.out.printf("%-12s | %-10s | %-10s | %-10s%n", "Priorité", "Frais", "Position", "Temps estimé");
                    System.out.println("-----------------------------------------------");
                    for (String line : results) {
                        System.out.println(line);
                    }
                    System.out.println("-----------------------------------------------\n");
                    break;

                case 5:
                    // 1. Générer des transactions aléatoires (simulation du réseau)
                    mempoolService.generateRandomTrans(15);

                    // 2. Récupérer toutes les transactions en attente
                    List<Transaction> pending = mempoolService.getPendingTrans();

                    // 3. Trier par frais décroissants
                    pending.sort((a, b) -> Double.compare(b.getFees(), a.getFees()));

                    // 4. Affichage du tableau
                    System.out.println("\n=== ÉTAT DU MEMPOOL ===");
                    System.out.println("Transactions en attente : " + pending.size());
                    System.out.println("┌──────────────────────────────────┬─────────┐");
                    System.out.println("│ Transaction                      │ Frais   │");
                    System.out.println("├──────────────────────────────────┼─────────┤");

                    for (Transaction t : pending) {
                        String idShort = t.getDestinationAddress().substring(0, 8) + "...";
                        
                        // Mettre en avant la transaction de l’utilisateur
                        if (lastUserTxUuid != null && t.getTxUuid().equals(lastUserTxUuid)) {
                            idShort = ">>> VOTRE TX: " + idShort;
                        }

                        System.out.printf("│ %-32s │ %-7.2f │%n", idShort, t.getFees());
                    }

                    System.out.println("└──────────────────────────────────┴─────────┘");
                    break;
                case 6:
                    if (lastUserTxUuid == null) {
                        System.out.println("⚠️ Vous n'avez pas encore créé de transaction !");
                    } else {
                        int position = mempoolService.getTransactionPosition(lastUserTxUuid);
                        int total = mempoolService.getPendingTrans().size();
                        int time = mempoolService.estimateConfirmationTime(lastUserTxUuid);

                        if (position == -1) {
                            System.out.println("❗ Votre transaction n'est pas dans le mempool.");
                        } else {
                            System.out.println("\n=== POSITION DANS LE MEMPOOL ===");
                            System.out.println("Votre transaction est en position " + position + " sur " + total);
                            System.out.println("Temps estimé avant confirmation : " + time + " minutes");
                        }
                    }
                    break;


                case 0:
                    running = false;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide !");
            }
        }

        scanner.close();
    }
}
