package dao;

import static dbutils.DBConnection.getDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pojo.BankAccountsPOJO;

public class BankAccountsDAO {
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public BankAccountsDAO() throws ClassNotFoundException, SQLException {
		connection = getDBConnection();
	}
	
	public void cleanUp() throws Exception{
		if(preparedStatement != null)
			preparedStatement.close();
			
		if(connection != null)
			connection.close();
	}
	
	public ArrayList<BankAccountsPOJO> getAccountsSumamry(int customerID) throws SQLException
	{
		System.out.println("reached in the acc summary");
		ArrayList<BankAccountsPOJO> bankAccountsArrayLits = new ArrayList<>();
		String accountsSQLQuery = "SELECT ac_no, type,bal FROM bank_accounts WHERE id=?";
		
		preparedStatement = connection.prepareStatement(accountsSQLQuery);
		
		preparedStatement.setInt(1, customerID);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next())
		{
			BankAccountsPOJO bnkPOJO = new BankAccountsPOJO(resultSet.getInt(1), customerID, resultSet.getString(2), resultSet.getLong(3));
			System.out.println("bankacc = "+bnkPOJO.toString());
			bankAccountsArrayLits.add(bnkPOJO);
		}
		
		return bankAccountsArrayLits;
	}
	
	
	public String deleteAccount(int accountID) throws SQLException {
		String closeAccountQuery = "DELETE FROM bank_accounts WHERE ac_no = ?";
		System.out.println("deleteAccount got called!");
		PreparedStatement preparedStatement = connection.prepareStatement(closeAccountQuery);
		preparedStatement.setInt(1, accountID);
		System.out.println("deleting account "+accountID);
		int rowsAffectedCount = preparedStatement.executeUpdate();
		if(rowsAffectedCount == 1)
		{
			System.out.println("one row got affected!");
			return "Account removed : "+accountID;
		}
	
		return null;
	}
	
	
	public String performTransaction(double transactionAmount, int accountID, int customerID) throws SQLException
	{
		String trasnsactionSQLQuery = "UPDATE bank_accounts SET bal = bal+? WHERE ac_no = ? AND id = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(trasnsactionSQLQuery);
		preparedStatement.setDouble(1, transactionAmount);
		preparedStatement.setInt(2, accountID);
		preparedStatement.setInt(3, customerID);
		int rowsAffectedCount = preparedStatement.executeUpdate();
		if(rowsAffectedCount == 1)
		{
			return "Account balance updated";
		}
		return null;	
	}
	
	public String createAccount(int customerID, String accountType, double accountBalance) throws SQLException {
		String createAccountSQLQuery = "INSERT INTO bank_accounts (id,type,bal) VALUES (?,?,?)";
		
		PreparedStatement preparedStatement = connection.prepareStatement(createAccountSQLQuery);
		preparedStatement.setInt(1, customerID);
		preparedStatement.setString(2, accountType);
		preparedStatement.setDouble(3, accountBalance);
		
		int rowsAffectedCount = preparedStatement.executeUpdate();
		
		if(rowsAffectedCount == 1)
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT @@IDENTITY");
			if(resultSet.next())
				return "Account created with ID "+resultSet.getInt(1);
		}
		return "Error in creating account!";
	}
}
