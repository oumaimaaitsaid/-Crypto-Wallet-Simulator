package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.Status;
import model.Transaction;
import model.wallet;
import model.Priority;
import repository.ITransactionRepository;
import repository.RepositoryWallet;
import repository.TransactionWallet;
import utils.IdGenerator;

public class TransactionService implements ITransactionService {
	
	private final ITransactionRepository TransactionRepository;
    private final RepositoryWallet walletRepository;   // <-- Ajout pour accéder aux wallets

	
	public TransactionService () throws SQLException {
		this.TransactionRepository= new TransactionWallet();
		this.walletRepository = new RepositoryWallet();
	}

	
	
	@Override
	public Transaction createTransaction(UUID waletId, String destinationAddress, double amount, double fees, Priority priority) {
		if(amount <=0) {
			throw new IllegalArgumentException("le montant doit ètre positif");
		}
		if(destinationAddress == null ||destinationAddress.isEmpty()) {
			throw new IllegalArgumentException("Adress de detination invalide.");
		}
		 wallet sourceWallet = walletRepository.findByUuid(waletId)
	                .orElseThrow(() -> new IllegalArgumentException("Wallet source introuvable !"));
		
		Transaction tr =new Transaction();
		tr.setTxUuid(IdGenerator.generateUUID());
		tr.setWalletId(waletId);
        tr.setSourceAddress(sourceWallet.getAddress());      
		tr.setDestinationAddress(destinationAddress);
		tr.setAmount(amount);
		tr.setFees(fees);
		tr.setPriority( priority );
		tr.setStatus(Status.PENDING);
		tr.setCreatedAt(java.time.LocalDateTime.now());
		
		return TransactionRepository.save(tr);
	}
	@Override
	public Optional<Transaction> getTransactionById(UUID txUuid){
		return TransactionRepository.findById(txUuid);}
	
	
	@Override
	public List<Transaction> getTransactionsByWallet(UUID walletId){
		return TransactionRepository.findByWallet(walletId);}
	@Override 
	public List<Transaction> getAllPendingTransactions(){
		return TransactionRepository.findAllPending()	;}

	@Override
	public boolean updateTransactionStatus(UUID Uuid, Status newStatus) {
		return TransactionRepository.updateStatus(Uuid, newStatus);}
	
	
	
   @Override
   public boolean deleteTransaction(UUID txUuid) {
	return TransactionRepository.delete(txUuid);}

	
}
