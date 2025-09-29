package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

	private int id;
	private UUID txUuid;
	private UUID walletId;
	private String sourceAddress ;
	private String destinationAddress;
	private double amount;
	private double fees;
	private Priority priority;
	private Status status;
	private LocalDateTime createdAt;
	
	
	// Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public UUID getTxUuid() { return txUuid; }
    public void setTxUuid(UUID txUuid) { this.txUuid = txUuid; }

    public UUID getWalletId() { return walletId; }
    public void setWalletId(UUID walletId) { this.walletId = walletId; }

    public String getSourceAddress() { return sourceAddress; }
    public void setSourceAddress(String sourceAddress) { this.sourceAddress = sourceAddress; }

    public String getDestinationAddress() { return destinationAddress; }
    public void setDestinationAddress(String destinationAddress) { this.destinationAddress = destinationAddress; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getFees() { return fees; }
    public void setFees(double fees) { this.fees = fees; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
