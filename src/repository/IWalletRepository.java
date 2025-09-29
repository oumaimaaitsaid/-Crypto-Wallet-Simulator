package repository;

import model.wallet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWalletRepository {

    /**
     * Sauvegarde un wallet en base de données.
     * @param wallet l'objet Wallet à sauvegarder
     * @return le wallet sauvegardé avec son ID mis à jour si auto-généré
     */
    wallet save(wallet wallet);

    /**
     * Recherche un wallet par son UUID.
     * @param uuid identifiant unique du wallet
     * @return un Optional contenant le wallet si trouvé, sinon Optional.empty()
     */
    Optional<wallet> findByUuid(UUID uuid);

    /**
     * Recherche un wallet par son adresse crypto.
     * @param address adresse publique du wallet
     * @return un Optional contenant le wallet si trouvé, sinon Optional.empty()
     */
    Optional<wallet> findByAddress(String address);

    /**
     * Récupère tous les wallets existants.
     * @return une liste de tous les wallets
     */
    List<wallet> findAll();

    /**
     * Met à jour le solde d’un wallet.
     * @param uuid identifiant unique du wallet
     * @param newBalance nouveau solde à enregistrer
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean updateBalance(UUID uuid, double newBalance);

    /**
     * Supprime un wallet par son UUID.
     * @param uuid identifiant unique du wallet
     * @return true si la suppression a réussi, false sinon
     */
    boolean delete(UUID uuid);
}
