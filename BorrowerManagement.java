import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class BorrowerManagement extends JLayeredPane {
	
	private JTextField textSSN;
	private JTextField textFname;
	private JTextField textLname;
	private JTextField textAddress;
	private JTextField textPhone;
	private JTextField textState;
	private JTextField textCity;
	
	public BorrowerManagement() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1100, 600);
		add(panel);
		
		JLabel labelSSN = new JLabel("SSN（xxx-xx-xxxx）");
		labelSSN.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelSSN.setBounds(20, 68, 140, 15);
		panel.add(labelSSN);
		
		JLabel labelFname = new JLabel("Fname");
		labelFname.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelFname.setBounds(20, 89, 140, 15);
		panel.add(labelFname);
		
		JLabel labelLname = new JLabel("Lname");
		labelLname.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelLname.setBounds(20, 110, 140, 15);
		panel.add(labelLname);
		
		JLabel labelAddress = new JLabel("Address");
		labelAddress.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelAddress.setBounds(20, 131, 140, 15);
		panel.add(labelAddress);
		
		JLabel labelCity = new JLabel("City");
		labelCity.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelCity.setBounds(20, 152, 140, 15);
		panel.add(labelCity);
		
		JLabel labelState = new JLabel("State（NY,TX,ect)");
		labelState.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelState.setBounds(20, 173, 140, 15);
		panel.add(labelState);
		
		JLabel labelPhone = new JLabel("Phone(xxx)xxx-xxxx");
		labelPhone.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelPhone.setBounds(20, 193, 180, 15);
		panel.add(labelPhone);
		
		JLabel labelFormat = new JLabel("*Please follow the format in the parentheses");
		labelFormat.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		labelFormat.setBounds(20, 216, 300, 15);
		panel.add(labelFormat);
		
		textSSN = new JTextField();
		textSSN.setColumns(10);
		textSSN.setBounds(300, 66, 120,21);
		panel.add(textSSN);
		

		textFname = new JTextField();
		textFname.setColumns(10);
		textFname.setBounds(300, 86, 240,21);
		panel.add(textFname);
		
		textLname = new JTextField();
		textLname.setColumns(10);
		textLname.setBounds(300, 106, 240,21);
		panel.add(textLname);
		
		textAddress = new JTextField();
		textAddress.setColumns(10);
		textAddress.setBounds(300, 126,240,21);
		panel.add(textAddress);
		
		textCity = new JTextField();
		textCity.setColumns(10);
		textCity.setBounds(300, 146, 60,21);
		panel.add(textCity);
		
		textState = new JTextField();
		textState.setColumns(10);
		textState.setBounds(300, 166, 40,21);
		panel.add(textState);
		
	    textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(300, 186,120,21);
		panel.add(textPhone);
		
		
		JButton createAccount = new JButton("Create New Borrower");
		createAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String SSN = textSSN.getText();
				String Fname = textFname.getText();
				String Lname = textLname.getText();
				String Address = textAddress.getText();
				String City=textCity.getText();
				String State=textState.getText();
				String Phone = textPhone.getText();
				String[] nameList = {SSN, Fname, Lname, Address, City, State, Phone};
				
			
				DBconnect.CreateNewAccount(nameList);
			}
		});
		createAccount.setForeground(Color.BLACK);
		createAccount.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		createAccount.setBounds(115, 300, 175, 40);
		panel.add(createAccount);
		

	}

}
