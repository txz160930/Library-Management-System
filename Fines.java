import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fines extends JLayeredPane {

	private JTable tableFines;
	private JTextField textCardNumber;
	private JLabel labelFinesNum;

	public Fines() {
		JScrollPane scrollPaneFine = new JScrollPane();
		scrollPaneFine.setBounds(350, 20, 700, 500);
		add(scrollPaneFine);

		tableFines = new JTable();
		scrollPaneFine.setViewportView(tableFines);

		JLabel LabelCardNumber = new JLabel("Card ID :");
		LabelCardNumber.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		LabelCardNumber.setBounds(20, 62, 150, 20);
		add(LabelCardNumber);

		textCardNumber = new JTextField();
		textCardNumber.setBounds(117, 60, 120, 25);
		add(textCardNumber);
		textCardNumber.setColumns(10);

		JButton showFines = new JButton("Check Fine");

		showFines.setForeground(Color.black);
		showFines.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		showFines.setBounds(120, 250, 160, 20);
		add(showFines);

		showFines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cardNum = textCardNumber.getText().trim();
				if (cardNum.length() != 8) {
					JOptionPane.showMessageDialog(null, "The length is 8");
				}
				int cnt = DBconnect.UpdateFines(tableFines, cardNum);
				labelFinesNum = new JLabel("");
				labelFinesNum.setBounds(0, 0, 0, 0);
				add(labelFinesNum);
				labelFinesNum.setText(" " + cnt);
			}
		});
		JButton enterPayment = new JButton("Update");

		enterPayment.setForeground(Color.black);
		enterPayment.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		enterPayment.setBounds(120, 280, 160, 20);
		add(enterPayment);
		enterPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String CardNum = (String) tableFines.getValueAt(tableFines.getSelectedRow(), 0);
				int paid = (Integer) tableFines.getValueAt(tableFines.getSelectedRow(), 2);
				Object ob = (Object) tableFines.getValueAt(tableFines.getSelectedRow(), 1);
				double EnterPayment = Double.parseDouble(ob.toString());

				if (paid == 0)
					JOptionPane.showMessageDialog(null, "Cannot Update");
				else
					DBconnect.UpdateFineRecord(CardNum, EnterPayment);
			}
		});

		JButton ButtonPaid = new JButton("Pay Fine");
		ButtonPaid.setForeground(Color.black);
		ButtonPaid.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		ButtonPaid.setBounds(120, 310, 160, 20);
		add(ButtonPaid);
		ButtonPaid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int paid = (Integer) tableFines.getValueAt(tableFines.getSelectedRow(), 2);
				String Card_no = (String) tableFines.getValueAt(tableFines.getSelectedRow(), 0);

				if (paid == 0)
					DBconnect.AlterPaid(Card_no);
				else
					JOptionPane.showMessageDialog(null, "Paid");
			}
		});

	}
}