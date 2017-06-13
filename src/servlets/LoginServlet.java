package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.BankAccountsDAO;
import dao.BankUsersDAO;
import pojo.LoginPojo;

@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {
		
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		try(PrintWriter pw = response.getWriter()) {
			HttpSession httpSession = request.getSession();
			
			BankUsersDAO usersDAOObject = new BankUsersDAO();
			BankAccountsDAO bankAccountsDAOObject = new BankAccountsDAO();
			
			httpSession.setAttribute("userDAO", usersDAOObject);
			httpSession.setAttribute("accountsDAO", bankAccountsDAOObject);
			
			String userName = request.getParameter("userid");
			String userPasword = request.getParameter("userpass");
			
			LoginPojo loginPojoObject = usersDAOObject.loginUser(userName, userPasword);
			
			if(loginPojoObject != null)
			{
				System.out.println("reached in if of okay");
				httpSession.setAttribute("user_details", loginPojoObject);
				System.out.println("from loginserv id= "+loginPojoObject.getUserID());
				httpSession.setAttribute("last_action","Successfully logged in!");
				response.sendRedirect("accountSummary");
			}
			else
				pw.write("<h3>Invalid username- password combo!</h3>");
			
			
		} catch (SQLException e) {
			throw new IOException("Error in login servlet!", e);
		}
		catch (ClassNotFoundException e) {
			throw new IOException("Error while creating DAO object.\n"+e.getMessage(),e);
		}
	}
}