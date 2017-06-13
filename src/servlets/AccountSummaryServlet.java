package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BankAccountsDAO;
import pojo.BankAccountsPOJO;
import pojo.LoginPojo;

@WebServlet("/accountSummary")
public class AccountSummaryServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		try(PrintWriter pw = response.getWriter()) {
			HttpSession httpSession = request.getSession();
			BankAccountsDAO bankAccountsDAOObject = (BankAccountsDAO)httpSession.getAttribute("accountsDAO");
			LoginPojo userDetails = (LoginPojo)httpSession.getAttribute("user_details");
			String lastAction = (String)httpSession.getAttribute("last_action");
			System.out.println("Reached summary. userid= "+userDetails.getUserID());
			ArrayList<BankAccountsPOJO> bankAccountsPOJOArrayList = 
					bankAccountsDAOObject.getAccountsSumamry(userDetails.getUserID());
			pw.print("<div>");
			pw.print("<h3 align='center'> Welcome "+userDetails.getUserName());
			pw.print("</h3>");
			pw.print("<h3 align='right'> Last Action: "+lastAction);
			pw.print("</h3>");
			pw.print("</div><br>");
			
			//revert to get if problems
			pw.print("<div align='center'>");
			pw.print("<form action='transaction' action='post'>");
			pw.print("<table>");
			for(BankAccountsPOJO bankAcc : bankAccountsPOJOArrayList)
			{
				pw.println("<tr>");
				pw.print("<td><input type='radio' name='accNo' value='"+bankAcc.getAccountNumber()+"'></td>");
				pw.print("<td>"+bankAcc.getAccountNumber()+"</td>");
				pw.print("<td>"+bankAcc.getAccountType()+"</td>");
				pw.print("<td>"+bankAcc.getBalance()+"</td>");
				pw.println("</tr>");
			}
			pw.print("<tr>");
			pw.print("<td colspasn='2'>Transaction Amount: </td>");
			pw.print("<td colspasn='2'><input type='text' name='transactionamt'></td>");
			pw.print("</tr>");
			
			pw.print("<tr>");
				pw.print("<td>");
					pw.print("<input type='submit' value='Close A/C' formaction='closeAcc'>");
				pw.print("</td>");
				pw.print("<td>");
					pw.print("<input type='submit' value='Create A/C' formaction='CreateAccount.html'>");
				pw.print("</td>");
				pw.print("<td>");
					pw.print("<input type='submit' name='transacBtn' value='Withdraw'>");
				pw.print("</td>");
				pw.print("<td>");
					pw.print("<input type='submit' name='transacBtn' value='Deposit'>");
				pw.print("</td>");
			pw.print("</tr>");
			pw.print("</table>");
			pw.print("</form>");
			pw.print("<a href='logout'>Log Out</a>");
			pw.print("</div>");
			
			
		} catch (SQLException e) {
			throw new IOException("Error while getting account summary!",e);
		}
	}
}