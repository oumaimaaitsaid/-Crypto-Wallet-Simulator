package service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.WalletType;
import model.wallet;

public interface IWalletService {

	/**
	 * Creation d'un wallet 
	 */
	
	wallet createWallet(WalletType type);
	
	
	/**
	 * récupèrer un wallet par son UUID
	 */
	
	Optional<wallet> getWalletByUuid(UUID walletUuid);
	
	/**
	 * récupèrer les listes de wallet
	 */
	
	List<wallet> getAllWallets();
	
	/**
	 * mis à jour le solde
	 */
	
	Boolean updateBalance(UUID walletUuid, double nowBalance);
	
	/**
	 * delete un wallet
	 */
	
	Boolean deleteWallet(UUID walletUuid); 
}
