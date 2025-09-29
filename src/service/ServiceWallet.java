package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.WalletType;
import model.wallet;
import repository.IWalletRepository;
import repository.RepositoryWallet;
import utils.AddressGenerator;
import utils.IdGenerator;

public class ServiceWallet implements IWalletService{
	
private final IWalletRepository walletRepository;

public ServiceWallet () throws SQLException {
	
	this.walletRepository= new RepositoryWallet();
}

@Override 
public wallet createWallet(WalletType type) {
	
	wallet wallet =new wallet();
	wallet.setWalletUuid(IdGenerator.generateUUID());
	wallet.setType(type);
	wallet.setAddress(AddressGenerator.generateAddress(type));
	wallet.setBalance(0.0);
	wallet.setCreatedAt(java.time.LocalDateTime.now());
	
	return walletRepository.save(wallet);
}

@Override
public Optional<wallet> getWalletByUuid(UUID walletUuid){
	
	return walletRepository.findByUuid(walletUuid);
}


@Override
public List<wallet> getAllWallets(){
	return walletRepository.findAll();
	}

@Override
public Boolean updateBalance(UUID walletUuid, double newBalance) {
	if(newBalance < 0) {
		System.err.println("votre solde est négatif");
		return false;
	}
	return walletRepository.updateBalance(walletUuid, newBalance);
}

@Override
public Boolean deleteWallet(UUID walletUuid) {
	return walletRepository.delete(walletUuid);
	
}

}
