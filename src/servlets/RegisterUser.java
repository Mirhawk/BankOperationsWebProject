package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BankUsersDAO;
import pojo.LoginPojo;

@WebServlet("/resgisterSerlvet")
public class RegisterUser extends HttpServlet {
	
BankUsersDAO usersDAOObject;
	
	@Override
	public void init() throws ServletException {
		
		try {
			usersDAOObject = new BankUsersDAO();
		}
		catch (ClassNotFoundException e) {
			throw new ServletException("Error while creaating DAO object.",e);
		}
		catch (SQLException e) {
			throw new ServletException("Error while creaating DAO object.",e);
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
			String userName = request.getParameter("userName");
			String userPasword = request.getParameter("userPass");
			String emailID = request.getParameter("emailID");
			
			LoginPojo pojoObject = usersDAOObject.addUser(userName,userPasword,emailID);
			
			if(pojoObject != null)
			{
				response.sendRedirect("Login.html");
				pw.write("User: "+pojoObject.getUserID());
			}
			else
				pw.write("Error while registering user!");
			
		} catch (SQLException e) {
			throw new IOException("Error while registering user!", e);
		}
	}

}
