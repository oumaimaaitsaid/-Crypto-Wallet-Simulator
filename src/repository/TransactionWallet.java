package repository;

import java.sql.*;
import java.util.*;
import model.*;
import utils.DataBaseConnection;

public class TransactionWallet implements ITransactionRepository{

	private Connection connection;
	
	public TransactionWallet() throws SQLException {
		
		this.connection= DataBaseConnection.getConnection();
		}
	
	/**
	 * Sauvegarder d'une transaction
	 */
	@Override
	
	public Transaction save(Transaction transaction) {
		
		String sql ="INSERT INTO transactions "
				+ "(tx_uuid,wallet_id,source_address,destination_address,amount,fees,priority,status,created_at) "
				+ "VALUES (?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setObject(1, transaction.getTxUuid());
			stmt.setObject(2, transaction.getWalletId());
			stmt.setString(3, transaction.getSourceAddress());
			stmt.setString(4, transaction.getDestinationAddress());
			stmt.setDouble(5, transaction.getAmount());
			stmt.setDouble(6,  transaction.getFees());
			stmt.setString(7, transaction.getPriority().name());
			stmt.setString(8, transaction.getStatus().name());
			stmt.setTimestamp(9, Timestamp.valueOf(transaction.getCreatedAt()));
			
			int rows =stmt.executeUpdate();
			if(rows >0) {
				return transaction;
			}
			else {
				throw new SQLException("la transaction non enregistré");
			}
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
			
		}
				
	}
	
	
	/**
	 * Recherche par ID
	 */
	
	  public Optional<Transaction> findById(UUID id) {
		  
		  String sql ="SELECT * FROM transactions WHERE tx_uuid =?";
		  
		  try(PreparedStatement stmt =connection.prepareStatement(sql)){
			  
			  stmt.setObject(1,id);
			  ResultSet rs = stmt.executeQuery();
			  
			  if(rs.next()) {
				  Transaction transaction =mapToTransaction(rs);
				  return Optional.of(transaction);
			  }
			  
			  
			  
		  }
		  catch(SQLException e) {
			  e.printStackTrace();
		  }
		  return Optional.empty();
	  };
	
	  
	  /**
	   * trouver transactions du wallet
	   */
	  
	  public List<Transaction> findByWallet(UUID walletId){
		  List<Transaction> transactions =new ArrayList<>();
		  String sql ="SELECT * FROM transactions WHERE wallet_id =? ";
		  
		  try(PreparedStatement stmt =connection.prepareStatement(sql)){
			  stmt.setObject(1, walletId);
			  ResultSet rs= stmt.executeQuery();
			  while(rs.next()) {
				  transactions.add(mapToTransaction(rs));
				  
			  }
			  
		  }
		  catch(SQLException e) {
			  e.printStackTrace();
		  }
		  return transactions;
	  }
	  
	  /**
	   * retourner tout les transactions avec la status Pending
	   */
	  
	  public List<Transaction> findAllPending(){
		  List<Transaction> transactions =new ArrayList<>();
		  String sql ="SELECT * FROM transactions WHERE status = 'PENDING'";
		  try(PreparedStatement stmt =connection.prepareStatement(sql)){
			  ResultSet rs= stmt.executeQuery();
			  
			  while(rs.next()) {
				  transactions.add(mapToTransaction(rs));
				  
				  
			  }
			  
		  }catch(SQLException e) {
			 e.printStackTrace();
		 }
		  return transactions;
		  
	  }
	  
	  /**
	   * mis à jours la status d'un transaction
	   */
	  public boolean updateStatus(UUID id, Status status) {
		  
		  String sql= "UPDATE transactions SET status = ? WHERE tx_uuid = ?";
		  try(PreparedStatement stmt =connection.prepareStatement(sql)){
			  
			  stmt.setString(1,status.name());
			  stmt.setObject(2, id);
			  
			  int rows =stmt.executeUpdate();
			  return rows > 0;
			  
			  
		  }
		  catch(SQLException e) {
			  e.printStackTrace();
			  return false;
		  }
		  
	  }
	  
	  /**
	   * delete une transaction
	   */
	  
	  public boolean delete(UUID id) {
		  String sql="DELETE FROM transactions WHERE tx_uuid= ?";
		  try(PreparedStatement stmt =connection.prepareStatement(sql)){
			  stmt.setObject(1, id);
			  int rows =stmt.executeUpdate();
			  return rows > 0;
			  
			  
		  }
		  catch(SQLException e) {
			  e.printStackTrace();
			  return false;
		  }
	  }
	
	/**
	 * Mapper ResultSet ->Transaction
	 */
	
	private Transaction mapToTransaction(ResultSet rs) throws SQLException {
		Transaction tr =new Transaction();
		tr.setTxUuid((UUID) rs.getObject("tx_uuid"));
		tr.setWalletId((UUID) rs.getObject("wallet_id"));
		tr.setDestinationAddress(rs.getString("destination_address"));
		tr.setAmount(rs.getDouble("amount"));
		tr.setFees(rs.getDouble("fees"));
		tr.setPriority(Priority.valueOf(rs.getString("priority")));
		tr.setStatus(Status.valueOf(rs.getString("status")));
		tr.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		return tr;
	}
}
