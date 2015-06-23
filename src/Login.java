

import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Data.DataConnection;

import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;



public class Login extends org.eclipse.swt.widgets.Composite {
	private DataBindingContext m_bindingContext;

	private Text txtEmail;
	private Text txtPass;
	private Label lblPW;
	private Label lblUN;
	private Button btnReg;
	private Button btnLogin;

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public Login(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(900, 600);
			this.setParent(getParent());
			getParent().setLocation(100,50);
			this.setBackground(SWTResourceManager.getColor(128, 128, 128));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setBackgroundImage(SWTResourceManager.getImage("bckg.jpg"));
			this.setForeground(SWTResourceManager.getColor(128, 0, 0));
			{
				lblUN = new Label(this, SWT.NONE);
				FormData lblUNLData = new FormData();
				lblUNLData.left =  new FormAttachment(0, 1000, 91);
				lblUNLData.top =  new FormAttachment(0, 1000, 245);
				lblUNLData.width = 140;
				lblUNLData.height = 35;
				lblUN.setLayoutData(lblUNLData);
				lblUN.setText("Email         :");
				lblUN.setBackgroundImage(SWTResourceManager.getImage("bcg.png"));
				lblUN.setFont(SWTResourceManager.getFont("Tw Cen MT Condensed", 18, 0, false, false));
				lblUN.setForeground(SWTResourceManager.getColor(255, 255, 255));
				lblUN.setAlignment(SWT.CENTER);
			}
			{
				txtEmail = new Text(this, SWT.NONE);
				FormData txtUsrNameLData = new FormData();
				txtUsrNameLData.left =  new FormAttachment(0, 1000, 302);
				txtUsrNameLData.top =  new FormAttachment(0, 1000, 248);
				txtUsrNameLData.width = 191;
				txtUsrNameLData.height = 32;
				txtEmail.setLayoutData(txtUsrNameLData);
			}
			
			
			{
				FormData text1LData = new FormData();
				text1LData.left =  new FormAttachment(0, 1000, 302);
				text1LData.top =  new FormAttachment(0, 1000, 314);
				text1LData.width = 191;
				text1LData.height = 32;
				txtPass = new Text(this, SWT.PASSWORD);
				txtPass.setLayoutData(text1LData);
			}
			
			
			
			
			{
				btnLogin = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 158);
				button1LData.top =  new FormAttachment(0, 1000, 416);
				button1LData.width = 124;
				button1LData.height = 53;
				button1LData.right =  new FormAttachment(1000, 1000, -618);
				button1LData.bottom =  new FormAttachment(1000, 1000, -131);
				btnLogin.setLayoutData(button1LData);
				btnLogin.setImage(SWTResourceManager.getImage("btn_img3.jpg"));
				btnLogin.setText("Login");
				btnLogin.setSelection(true);
				btnLogin.setFont(SWTResourceManager.getFont("Tw Cen MT Condensed Extra Bold", 16, 0, false, false));
				btnLogin.setBackground(SWTResourceManager.getColor(255, 0, 0));
				btnLogin.setForeground(SWTResourceManager.getColor(255, 0, 0));
				btnLogin.setBackgroundImage(SWTResourceManager.getImage("bcg2.jpg"));
				btnLogin.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
				btnLogin.setGrayed(true);
				
				
				/*btnLogin.addSelectionListener(new SelectionListener() {
					
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stubs
						getShell().setVisible(false);
						
						ConsultantMain cMain = new ConsultantMain();
						cMain.setVisible(true);*/
						
						
						btnLogin.addSelectionListener(new SelectionListener() {
							
							
							DataConnection dc = new DataConnection();
							
							public void widgetSelected(SelectionEvent e) {
								
							//getShell().setVisible(false);
							int noOfRows = 0;
							
							////Test :=>	System.out.println(txtSearchPatient.getText());
									
							try {
									dc.createConn();
									dc.stmt = dc.con.createStatement();
									String sql = "SELECT * FROM users WHERE User_Email ='"+txtEmail.getText()+"'";
									ResultSet rs = dc.stmt.executeQuery(sql);
									int id1 = 0;
									
									boolean full = rs.next();
									if(full == false)
									{
										System.out.print("Incorrect UN or PW");
										JOptionPane.showMessageDialog(null,"Incorrect User Name or Password... Please try again!","LOGIN ERROR",JOptionPane.PLAIN_MESSAGE);
									}

									rs.beforeFirst();
									
									
									while(rs.next())
									{
								//Test ::=>		System.out.print("UT ::=>"+rs.getString("User_Type"));
										if(rs.getString("User_Type").equals("Consultant"))
										{
											String name = rs.getString("User_Full_Name"); 
											String title = rs.getString("User_Title");
											ConsultantMain cm = new ConsultantMain(title+" "+name);
											cm.setVisible(true);
											getShell().setVisible(false);
									    }
										
										else if(rs.getString("User_Type").equals("Suspected Patient"))
										{
											String name = rs.getString("User_Full_Name"); 
											String title = rs.getString("User_Title");
											PatientMainWin pm = new PatientMainWin(title+" "+name);
											pm.setVisible(true);
											getShell().setVisible(false);
									    }
										
										else
											System.out.println("Incorrect User type");
										System.out.println(rs.getString("User_Type").toString());
										
										
									}
									
									
									/*rs = null;
									dc.stmt = null;
									sql = null;*/
									
							}catch(Exception e1)
							{
								e1.printStackTrace();
							}
						
					}
					
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
						
						
		
			{
				btnReg = new Button(this, SWT.PUSH | SWT.CENTER);
				btnReg.setText("Register");
				btnReg.setImage(SWTResourceManager.getImage("btn_img3.jpg"));
				FormData button2LData = new FormData();
				button2LData.left =  new FormAttachment(0, 1000, 340);
				button2LData.top =  new FormAttachment(0, 1000, 416);
				button2LData.width = 134;
				button2LData.height = 53;
				btnReg.setLayoutData(button2LData);
				btnReg.setSelection(true);
				btnReg.setBackground(SWTResourceManager.getColor(255,0,0));
				btnReg.setForeground(SWTResourceManager.getColor(255,0,0));
				btnReg.setFont(SWTResourceManager.getFont("Tw Cen MT Condensed Extra Bold", 16, 0, false, false));
				btnReg.setBackgroundImage(SWTResourceManager.getImage("bcg2.jpg"));
				btnReg.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
				
				//final Display display1 = Display.getDefault();
				Display displayFront = Display.getCurrent();
				final Shell shellReg = new Shell(displayFront);
				shellReg.setSize(900, 620);
				shellReg.setBounds(40,40,900,620);
				
				
						
				//final RegisterScreen rScrn = new RegisterScreen(get, getBackgroundMode());
				//rScrn.setVisible(false);
				
				btnReg.addSelectionListener(new SelectionListener() {
					
					public void widgetSelected(SelectionEvent e) {
						
						getShell().setVisible(false);
						shellReg.open();
						
						RegisterForm rFrm = new RegisterForm(shellReg, getBackgroundMode());
					
						
					}
					
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			{
				lblPW = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 91);
				label1LData.top =  new FormAttachment(0, 1000, 315);
				label1LData.width = 140;
				label1LData.height = 35;
				lblPW.setLayoutData(label1LData);
				lblPW.setText("Password    :");
				lblPW.setFont(SWTResourceManager.getFont("Tw Cen MT Condensed", 18, 0, false, false));
				lblPW.setForeground(SWTResourceManager.getColor(255, 255, 255));
				lblPW.setBackgroundImage(SWTResourceManager.getImage("bcg.png"));
				lblPW.setOrientation(SWT.HORIZONTAL);
				lblPW.setAlignment(SWT.CENTER);
			}
			
			

			this.layout();}
		} catch (Exception e) {
			e.printStackTrace();
		}
		m_bindingContext = initDataBindings();
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				Login inst = new Login(shell, SWT.NULL);
				//inst.setBackgroundImage(SWTResourceManager.getImage("bckg.jpg"));
				inst.setLayoutDeferred(true);
				Point size = inst.getSize();
				shell.setLayout(new FillLayout());
				//shell.setBackgroundImage(SWTResourceManager.getImage("bckg.jpg"));
				shell.layout();
				if(size.x == 0 && size.y == 0) {
					inst.pack();
					shell.pack();
				} else {
					Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
					shell.setSize(shellBounds.width, shellBounds.height);
				}
				shell.open();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
			}
		});
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		return bindingContext;
	}
}
