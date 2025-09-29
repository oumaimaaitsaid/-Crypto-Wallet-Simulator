package service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.Status;
import model.Transaction;
import model.Priority;

public interface ITransactionService {
	
/**
 * créer une nouvelle transaction
 * 
 * @param walletId
 * @param destinationAddress
 * @param amount (montant à transférer)
 * @param fees frais de transaction
 * @param priority 
 * @return la transaction créée
 */
	
	Transaction createTransaction(UUID waletId, String destinationAddress, double amount, double fees, Priority priority);
	
	
	
	/**
	 * récupérè un transaction par son id
	 */
	Optional<Transaction> getTransactionById(UUID txUuid);
	
	
	/**
	 * Récupèrer toutes les  transaction d'un wallet donné.
	 */
	
	List<Transaction> getTransactionsByWallet(UUID walletId);
	
	/**
	 * Récupèrer toutes les transactions en attente
	 */
	
	List<Transaction> getAllPendingTransactions();
	
	/**
	 * Mettre à jours le status d'une transaction
	 */
	
	boolean updateTransactionStatus(UUID Uuid, Status newStatus);
	
	
	/**
	 * Supprimer une transaction.
	 */
	
	boolean deleteTransaction(UUID txUuid);
}
