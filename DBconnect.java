
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class DBconnect {


	public static boolean checkSQLConnection(String password) {
		Connection conn=null;
		try {
		conn = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/DBlibrary","root",password);
		return true;
		}
		catch(Exception e) {
	    return false;
		}
	}
	
	public static Connection getConnector(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBlibrary","root","fghj");
			return conn;
		} 
		catch(SQLException ex) {
			return null;
		}
	}

	public static ResultSet BookQuery(Connection conn, String ISBN, String title, String Author) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("use DBlibrary;");
			rs = stmt.executeQuery("SELECT BOOK.Isbn, Title, AUTHORS.Name " +
					"FROM BOOK, BOOK_AUTHORS, AUTHORS " + 
					"WHERE BOOK.Isbn LIKE '%"+ISBN+"%' AND Title LIKE '%"+title+"%' AND AUTHORS.Name LIKE '%"+Author+"%' AND " +
					"BOOK.Isbn=BOOK_AUTHORS.Isbn AND BOOK_AUTHORS.Author_id=AUTHORS.Author_id;");
			return rs;
		} 
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			return null;
		}
	}

	public static void CheckingOut(String ISBN, String cardNum) {

		ResultSet result = null;

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBlibrary","root","fghj");
			Statement stmt = conn.createStatement();
			stmt.execute("use DBlibrary;");
			
			result = stmt.executeQuery("SELECT COUNT(Isbn) FROM BOOK_LOANS WHERE Card_id='"+cardNum+"' AND Date_in is null"); 
			result.next();
			int count = Integer.parseInt(result.getString("COUNT(Isbn)"));
			if(count==3){
				JOptionPane.showMessageDialog(null, "One person has maximum 3 books");
				result.close();
				conn.close();
				return;
			}
			
			result = stmt.executeQuery("SELECT COUNT(Isbn) FROM BOOK_LOANS WHERE Isbn='"+ISBN+"' AND Date_in is null"); 
			
			result.next();
			count = Integer.parseInt(result.getString("count(Isbn)"));
			
			if(count == 1){
				JOptionPane.showMessageDialog(null, "This book is not here");
				result.close(); 
				conn.close();
				return;
			}
			
			SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd"); 
	        Date day = new Date();  
	        
	        Calendar now = Calendar.getInstance();
	        String Date_out = newDate.format(day);
	        now.set(Calendar.DATE,now.get(Calendar.DATE)+14);
	        String Due_date = newDate.format(now.getTime());

			stmt.executeUpdate(" INSERT INTO BOOK_LOANS(Isbn, Card_id, Date_out, Due_date, Date_in) " +
					"VALUES ('"+ISBN+"', '"+cardNum+"', '"+Date_out+"', '"+Due_date+"', NULL);");
			JOptionPane.showMessageDialog(null, "Check out successfully");
			
			result.close();
			conn.close();
		} 
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		
	}
	
	public static void CheckingIn(String ISBN) {
	       try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "fghj");
				Statement stmt = conn.createStatement();
				stmt.execute("use DBlibrary;");
				
				SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd"); 
		        Date day = new Date();
		        String Date_in = newDate.format(day);
				
				stmt.executeUpdate("UPDATE BOOK_LOANS SET Date_in = '"+Date_in+"' WHERE ISBN = "+ISBN+";");
				JOptionPane.showMessageDialog(null, "Check In Successfully");
				conn.close();
			} 
			catch(SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}

	public static void CreateNewAccount(String[] nameList) {
		
		String[] List = {"SSN", "Fname", "Lname", "Address", "City","State","Phone"}; 
		for (int i = 1; i < 7; i++)
			if(nameList[i].length() == 0){
				JOptionPane.showMessageDialog(null, "The "+List[i]+" is not null");
				return;
			}
		ResultSet rs = null;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "fghj");
			Statement stmt = conn.createStatement();
			stmt.execute("use DBlibrary;");
			
		rs = stmt.executeQuery("SELECT MAX(Card_id) FROM BORROWER;"); 
		rs.next();
		String temp = rs.getString("MAX(Card_id)");
		String CardNumberStr = temp.substring(temp.length()-6, temp.length()); 
		int CardNum = Integer.parseInt(CardNumberStr) + 1; 
		String BuildCardNo = "ID";
		int tmpInt = 100000;
		for (int i = 0; i <= 5; i++) { 
			BuildCardNo = BuildCardNo + Integer.toString(CardNum / tmpInt);
			CardNum = CardNum % tmpInt;
			tmpInt = tmpInt/10;
		}
		
		
		rs = stmt.executeQuery("SELECT COUNT(Ssn) FROM BORROWER WHERE Ssn = '"+nameList[0]+"';"); 
		rs.next();
		String ssnCnt = rs.getString("COUNT(Ssn)");
		if(Integer.parseInt(ssnCnt) > 0) {
			JOptionPane.showMessageDialog(null, "This SSN already in the database");
			return;
		}
		stmt.executeUpdate("INSERT INTO BORROWER VALUES ('"+BuildCardNo+"', '"+nameList[0]+"', '"+nameList[1]+"','"
				+nameList[2]+"','"+nameList[3]+"', '"+nameList[4]+"', '"+nameList[5]+"','"+nameList[6]+"' );");

		JOptionPane.showMessageDialog(null, "New Card ID is " + BuildCardNo);
		rs.close();
		conn.close();
	}
	catch(SQLException ex) {
		JOptionPane.showMessageDialog(null, ex.getMessage());
	}
		
	}



	public static ResultSet SearchBeforeCheckingIn(Connection getConnection, String bookNumber, String cardNumber) {
		ResultSet result = null;
		try {
			Statement statement = getConnection.createStatement();
			statement.execute("use DBlibrary;");
			result = statement.executeQuery("SELECT BOOK_LOANS.* FROM BOOK_LOANS, "
					+ "BORROWER WHERE Isbn LIKE '%"+bookNumber+"%' AND BOOK_LOANS.Card_id LIKE '%"+cardNumber+
					"%'  AND BOOK_LOANS.Card_id=BORROWER.Card_id;");
			return result;
		} 
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			return null;
		}
	
	}

	public static int UpdateFines(JTable tableFines, String cardNum) {
		ResultSet rs = null;
		try {
			Connection conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "fghj");
			Statement stmt = conn.createStatement();
			stmt.execute("use DBlibrary;");
			String updateList = "(SELECT FINES.Loan_id, DATEDIFF(date(now()), Due_Date)*0.25 AS total_fine_amt " +
					"FROM (FINES JOIN BOOK_LOANS ON FINES.Loan_id = BOOK_LOANS.Loan_id) " +
					"WHERE paid=0 AND Date_in IS NULL AND fine_amt <> DATEDIFF(date(now()), Due_Date)*0.25) " +
					"UNION " +
					"(SELECT FINES.Loan_id, DATEDIFF(Date_in, Due_Date)*0.25 AS total_fine_amt " +
					"FROM (FINES JOIN BOOK_LOANS ON BOOK_LOANS.Loan_id = FINES.Loan_id) " +
					"WHERE paid=0 AND Date_in IS NOT NULL AND fine_amt <> DATEDIFF(Date_in, Due_Date)*0.25);";
			
			rs = stmt.executeQuery(updateList);
			List<ArrayList<String>> StringList = new ArrayList<ArrayList<String>>();
			StringList.add(new ArrayList<String>()); 
			StringList.add(new ArrayList<String>()); 
			while (rs.next()) {
				String LoanID = rs.getString("Loan_id");
				String newFineAmt = rs.getString("total_fine_amt");
				StringList.get(0).add(LoanID);
				StringList.get(1).add(newFineAmt);
			}
			for(int i=0; i<StringList.get(0).size(); i++) {
				String Update = "UPDATE FINES SET fine_amt = "+StringList.get(1).get(i)+" WHERE Loan_id = '"+StringList.get(0).get(i)+"'; ";
				stmt.executeUpdate(Update);
			}

			stmt.executeUpdate("DELETE FROM FINES WHERE fine_amt < 0;");
			stmt.executeUpdate("DELETE FROM FINES WHERE fine_amt = 0;");
			
			stmt.executeUpdate("Insert into FINES(Loan_id, fine_amt, paid) " +
					"(select BOOK_LOANS.Loan_id, DATEDIFF(date(now()), Due_Date)*0.25, 0 from BOOK_LOANS " +
					"WHERE Date_in IS NULL AND Due_Date < date(now()) AND " +
					"NOT EXISTS (SELECT * FROM FINES WHERE BOOK_LOANS.Loan_id = FINES.Loan_id)) " +
					"UNION " +
					"(select BOOK_LOANS.Loan_id, DATEDIFF(Date_in, Due_Date)*0.25, 0 from BOOK_LOANS " +
					"WHERE Date_in IS NOT NULL AND Due_Date < Date_in AND " +
					"NOT EXISTS (SELECT * FROM FINES WHERE BOOK_LOANS.Loan_id = FINES.Loan_id)); ");
			
			rs = stmt.executeQuery("select Card_id, SUM(fine_amt) AS ALL_Fine_Amount, paid " +
					"from FINES JOIN BOOK_LOANS ON BOOK_LOANS.Loan_id = FINES.Loan_id " +
					"WHERE paid = 0 AND Card_id = '"+cardNum+"' GROUP BY Card_id; ");
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); 
			}
			else rowcount = 0;
			
			tableFines.setModel(DbUtils.resultSetToTableModel(rs));
			
			rs.close();
			conn.close();
			return rowcount;
		} 
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			return 0;
		}
	}

	public static void UpdateFineRecord(String cardNum, double enterPayment) {

		ResultSet rs = null;
		try {
			Connection conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "fghj");
			Statement stmt = conn.createStatement();
			stmt.execute("use DBlibrary;");
			rs = stmt.executeQuery("SELECT FINES.Loan_id " +
					"FROM BOOK_LOANS JOIN FINES ON BOOK_LOANS.Loan_id = FINES.Loan_id " +
					"WHERE Card_id = '"+cardNum+"' AND paid = 1;");
			
			List<String> StrTemp = new ArrayList<String>();
			while (rs.next()) {
				String Loan_id = rs.getString("Loan_id");
				StrTemp.add(Loan_id);
			}
		
			stmt.executeUpdate("UPDATE FINES SET Fine_amt = "+enterPayment+" WHERE Loan_id = "+StrTemp.get(0)+"; ");
			for(int i=1; i<StrTemp.size(); i++)
				stmt.executeUpdate("UPDATE FINES SET Fine_amt = 0 WHERE Loan_id = "+StrTemp.get(i)+"; ");
	
			JOptionPane.showMessageDialog(null, "Update Successfully!");
			rs.close();
			conn.close();
		} 
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}		
		
	}

	public static void AlterPaid(String cardNum) {

		ResultSet rs = null;
		try {
			Connection conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "fghj");
			Statement stmt = conn.createStatement();
			stmt.execute("use DBlibrary;");

			rs = stmt.executeQuery("SELECT COUNT(FINES.Loan_id) " +
					"FROM BOOK_LOANS JOIN FINES ON BOOK_LOANS.Loan_id = FINES.Loan_id " +
					"WHERE Card_id = '"+cardNum+"' AND Paid = 0 AND Date_in IS NULL;"); 
			rs.next();
			
			String count = rs.getString("COUNT(FINES.Loan_id)");
			
			if(Integer.parseInt(count) > 0){
				JOptionPane.showMessageDialog(null, "This book is not checked in");
				return;
			}
			
			String CardNoPaidQuery = "SELECT FINES.Loan_id " +
					"FROM BOOK_LOANS JOIN FINES ON BOOK_LOANS.Loan_id = FINES.Loan_id " +
					"WHERE Card_id = '"+cardNum+"' AND Paid = 0;";
			rs = stmt.executeQuery(CardNoPaidQuery);
			List<String> StrTemp = new ArrayList<String>();
			while (rs.next()) {
				String Loan_id = rs.getString("Loan_id");
				StrTemp.add(Loan_id);
			}
			for(int i=0; i<StrTemp.size(); i++) {
				stmt.executeUpdate("UPDATE FINES SET Paid = 1 WHERE Loan_id = "+StrTemp.get(i)+"; ");
			}
			
			JOptionPane.showMessageDialog(null, "Pay successfully");
			rs.close();
			conn.close();
		} 
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}		
		
	}


}
