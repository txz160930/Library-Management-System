import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIDesign {
	private JFrame newFrame;
	private String password = "fghj";
	private boolean checkConnect;
	private JTabbedPane tabPane;
	private JLayeredPane passwordPane;
	private BookSearch bookSearch;
	private BookLoan bookLoan;
	private BorrowerManagement borrowerManagement;
	private Fines fines;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUIDesign startWindow = new GUIDesign();
				startWindow.newFrame.setVisible(true);
			}
		});

	}

	public GUIDesign(){
		
		newFrame = new JFrame();
		newFrame.setBounds(100, 100, 1100, 600);
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.getContentPane().setLayout(null);

		tabPane = new JTabbedPane(JTabbedPane.TOP);
		tabPane.setBounds(0, 0, 1100, 600);
		newFrame.getContentPane().add(tabPane);

		bookSearch = new BookSearch();
		bookLoan = new BookLoan();
		borrowerManagement = new BorrowerManagement();
		fines = new Fines();
		
		passwordPane= new JLayeredPane();
		tabPane.addTab("Connection Pane", null, passwordPane, null);
		passwordPane.setLayout(null);
		
		JPanel PasswordPanel = new JPanel();
		PasswordPanel.setBounds(0, 0,1100, 600);
		passwordPane.add(PasswordPanel);
		PasswordPanel.setLayout(null);
		
		

		JButton enterPassword = new JButton("Click Here");
		enterPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				checkConnect = DBconnect.checkSQLConnection(password);
				if (checkConnect == true) {
					tabPane.add("Book Search", bookSearch);
					tabPane.add("Book Loans", bookLoan);
					tabPane.add("Borrower Management", borrowerManagement);
					tabPane.add("Fines", fines);
					tabPane.remove(passwordPane);
				}
			}
		});
		enterPassword.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		enterPassword.setForeground(Color.blue);
		enterPassword.setBounds(0, 0, 350, 270);
		PasswordPanel.add(enterPassword);

	}

}
