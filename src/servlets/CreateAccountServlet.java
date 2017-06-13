package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BankAccountsDAO;
import pojo.LoginPojo;

@WebServlet("/createAcc")
public class CreateAccountServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		try {
			HttpSession httpSession = request.getSession();
			BankAccountsDAO bankaccObject = (BankAccountsDAO) httpSession.getAttribute("accountsDAO");
			LoginPojo userDetails = (LoginPojo) httpSession.getAttribute("user_details");
			
			String accType = request.getParameter("accType");
			double accBal = Double.parseDouble(request.getParameter("accBal"));


			httpSession.setAttribute("last_action", bankaccObject.createAccount(userDetails.getUserID(), accType, accBal));
			response.sendRedirect("accountSummary");
		} catch (SQLException e) {
			throw new IOException("Error in Create acc servlet"+e.getMessage(),e);
		}
	}
}
