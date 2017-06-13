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
import pojo.LoginPojo;

@WebServlet("/closeAcc")
public class CloseAccoutServlet extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession httpSession = request.getSession();
		int closeAccountID =Integer.parseInt(request.getParameter("accNo"));
		LoginPojo userDetails = (LoginPojo) httpSession.getAttribute("user_details");
		BankAccountsDAO bankAccountsDAOObject = (BankAccountsDAO) httpSession.getAttribute("accountsDAO");
		try {
			httpSession.setAttribute("last_action", bankAccountsDAOObject.deleteAccount(closeAccountID));
			response.sendRedirect("accountSummary");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new IOException("Error in closing account! Message:"+e.getMessage(),e);
		}
	}
}
