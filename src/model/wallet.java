package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class wallet {
	
	private int id;
	private UUID walletUuid ;
	private WalletType type;
	private String address;
	private  double balance;
	private LocalDateTime createdAt ;
	

	public int getId() {return id;}
	public void setId(int id) {this.id=id;}
	
	public UUID getWalletUuid() {return walletUuid;}
	public void setWalletUuid(UUID uuid) {this.walletUuid=uuid;}
	
	public WalletType getType() {return type;}
	public void setType(WalletType type) {this.type=type;}
	
    public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }

	public double getBalance() { return balance; }
	public void setBalance(double balance) { this.balance = balance; }

	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
