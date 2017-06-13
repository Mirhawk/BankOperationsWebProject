package pojo;

public class BankAccountsPOJO {
	int accountNumber;
	int customerID;
	String accountType;
	long balance;
	
	
	public BankAccountsPOJO(int accountNumber, int customerID, String accountType, long balance) {
		super();
		this.accountNumber = accountNumber;
		this.customerID = customerID;
		this.accountType = accountType;
		this.balance = balance;
	}


	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}


	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public long getBalance() {
		return balance;
	}


	public void setBalance(long balance) {
		this.balance = balance;
	}


	@Override
	public String toString() {
		return "BankAccountsDAO [accountNumber=" + accountNumber + ", customerID=" + customerID + ", accountType="
				+ accountType + ", balance=" + balance + "]";
	}
}
