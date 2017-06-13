package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BankAccountsDAO;
import dao.BankUsersDAO;
import pojo.LoginPojo;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		try(PrintWriter pw = response.getWriter()) {
			HttpSession httpSession = request.getSession();
			BankAccountsDAO bankAccountsDAO = (BankAccountsDAO) httpSession.getAttribute("accountsDAO");
			BankUsersDAO bankUsersDAO = (BankUsersDAO) httpSession.getAttribute("userDAO");
			LoginPojo userDetails = (LoginPojo) httpSession.getAttribute("user_details");
			pw.print("<h3 align='center'>");
			pw.print("Loggin user "+userDetails.getUserName()+" out.");
			pw.print("</h3>");
			
			bankAccountsDAO.cleanUp();
			bankUsersDAO.cleanUp();
			
			httpSession.invalidate();
			
			response.setHeader("refresh", "5;url="+request.getContextPath());
			
		} catch (Exception e) {
			throw new IOException("Error in Logout! "+e.getMessage(),e);
		}
	}
}
