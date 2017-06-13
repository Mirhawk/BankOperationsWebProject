package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BankAccountsDAO;
import pojo.LoginPojo;


@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession httpSession = request.getSession();
		
		LoginPojo userDetails = (LoginPojo) httpSession.getAttribute("user_details");
		
		int accountID = Integer.parseInt(request.getParameter("accNo"));
		
		int transactionAmount = Integer.parseInt(request.getParameter("transactionamt"));
		String transactionType = request.getParameter("transacBtn");
		
		if(transactionType.equals("Withdraw"))
			transactionAmount = 0 - transactionAmount;
		
		BankAccountsDAO bankAccountsDAOObject = (BankAccountsDAO) httpSession.getAttribute("accountsDAO");
		
		
		try {
			httpSession.setAttribute("last_action", bankAccountsDAOObject.performTransaction(transactionAmount, accountID,userDetails.getUserID()));
			response.sendRedirect("accountSummary");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new IOException("Error in closing account! Message:"+e.getMessage(),e);
		}
	}
}
