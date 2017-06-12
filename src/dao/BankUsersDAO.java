package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dbutils.DBConnection.*;

import org.apache.tomcat.util.descriptor.web.LoginConfig;

import pojo.LoginPojo;

public class BankUsersDAO {
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public BankUsersDAO() throws ClassNotFoundException, SQLException {
		connection = getDBConnection();
	}
	
	public void cleanUp() throws Exception{
		if(preparedStatement != null)
			preparedStatement.close();
			
		if(connection != null)
			connection.close();
	}
	
	public LoginPojo loginUser(String userName, String Password) throws SQLException
	{
		String selectQuery = "SELECT id,email from bank_users WHERE name=? AND password = ?";
		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, Password);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next())
		{
			return new LoginPojo(resultSet.getInt(1), userName, Password, resultSet.getString(2));
		}
		return null;
	}
	
	public LoginPojo addUser(String userName, String Password, String emailID) throws SQLException {
		String addUserQuery = "INSERT INTO bank_users (name,password,email) VALUES (?,?,?)";
		preparedStatement = connection.prepareStatement(addUserQuery);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, Password);
		preparedStatement.setString(3, emailID);
		
		int numberOfRowsAffected = preparedStatement.executeUpdate(); 
		
		if(numberOfRowsAffected == 1)
		{
			String idQuery = "SELECT @@IDENTITY";
			Statement idStatement = connection.createStatement();
			ResultSet resultSet = idStatement.executeQuery(idQuery);
			if(resultSet.next())
			{
				return new LoginPojo(resultSet.getInt(1), userName, Password, emailID); 
			}
		}
		return null;
	}
}