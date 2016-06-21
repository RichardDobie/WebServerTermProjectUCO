package Program6;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Doobifier
 */
@ManagedBean
@SessionScoped
public class AssignmentBean implements Serializable {
    private int assN;
    private String name;
    private String description;
    private String msg;
    private Date duedate;
    private Object listAssignments[];
    private Assignment assignments[];
    private Assignment tempAssignment;
    private ArrayList assignment = new <Assignment> ArrayList();;
    private static Utilities util = new Utilities();
    private ResultSet rs;
    
    public AssignmentBean() {
        String sql = "select * from assignments";
        rs = util.dbQuery(sql); 
        
        try{
            while (rs.next()){
                int assn = rs.getInt("assn");
                String nname = rs.getString("name");
                String ndescription = rs.getString("description");
                Date nduedate = rs.getDate("duedate");
                tempAssignment = new Assignment(assn, nname, ndescription, nduedate);
                boolean b = assignment.add(tempAssignment);
                listAssignments = assignment.toArray();
        }
                    
        } catch (SQLException e){
            msg = e.getMessage();
        }
    }
    
    public String setupForUpdate(int assn, String n, String d, Date dd){
        this.assN = assn;
        this.name = n;
        this.description = d;
        this.duedate = dd;
        return "editAssignment";
    }
    
    public String editAssignment(){
        util.dbEditAssignment(assN, name, description, duedate);
        msg = util.getMsg();//+ "  " + duedate;
        updateAssignments();
        clearBeanAttributes();
        return "AdminPage";
    }
    
    public String createAssignment(){
        PreparedStatement ps;
        util.dbInsertAssignment(name, description, duedate);
        //msg = util.getMsg() + "  " + duedate;
        updateAssignments();
        return "uploadFile";
    }
    
    public String cancel(){
        clearBeanAttributes();
        updateAssignments();
        return "AdminPage";
    }
    
    public void clearBeanAttributes(){
        name = "";
        description = "";
        duedate = null;
    }
    
    public void updateAssignments(){
        String sql = "select * from assignments";
        rs = util.dbQuery(sql);
        assignment.clear();
        try{
            while (rs.next()){
                String nname = rs.getString("name");
                String ndescription = rs.getString("description");
                Date nduedate = rs.getDate("duedate");
                int assn = rs.getInt("assn");
                tempAssignment = new Assignment(assn, nname, ndescription, nduedate);
                boolean b = assignment.add(tempAssignment);
                listAssignments = assignment.toArray();
        }
                    
        } catch (SQLException e){
            msg += e.getMessage();
        }
    }
    
    public String deleteAssignment(int assn){
        String sql = "delete from assignments where assn=" + assn;
        util.dbDeleteAssignment(sql);
        updateAssignments();
        return "AdminPage";
    }

    public Object[] getListAssignments() {
        return listAssignments;
    }

    public void setListAssignments(Object[] listAssignments) {
        this.listAssignments = listAssignments;
    }

    public Assignment[] getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignment[] assignments) {
        this.assignments = assignments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}

