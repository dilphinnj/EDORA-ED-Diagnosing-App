import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Business.UpdateAppointment;
import Data.DataConnection;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;


public class CreateAppointment {

	private JFrame frame;
	private JTextField txtDate;
	protected static String pName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAppointment window = new CreateAppointment(pName);
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
	public CreateAppointment(String patName) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 597, 411);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAppointmentTime = new JLabel("Appointment for Consultant =>");
		lblAppointmentTime.setBounds(58, 111, 183, 14);
		frame.getContentPane().add(lblAppointmentTime);
		
		JLabel lblAppointmentTime_1 = new JLabel("Appointment Time");
		lblAppointmentTime_1.setBounds(58, 170, 181, 14);
		frame.getContentPane().add(lblAppointmentTime_1);
		
		JLabel lblAppointmentDate = new JLabel("Appointment Date");
		lblAppointmentDate.setBounds(58, 226, 174, 14);
		frame.getContentPane().add(lblAppointmentDate);
		
		
		Date dtToday = new Date(Calendar.getInstance().getTime().getTime());
		
		txtDate = new JTextField();
		txtDate.setBounds(314, 217, 183, 32);
		frame.getContentPane().add(txtDate);
		txtDate.setColumns(10);
		txtDate.setText(dtToday.toString());
	
		
		final JComboBox cmbTime = new JComboBox();
		cmbTime.setModel(new DefaultComboBoxModel(new String[] {"04:00 PM", "04:30 PM", "05:00 PM", "05:30 PM", "06:00 PM", "06:30 PM"}));
		cmbTime.setBounds(314, 167, 111, 20);
		frame.getContentPane().add(cmbTime);
		
		final UpdateAppointment upApp = new UpdateAppointment();
		
		final JComboBox cmbConsList = new JComboBox();		
		cmbConsList.setModel(new DefaultComboBoxModel(upApp.getConNamesList()));
		cmbConsList.setBounds(314, 108, 172, 20);
		frame.getContentPane().add(cmbConsList);
		
		JButton btnAddAppointment = new JButton("Add an Appointment");
		btnAddAppointment.setBounds(212, 287, 157, 23);
		frame.getContentPane().add(btnAddAppointment);
		
		btnAddAppointment.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(cmbTime.getSelectedItem().toString());			
				upApp.addAppointment(cmbConsList.getSelectedItem().toString(), Date.valueOf(txtDate.getText()),cmbTime.getSelectedItem().toString(), "Ms J Perera");
				JOptionPane.showMessageDialog(null,"Successfully Added Appointment!");
			}
		});
		
		
		JLabel lblCreateAnAppointment = new JLabel("Create an Appointment with Consultant");
		lblCreateAnAppointment.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		lblCreateAnAppointment.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateAnAppointment.setBounds(175, 29, 277, 23);
		frame.getContentPane().add(lblCreateAnAppointment);
	}
}
