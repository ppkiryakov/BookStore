package bg.pragmatic.bookstore.ui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import bg.pragmatic.bookstore.users.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog {
	
	private static final String ADMIN_USER_PASSWORD = "admin";
	private static final String NORMAL_USER_PASSWORD = "user";
	
	private JTextField textField;
	
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDialog dialog = new LoginDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public LoginDialog() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 340, 200);
		getContentPane().setLayout(null);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(6, 62, 98, 16);
		getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(94, 56, 240, 28);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogIn.setBounds(100, 143, 140, 29);
		getContentPane().add(btnLogIn);
	}
	
	public User getUser() {
		return user;
	}
	
	private void login() {
		if(textField.getText().equals(ADMIN_USER_PASSWORD)) {
			user = new AdminUser();
			setVisible(false);
		} else if(textField.getText().equals(NORMAL_USER_PASSWORD)) {
			user = new NormalUser();
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(this, 
					"Invalid password. Please, try again.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
