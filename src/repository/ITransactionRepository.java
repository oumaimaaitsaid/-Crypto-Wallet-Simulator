package repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.Status;
import model.Transaction;

public interface ITransactionRepository {

    /**
     * Sauvegarde une transaction en base de données
     * @param transaction à sauvegarder
     * @return la transaction sauvegardée (avec son UUID mis à jour si nécessaire)
     */
    Transaction save(Transaction transaction);

    /**
     * Recherche une transaction par son UUID
     * @param id identifiant unique
     * @return Optional<Transaction>
     */
    Optional<Transaction> findById(UUID id);

    /**
     * Recherche toutes les transactions d’un wallet
     * @param walletId identifiant du wallet
     * @return liste des transactions liées à ce wallet
     */
    List<Transaction> findByWallet(UUID walletId);

    /**
     * Récupère toutes les transactions en attente (PENDING)
     * @return liste des transactions PENDING
     */
    List<Transaction> findAllPending();

    /**
     * Met à jour le statut d’une transaction
     * @param id identifiant unique de la transaction
     * @param status nouveau statut (PENDING, CONFIRMED, REJECTED)
     * @return true si la mise à jour a réussi
     */
    boolean updateStatus(UUID id, Status status);

    /**
     * Supprime une transaction par son UUID
     * @param id identifiant unique de la transaction
     * @return true si la suppression a réussi
     */
    boolean delete(UUID id);
}
