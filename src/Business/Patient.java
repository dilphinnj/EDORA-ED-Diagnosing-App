package Business;

public class Patient {
	
	private String title; 
	private String name; 
	private int age;
	private int tel; 
	private String address;
	
	public Patient (String pTit, String pN, int pAge, int pTel, String pAdd)
	{
		this.title = pTit;
		this.name = pN;
		this.age = pAge;
		this.tel = pTel;
		this.address = pAdd;
	}
	
	
	public String getPatTitle()
	{
		return title;
		
	}
	
	public String getPatName()
	{
		return name;
		
	}
	
	public int getPatAge()
	{
		return age;
		
	}
	
	public int getPatTel()
	{
		return tel;
		
	}
	
	public String getPatAddress()
	{
		return address;
		
	}
	
		

}
