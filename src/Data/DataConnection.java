/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

//import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author dilphinn
 */
public class DataConnection {
    
    public Connection con = null;
    public Statement stmt = null;
 //   public java.sql.Statement stmt = null;
    
    public void createConn()
    {
        // adding a connection
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ed_db","ed_admin","edadmin");
        
        }catch(Exception e1)
        {
            System.out.println("Driver Error :"+ e1);
            e1.printStackTrace();
        }
    }
    
}
