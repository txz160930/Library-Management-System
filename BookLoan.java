import java.sql.*;
import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookLoan extends JLayeredPane {

	private JTextField ISBN;
	private JTextField cardNum;
	private JTextField bookID;
	private JTextField cardNum2;
	Connection getConnection;

	public BookLoan() {
		JPanel Checking = new JPanel();
		Checking.setToolTipText("");
		Checking.setBounds(0, 0, 1000, 600);
		add(Checking);
		Checking.setLayout(null);

		ISBN = new JTextField();
		ISBN.setBounds(100, 20, 100, 20);
		Checking.add(ISBN);
		ISBN.setColumns(10);

		JLabel NewISBN = new JLabel("ISBN");
		NewISBN.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		NewISBN.setBounds(20, 20, 100, 20);
		Checking.add(NewISBN);

		cardNum = new JTextField();
		cardNum.setBounds(100, 40, 100, 20);
		Checking.add(cardNum);
		cardNum.setColumns(10);

		JLabel NewCardNo = new JLabel("Card ID");
		NewCardNo.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		NewCardNo.setBounds(20, 40, 100, 20);
		Checking.add(NewCardNo);

		JButton checkOut = new JButton("Check Out");
		checkOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ISBN.getText().length() != 13) {
					JOptionPane.showMessageDialog(null, "ISBN's Length is not correct");
				} else {
					DBconnect.CheckingOut(ISBN.getText(), cardNum.getText());
				}
			}
		});
		checkOut.setForeground(Color.BLACK);
		checkOut.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		checkOut.setBounds(20, 80, 150, 40);
		Checking.add(checkOut);

		JLabel NewBookID = new JLabel("ISBN");
		NewBookID.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		NewBookID.setHorizontalAlignment(SwingConstants.LEFT);
		NewBookID.setBounds(350, 20, 100, 20);
		Checking.add(NewBookID);

		bookID = new JTextField();
		bookID.setBounds(430, 20, 100, 20);
		Checking.add(bookID);
		bookID.setColumns(10);

		JLabel NewCardNo2 = new JLabel("Card ID");
		NewCardNo2.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		NewCardNo2.setHorizontalAlignment(SwingConstants.LEFT);
		NewCardNo2.setBounds(350, 40, 100, 20);
		Checking.add(NewCardNo2);

		cardNum2 = new JTextField();
		cardNum2.setBounds(430, 40, 100, 20);
		Checking.add(cardNum2);
		cardNum2.setColumns(10);

		JScrollPane PaneCheckIn = new JScrollPane();
		PaneCheckIn.setBounds(340, 133, 626, 319);
		Checking.add(PaneCheckIn);

		JTable checkIn = new JTable();
		PaneCheckIn.setViewportView(checkIn);
		
		JButton ButtonCheckingIn = new JButton("Check In");
		ButtonCheckingIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkIn.getValueAt(checkIn.getSelectedRow(), 5) != null) {
					JOptionPane.showMessageDialog(null, "This book has been checked in");
				} else {
					String ISBN = String.valueOf(checkIn.getValueAt(checkIn.getSelectedRow(), 1));
					DBconnect.CheckingIn(ISBN);
				}
			}
		});
		ButtonCheckingIn.setForeground(Color.GREEN);
		ButtonCheckingIn.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		ButtonCheckingIn.setBounds(530, 80, 150, 40);
		Checking.add(ButtonCheckingIn);

		JButton Search = new JButton("Search");
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bookID.getText().length() == 0 && cardNum2.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "Please input content in 'BookID, Card_id' Field!");
				else {
					try {
						getConnection = DBconnect.getConnector();
						ResultSet rs = DBconnect.SearchBeforeCheckingIn(getConnection, bookID.getText(),
								cardNum2.getText());
						checkIn.setModel(DbUtils.resultSetToTableModel(rs));
						rs.close();
						getConnection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});

		Search.setForeground(Color.GREEN);
		Search.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		Search.setBounds(350, 80, 150, 40);
		Checking.add(Search);

		
	}
}
