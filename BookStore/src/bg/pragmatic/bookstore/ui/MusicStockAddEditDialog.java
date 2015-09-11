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

import bg.pragmatic.bookstore.model.Music;
import bg.pragmatic.bookstore.model.ProductStock;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MusicStockAddEditDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldTitle;
	private JTextField textFieldAuthorName;
	private JSpinner spinnerCount;
	private JSpinner spinnerPrice;
	private JSpinner spinnerYear;
	private JSpinner spinnerSongsCount;
	
	private ProductStock musicStock;
	private boolean isMusicStockUpdated;

	/**
	 * Create the dialog.
	 */
	public MusicStockAddEditDialog() {
		setTitle("Add music");
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
		
		JLabel lblPublishersName = new JLabel("Year:");
		lblPublishersName.setBounds(6, 165, 124, 16);
		contentPanel.add(lblPublishersName);
		
		JLabel lblIsForeign = new JLabel("Songs count:");
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
		
		spinnerYear = new JSpinner();
		spinnerYear.setBounds(142, 159, 80, 28);
		contentPanel.add(spinnerYear);
		
		spinnerSongsCount = new JSpinner();
		spinnerSongsCount.setBounds(142, 200, 80, 28);
		contentPanel.add(spinnerSongsCount);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Create empty product stock that holds book if needed
						if(musicStock == null) {
							musicStock = new ProductStock(new Music(), 0);
						}
						updateMusicStockPropertiesFromUI();
						isMusicStockUpdated = true;
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
						isMusicStockUpdated = false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setMusicStockForEdit(ProductStock musicStock) {
		this.musicStock = musicStock;
		populateUI();
	}

	public ProductStock getMusicStock() {
		return musicStock;
	}

	public boolean isMusicStockUpdated() {
		return isMusicStockUpdated;
	}

	private void populateUI() {
		Music music = (Music)musicStock.getProduct();
		spinnerCount.setValue(musicStock.getCount());
		textFieldTitle.setText(music.getTitle());
		textFieldAuthorName.setText(music.getAuthor());
		spinnerPrice.setValue(music.getPrice());
		spinnerYear.setValue(music.getYearOfPublish());
		spinnerSongsCount.setValue(music.getSongsCount());
	}
	
	private void updateMusicStockPropertiesFromUI() {
		Music music = (Music)musicStock.getProduct();
		musicStock.setCount(Integer.valueOf(String.valueOf(spinnerCount.getValue())));
		music.setTitle(textFieldTitle.getText());
		music.setAuthor(textFieldAuthorName.getText());
		music.setPrice(Double.valueOf(String.valueOf(spinnerPrice.getValue())));
		music.setYearOfPublish(Integer.valueOf(String.valueOf(spinnerYear.getValue())));
		music.setSongsCount(Integer.valueOf(String.valueOf(spinnerSongsCount.getValue())));
	}
}