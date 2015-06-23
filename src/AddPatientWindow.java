
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Business.UpdatePatient;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Window.Type;


public class AddPatientWindow {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtTel;
	private JTextField txtAdd;
	private JTextField txtAge;
	private static String consName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPatientWindow window = new AddPatientWindow(consName);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddPatientWindow(String cName) {
		initialize();
		this.consName = cName;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("EDORA");
		frame.setType(Type.POPUP);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 427);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblTitle.setBounds(41, 65, 46, 14);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblFullName.setBounds(41, 117, 110, 14);
		frame.getContentPane().add(lblFullName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblAddress.setBounds(41, 266, 110, 14);
		frame.getContentPane().add(lblAddress);
		
		JLabel lblTel = new JLabel("Tel No");
		lblTel.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblTel.setBounds(41, 205, 110, 14);
		frame.getContentPane().add(lblTel);
		
		final JComboBox cmbTitle = new JComboBox();
		cmbTitle.setModel(new DefaultComboBoxModel(new String[] {"Mr", "Mrs", "Miss", "Ms", "Dr", "Rev", "Prof"}));
		cmbTitle.setBounds(180, 62, 66, 20);
		frame.getContentPane().add(cmbTitle);
		
		txtName = new JTextField();
		txtName.setBounds(180, 109, 222, 31);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtTel = new JTextField();
		txtTel.setBounds(180, 197, 222, 31);
		frame.getContentPane().add(txtTel);
		txtTel.setColumns(10);
		
		txtAdd = new JTextField();
		txtAdd.setBounds(180, 248, 222, 50);
		frame.getContentPane().add(txtAdd);
		txtAdd.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(0, 0, 128));
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setBounds(100, 333, 89, 31);
		frame.getContentPane().add(btnUpdate);
		
		btnUpdate.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				UpdatePatient up = new UpdatePatient();
				try {
					up.addNewPatient(cmbTitle.getSelectedItem().toString(), txtName.getText(), Integer.parseInt(txtTel.getText()), txtAdd.getText(), Integer.parseInt(txtAge.getText()), consName);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
				
			}
		});
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBackground(new Color(0, 0, 128));
		btnClear.setForeground(Color.WHITE);
		btnClear.setBounds(253, 333, 89, 31);
		frame.getContentPane().add(btnClear);
		
		JLabel lblUpdateNewPatient = new JLabel("Enter New Patient Screen");
		lblUpdateNewPatient.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		lblUpdateNewPatient.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateNewPatient.setBounds(137, 11, 159, 31);
		frame.getContentPane().add(lblUpdateNewPatient);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblAge.setBounds(41, 158, 46, 14);
		frame.getContentPane().add(lblAge);
		
		txtAge = new JTextField();
		txtAge.setBounds(180, 151, 222, 31);
		frame.getContentPane().add(txtAge);
		txtAge.setColumns(10);
	}
}
