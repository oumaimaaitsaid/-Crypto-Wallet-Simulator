package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.WalletType;
import model.wallet;
import utils.DataBaseConnection;

public class RepositoryWallet implements IWalletRepository{
	
	private Connection connection;
	
	
	  // Constructeur : récupère la connexion JDBC
    public RepositoryWallet() throws SQLException {
        this.connection = DataBaseConnection.getConnection();
    }
	/**
	 * souvgardé un wallet en base de données
	 */
	@Override
	public wallet save(wallet wallet) {
		String sql ="INSERT INTO wallets (wallet_uuid, type, address, balance, created_at) VALUES (?,?,?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setObject(1, wallet.getWalletUuid());
			stmt.setString(2, wallet.getType().name());
			stmt.setString(3, wallet.getAddress());
			stmt.setDouble(4, wallet.getBalance());
			stmt.setTimestamp(5, Timestamp.valueOf(wallet.getCreatedAt()));
			
			int rows = stmt.executeUpdate();
			if(rows > 0) {
				return wallet;
			}
			else {
				throw new SQLException("échec de enregistré du wallet");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public Optional<wallet> findByUuid(UUID uuid){
		String sql ="SELECT * FROM wallets WHERE wallet_uuid = ?";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setObject(1 ,uuid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next() ) {
				wallet wallet = mapResultSetToWallet(rs);
				return Optional.of(wallet);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	 /**
     * Recherche un wallet par son adresse
     */
    @Override
    public Optional<wallet> findByAddress(String address) {
        String sql = "SELECT * FROM wallets WHERE address = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                wallet wallet = mapResultSetToWallet(rs);
                return Optional.of(wallet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    /**
     * Liste tous les wallets
     */
    @Override
    public List<wallet> findAll() {
        List<wallet> wallets = new ArrayList<>();
        String sql = "SELECT * FROM wallets";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                wallets.add(mapResultSetToWallet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallets;
    }
    /**
     * Met à jour le solde d’un wallet
     */
    @Override
    public boolean updateBalance(UUID uuid, double newBalance) {
        String sql = "UPDATE wallets SET balance = ? WHERE wallet_uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setObject(2, uuid);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Supprime un wallet par UUID
     */
    @Override
    public boolean delete(UUID uuid) {
        String sql = "DELETE FROM wallets WHERE wallet_uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, uuid);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
    /**
     * Méthode utilitaire pour mapper ResultSet en Wallet
     */
    private wallet mapResultSetToWallet(ResultSet rs) throws SQLException {
        wallet wallet = new wallet();
        wallet.setId(rs.getInt("id"));
        wallet.setWalletUuid((UUID) rs.getObject("wallet_uuid"));
        wallet.setType(WalletType.valueOf(rs.getString("type")));
        wallet.setAddress(rs.getString("address"));
        wallet.setBalance(rs.getDouble("balance"));
        wallet.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return wallet;
    }

}
