import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import net.proteanit.sql.DbUtils;

public class BookSearch extends JLayeredPane {

	private JTextField ISBN;
	private JTextField title;
	private JTextField author;
	private Connection getConnection;
	private JLabel numofRows;
	private JTable searchTable;
	private int count;

	public BookSearch() {

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 94, 969, 388);
		add(scrollPane);

		ISBN = new JTextField();
		ISBN.setBounds(118, 21, 200, 21);
		add(ISBN);
		ISBN.setColumns(10);

		title = new JTextField();
		title.setBounds(118, 41, 200, 21);
		add(title);
		title.setColumns(10);

		author = new JTextField();
		author.setBounds(118, 61, 200, 21);
		add(author);
		author.setColumns(10);

		JLabel Label_ISBN = new JLabel("ISBN");
		Label_ISBN.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		Label_ISBN.setBounds(20, 23, 54, 15);
		add(Label_ISBN);

		JLabel Label_BookTitle = new JLabel("Book Title");
		Label_BookTitle.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		Label_BookTitle.setBounds(20, 43, 81, 15);
		add(Label_BookTitle);

		JLabel Label_Author = new JLabel("Author");
		Label_Author.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		Label_Author.setBounds(20, 63, 100, 15);
		add(Label_Author);

		searchTable = new JTable();
		scrollPane.setViewportView(searchTable);

		JButton BookSearch = new JButton("Book Search");
		BookSearch.setForeground(Color.blue);
		BookSearch.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		BookSearch.setBounds(350, 20, 125, 43);
		add(BookSearch);

		numofRows = new JLabel("");
		numofRows.setBounds(0, 0, 0, 0);
		add(numofRows);

		BookSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ISBN.getText().length() == 0 && title.getText().length() == 0 && author.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Enter ISBN, Title, and Author");
				} else {
					try {
						getConnection = DBconnect.getConnector();
						ResultSet result = DBconnect.BookQuery(getConnection, ISBN.getText(), title.getText(),
								author.getText());
						if (result.last()) {
							count = result.getRow();
							result.beforeFirst();
						} else {
							count = 0;
						}
						numofRows.setText(" " + numofRows);
						searchTable.setModel(DbUtils.resultSetToTableModel(result));
						result.close();
						getConnection.close();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}

				}
			}
		});
	}
}
