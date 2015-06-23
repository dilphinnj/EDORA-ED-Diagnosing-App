package Business;

public class Registration {

	private String title;
	private String usrFullName;
	private int age = 0;
	private String qualifics = "Null";
	private String email;
	private String pass;
	private int telNo = 0;
	private String usrType;
	private String profs = "Null";
	
	public Registration(String title, String fn, String ln, String email, String pass, String ut, int age, String prof)
	{
		//this.usrName = un;
		this.title = title;
		this.email = email;
		this.pass = pass;
		this.usrFullName = fn + " " +ln;
		this.usrType = ut;
		this.age = age;
		this.profs = prof;
	}
	
	public Registration()
	{
		
	}
	
	public void setTel(int tel)
	{
		this.telNo = tel;
	}
	
	public String getUsrTitle()
	{
		return title;
	}
	
	public String getUsrFullName()
	{
		return usrFullName;
	}
		
	public int getUsrAge()
	{
		return age;
	}	
	
	public int getUsrTel()
	{
		return telNo;
	}	
	
	public String getUsrEmail()
	{
		return email;
	}
	
	public String getUsrPass()
	{
		return pass;
	}
	
	public String getUsrType()
	{
		return usrType;
	}
	
	public String getUsrProfs()
	{
		return profs;
	}
	
	public String getUsrQualifics()
	{
		return qualifics;
	}
	

}
