package Program6;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    String msg;
    
    public Utilities(){
        
    }
    
    public ResultSet dbQuery(String sql){
        ResultSet rs = null;
        try{
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e){
            
        }
        return rs;
    }
    
    public void dbInsertAssignment(String name, String description, Date date){
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = df.format(date);
            msg = dateString + "   ";
            String sql = "insert into Assignments (name, description, duedate) values (?, ?, ?)"; 
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, dateString);
            ps.execute();
        } catch (SQLException e){
            msg += e.getMessage();
        }
    }
    
    public void dbInsertSubmission(int assn, int subid){
        try{
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = df.format(date);
            msg = dateString + "   ";
            String sql = "insert into submissions (assn, subid, subdate) values (?, ?, ?)"; 
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, assn);
            ps.setInt(2, subid);
            ps.setString(3, dateString);
            ps.execute();
        } catch (SQLException e){
            msg += e.getMessage();
        }
    }
    
    public void dbEditAssignment(int assn, String name, String description, Date date){
        int rows;
        try{
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
            Statement stmt = con.createStatement();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = df.format(date);
            String sql = "update assignments set name='" + name + "', description='"+ description + "', duedate='" + dateString + 
                         "' where assn= " + assn;
            rows = stmt.executeUpdate(sql);
            //msg = dateString + "   ";
        } catch (SQLException e){
            msg = e.toString();
        }
    }
    
    public void dbAddFeedback(String feedback, int assn, int subn){
        int rows;
        try{
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
            Statement stmt = con.createStatement();
            String sql = "update submissions set feedback='" + feedback + 
                         "' where subn= " + subn;
            rows = stmt.executeUpdate(sql);
            //msg = dateString + "   ";
        } catch (SQLException e){
            msg = e.toString();
        }
    }
    
    public void dbEditStudent(int id, String fn, String ln, String ea, String pw){
        int rows;
        try{
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
            Statement stmt = con.createStatement();
            String sql = "update newusers set firstname='" + fn + "', lastname='"+ ln + "', email='" + ea + 
                         "', password='" + pw + "' where id= " + id;
            rows = stmt.executeUpdate(sql);
            
        } catch (SQLException e){
            msg = e.toString();
        }
    }
    
    public void dbDeleteAssignment(String sql){
        try{
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
            Statement stmt = con.createStatement();
            stmt.execute(sql); 
        } catch (SQLException e){
            msg = e.toString();
        }
        
    }

    public String getMsg() {
        return msg;
    }
    
}
