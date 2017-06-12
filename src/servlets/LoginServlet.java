package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.*;

import dao.BankUsersDAO;
import pojo.LoginPojo;

@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {
	
	BankUsersDAO usersDAOObject;
	
	@Override
	public void init() throws ServletException {
		
		try {
			usersDAOObject = new BankUsersDAO();
		}
		catch (ClassNotFoundException e) {
			throw new ServletException("Error while creating DAO object.",e);
		}
		catch (SQLException e) {
			throw new ServletException("Error while creating DAO object.",e);
		}
	}
	
	@Override
	public void destroy() {
		try {
			usersDAOObject.cleanUp();
		} catch (Exception e) {
			throw new RuntimeException("Error while cleaning up!",e);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		try(PrintWriter pw = response.getWriter()) {
			String userName = request.getParameter("userid");
			String userPasword = request.getParameter("userpass");
			
			LoginPojo pojoObject = usersDAOObject.loginUser(userName, userPasword);
			
			if(pojoObject != null)
			{
				
				response.sendRedirect("Home.html");
				pw.write("Login user is : "+pojoObject.getUserName());
			}
			else
				pw.write("Invalid username- password combo!");
			
		} catch (SQLException e) {
			throw new IOException("Error while loggin user in!", e);
		}
	}
}