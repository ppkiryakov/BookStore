package bg.pragmatic.bookstore.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import bg.pragmatic.bookstore.model.Book;
import bg.pragmatic.bookstore.model.ProductStock;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BookStockAddEditDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldTitle;
	private JTextField textFieldAuthorName;
	private JTextField textFieldPublishersName;
	private JCheckBox checkBoxIsForeign;
	private JSpinner spinnerCount;
	private JSpinner spinnerPrice;
	
	private ProductStock bookStock;
	private boolean isBookStockUpdated;

	/**
	 * Create the dialog.
	 */
	public BookStockAddEditDialog() {
		setTitle("Add book");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Count:");
		lblNewLabel.setBounds(6, 6, 124, 16);
		contentPanel.add(lblNewLabel);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(6, 45, 124, 16);
		contentPanel.add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(6, 85, 124, 16);
		contentPanel.add(lblAuthor);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(6, 125, 124, 16);
		contentPanel.add(lblPrice);
		
		JLabel lblPublishersName = new JLabel("Publisher's name:");
		lblPublishersName.setBounds(6, 165, 124, 16);
		contentPanel.add(lblPublishersName);
		
		JLabel lblIsForeign = new JLabel("Is foreign:");
		lblIsForeign.setBounds(6, 206, 124, 16);
		contentPanel.add(lblIsForeign);
		
		spinnerCount = new JSpinner();
		spinnerCount.setBounds(142, 6, 80, 28);
		contentPanel.add(spinnerCount);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setBounds(142, 39, 302, 28);
		contentPanel.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		textFieldAuthorName = new JTextField();
		textFieldAuthorName.setBounds(142, 79, 302, 28);
		contentPanel.add(textFieldAuthorName);
		textFieldAuthorName.setColumns(10);
		
		spinnerPrice = new JSpinner();
		spinnerPrice.setBounds(142, 119, 80, 28);
		contentPanel.add(spinnerPrice);
		
		textFieldPublishersName = new JTextField();
		textFieldPublishersName.setBounds(142, 159, 302, 28);
		contentPanel.add(textFieldPublishersName);
		textFieldPublishersName.setColumns(10);
		
		checkBoxIsForeign = new JCheckBox("");
		checkBoxIsForeign.setBounds(141, 199, 45, 23);
		contentPanel.add(checkBoxIsForeign);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Create empty product stock that holds book if needed
						if(bookStock == null) {
							bookStock = new ProductStock(new Book(), 0);
						}
						updateBookStockPropertiesFromUI();
						isBookStockUpdated = true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						isBookStockUpdated = false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setBookForEdit(ProductStock bookStock) {
		this.bookStock = bookStock;
		populateUI();
	}

	public ProductStock getBookStock() {
		return bookStock;
	}

	public boolean isBookStockUpdated() {
		return isBookStockUpdated;
	}

	private void populateUI() {
		Book book = (Book)bookStock.getProduct();
		spinnerCount.setValue(bookStock.getCount());
		textFieldTitle.setText(book.getTitle());
		textFieldAuthorName.setText(book.getAuthor());
		textFieldPublishersName.setText(book.getPublishersName());
		spinnerPrice.setValue(book.getPrice());
		checkBoxIsForeign.setSelected(book.isForeignLiterature());
	}
	
	private void updateBookStockPropertiesFromUI() {
		Book book = (Book)bookStock.getProduct();
		bookStock.setCount(Integer.valueOf(String.valueOf(spinnerCount.getValue())));
		book.setTitle(textFieldTitle.getText());
		book.setAuthor(textFieldAuthorName.getText());
		book.setPublishersName(textFieldPublishersName.getText());
		book.setPrice(Double.valueOf(String.valueOf(spinnerPrice.getValue())));
		book.setForeignLiterature(checkBoxIsForeign.isSelected());
	}
}
