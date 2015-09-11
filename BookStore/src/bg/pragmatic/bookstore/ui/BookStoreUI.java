package bg.pragmatic.bookstore.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bg.pragmatic.bookstore.model.BookStore;
import bg.pragmatic.bookstore.model.ProductStock;
import bg.pragmatic.bookstore.model.ProductType;
import bg.pragmatic.bookstore.model.exceptions.LoadingFailedException;
import bg.pragmatic.bookstore.model.exceptions.SavingFailedException;
import bg.pragmatic.bookstore.model.tables.BooksTableModel;
import bg.pragmatic.bookstore.model.tables.MusicsTableModel;
import bg.pragmatic.bookstore.model.tables.ProductTableModel;
import bg.pragmatic.bookstore.users.User;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

@SuppressWarnings("serial")
public class BookStoreUI extends JFrame {

	private JPanel contentPane;
	
	private User user;
	private BookStore bookStore;
	
	JTabbedPane tabbedPane;
	
	private JTextField textFieldSearchProducts;
	private JTable tableBooks;
	private BooksTableModel booksTableModel;
	private JTable tableMusic;
	private MusicsTableModel musicsTableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User user = null;
					do {
						// Show the login dialog first
						LoginDialog loginDialog = new LoginDialog();
						loginDialog.setVisible(true);
						user = loginDialog.getUser();
					} while(user == null);
					
					// Show the main UI when login is finished
					BookStoreUI frame = new BookStoreUI();
					frame.user = user;
					frame.load();
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookStoreUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 57, 428, 301);
		contentPane.add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Books", null, scrollPane, null);
		
		booksTableModel = new BooksTableModel();
		tableBooks = new JTable(booksTableModel);
		scrollPane.setViewportView(tableBooks);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Musics", null, scrollPane_1, null);
		
		musicsTableModel = new MusicsTableModel();
		tableMusic = new JTable(musicsTableModel);
		scrollPane_1.setViewportView(tableMusic);
		
		textFieldSearchProducts = new JTextField();
		textFieldSearchProducts.setBounds(20, 13, 279, 28);
		contentPane.add(textFieldSearchProducts);
		textFieldSearchProducts.setColumns(10);
		
		JButton btnSearchProduct = new JButton("Search product");
		btnSearchProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProducts();
			}
		});
		btnSearchProduct.setBounds(310, 14, 138, 29);
		contentPane.add(btnSearchProduct);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		btnAdd.setBounds(460, 59, 134, 29);
		contentPane.add(btnAdd);
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteProduct();
			}
		});
		btnNewButton.setBounds(460, 100, 134, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Sell");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sellProduct();
			}
		});
		btnNewButton_1.setBounds(460, 182, 134, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Edit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editProduct();
			}
		});
		btnNewButton_2.setBounds(460, 141, 134, 29);
		contentPane.add(btnNewButton_2);
		
		bookStore = new BookStore();
	}
	
	private void load() {
		try {
			bookStore.loadAllProducts();
			booksTableModel.setProductStocks(bookStore.getProductStocks(ProductType.BOOK));
			musicsTableModel.setProductStocks(bookStore.getProductStocks(ProductType.MUSIC));
		} catch (LoadingFailedException e) {
			JOptionPane.showMessageDialog(this,
					"Unable to load book store",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			
			e.printStackTrace();
			// Close the window and terminate the application
			dispose();
		}
	}
	
	private void addProduct() {
		if(!user.canAddProducts()) {
			showUnsufficientPermissionsError();
			return;
		}
		
		if(tabbedPane.getSelectedIndex() == 0) {
			// "Books" tab is selected so add book
			BookStockAddEditDialog dialog = new BookStockAddEditDialog();
			dialog.setModal(true);
			dialog.setVisible(true);
			
			if(dialog.isBookStockUpdated()) {
				ProductStock bookStock = dialog.getBookStock();
				bookStore.addProduct(bookStock.getProduct(), bookStock.getCount());
				try {
					bookStore.save(ProductType.BOOK);
				} catch (SavingFailedException e) {
					e.printStackTrace();
				}
				booksTableModel.setProductStocks(bookStore.getProductStocks(ProductType.BOOK));
			}
		} else if(tabbedPane.getSelectedIndex() == 1) {
			// "Musics" tab is selected so add music
			MusicStockAddEditDialog dialog = new MusicStockAddEditDialog();
			dialog.setModal(true);
			dialog.setVisible(true);
			
			if(dialog.isMusicStockUpdated()) {
				ProductStock musicStock = dialog.getMusicStock();
				bookStore.addProduct(musicStock.getProduct(), musicStock.getCount());
				try {
					bookStore.save(ProductType.MUSIC);
				} catch (SavingFailedException e) {
					e.printStackTrace();
				}
				musicsTableModel.setProductStocks(bookStore.getProductStocks(ProductType.MUSIC));
			}
		}
	}
	
	private void deleteProduct() {
		if(!user.canDeleteProducts()) {
			showUnsufficientPermissionsError();
			return;
		}

		int selectedRow = getVisibleTableSelectedRow();		
		if(selectedRow != -1) {
			ProductTableModel tableModel = getVisibleTableModel();
			ProductStock selectedProductStock = tableModel.getProductStock(selectedRow);
			ProductType productType = selectedProductStock.getProduct().getType();
			bookStore.deleteProductStock(selectedProductStock);
			try {
				bookStore.save(productType);
			} catch (SavingFailedException e) {
				e.printStackTrace();
			}
			tableModel.setProductStocks(bookStore.getProductStocks(productType));
		}
	}
	
	private void editProduct() {
		if(!user.canEditProducts()) {
			showUnsufficientPermissionsError();
			return;
		}
		
		int selectedRow = getVisibleTableSelectedRow();
		if(selectedRow == -1) {
			return;
		}
		
		if(tabbedPane.getSelectedIndex() == 0) {
			// "Books" tab is selected so edit book
			BookStockAddEditDialog dialog = new BookStockAddEditDialog();
			dialog.setBookForEdit(booksTableModel.getProductStock(selectedRow));
			dialog.setModal(true);
			dialog.setVisible(true);
			
			if(dialog.isBookStockUpdated()) {
				try {
					bookStore.save(ProductType.BOOK);
				} catch (SavingFailedException e) {
					e.printStackTrace();
				}
				booksTableModel.setProductStocks(bookStore.getProductStocks(ProductType.BOOK));
			}
		} else if(tabbedPane.getSelectedIndex() == 1) {
			// "Musics" tab is selected so edit music
			MusicStockAddEditDialog dialog = new MusicStockAddEditDialog();
			dialog.setMusicStockForEdit(musicsTableModel.getProductStock(selectedRow));
			dialog.setModal(true);
			dialog.setVisible(true);
			
			if(dialog.isMusicStockUpdated()) {
				try {
					bookStore.save(ProductType.MUSIC);
				} catch (SavingFailedException e) {
					e.printStackTrace();
				}
				musicsTableModel.setProductStocks(bookStore.getProductStocks(ProductType.MUSIC));
			}
		}
	}
	
	private void sellProduct() {
		if(!user.canSellProducts()) {
			showUnsufficientPermissionsError();
			return;
		}
		
		int selectedRow = getVisibleTableSelectedRow();		
		if(selectedRow != -1) {
			ProductTableModel tableModel = getVisibleTableModel();
			ProductStock selectedProductStock = tableModel.getProductStock(selectedRow);
			if(selectedProductStock.getCount() > 0) {
				ProductType productType = selectedProductStock.getProduct().getType();
				
				bookStore.sellProductFromStock(selectedProductStock);
				try {
					bookStore.save(productType);
				} catch (SavingFailedException e) {
					e.printStackTrace();
				}
				tableModel.setProductStocks(bookStore.getProductStocks(productType));
			} else {
				JOptionPane.showMessageDialog(this, 
						"Product " + selectedProductStock.getProduct().getTitle() + " is out of stock",
						"Out of stock",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void searchProducts() {
		if(textFieldSearchProducts.getText().length() > 0) {
			List<ProductStock> foundProductStocks = bookStore.search(textFieldSearchProducts.getText());
			if(foundProductStocks.size() > 0) {
				FoundProductsDialog dialog = new FoundProductsDialog();
				dialog.setFoundProductStocks(foundProductStocks);
				dialog.setModal(true);
				dialog.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this,
						"No products found", 
						"Info",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Please, enter search term", 
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void showUnsufficientPermissionsError() {
		JOptionPane.showMessageDialog(this,
				"This user has no permissions to perfrom this action",
				"Insufficient permissions",
				JOptionPane.ERROR_MESSAGE);
	}
	
	private int getVisibleTableSelectedRow() {
		switch(tabbedPane.getSelectedIndex()) {
		case 0: return tableBooks.getSelectedRow();
		case 1: return tableMusic.getSelectedRow();
		}
		
		return -1;
	}
	
	private ProductTableModel getVisibleTableModel() {
		switch(tabbedPane.getSelectedIndex()) {
		case 0: return booksTableModel;
		case 1: return musicsTableModel;
		}
		
		return null;
	}
}
