package Program6;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletOutputStream;
import javax.faces.context.FacesContext;



@ManagedBean
@SessionScoped
public class submissionBean implements Serializable {
    private int assn;
    private int ID;
    private int subn;
    private String name;
    private String firstName;
    private String lastName;
    private String assignmentName;
    private String feedBack;
    private String fileName;
    private String msg;
    private Submission submissions[];
    private Object listSubmissions[];
    private Object listStudentSubmissions[];
    private ArrayList<Submission> submission = new ArrayList();
    private ArrayList<Submission> studentSubmission = new ArrayList();
    private Utilities util = new Utilities();
    private ResultSet rs;
    /**
     * Creates a new instance of submissionBean
     */
    public submissionBean() {
    }
    
    public String prepareSubmission(int assn, int ID, String name){
        this.assn = assn;
        this.ID = ID;
        this.assignmentName = name;
        return "createSubmission";
    }
    
    public String cancel(){
        return "StudentPage";
    }
    
    public String createSubmission(){
        PreparedStatement ps;
        util.dbInsertSubmission(assn, ID);
        //msg = util.getMsg() + "  " + duedate;
        updateStudentSubmissions(assn);
        clearBeanAttributes();
        return "StudentPage";
    }
    
    public String updateStudentSubmissions(int ID){
        this.ID = ID;
        String sql = "select * from  assignments a, submissions s, files f where s.assn = a.assn and f.id=" + ID;
        rs = util.dbQuery(sql);
        Submission tempSubmission;
        studentSubmission.clear();
        try{
            while (rs.next()){
                int subid = rs.getInt("ID");
                String name = rs.getString("name");
                int subn = rs.getInt("subn");
                String feedback = rs.getString("feedback");
                String fileName = rs.getString("filename");
                Date subDate = rs.getDate("subdate");
                int assn = rs.getInt("assn");
                tempSubmission = new Submission(subid, subn, firstName, lastName, fileName, assn, subDate, feedback, name);
                boolean b = studentSubmission.add(tempSubmission);
                listStudentSubmissions = studentSubmission.toArray();
        }
                    
        } catch (SQLException e){
            msg = e.getMessage();
        }
        return "studentSubmissions";
    }
    
     public String updateSubmissions(int assn){
        String sql = "select * from submissions s, files f, newusers n where s.assn = " + assn + "and f.id=n.id and n.id=s.subid";
        rs = util.dbQuery(sql);
        Submission tempSubmission;
        submission.clear();
        try{
            while (rs.next()){
                int subid = rs.getInt("id");
                int subn = rs.getInt("subn");
                //String name = rs.getString("name");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastName");
                String feedback = rs.getString("feedback");
                String fileName = rs.getString("filename");
                Date subDate = rs.getDate("subdate");
                assn = rs.getInt("assn");
                tempSubmission = new Submission(subid, subn, firstName, lastName, fileName, assn, subDate, feedback, name);
                boolean b = submission.add(tempSubmission);
                listSubmissions = submission.toArray();
        }
                    
        } catch (SQLException e){
            msg += e.getMessage();
        }
        return "viewSubmissions";
    }
     
    public String delete(int subn){
        String sql = "delete from submissions were subn=" + subn;
        util.dbDeleteAssignment(sql);
        return updateStudentSubmissions(ID);
    }
    
    public String leaveFeedBack(int subn){
        util.dbAddFeedback(feedBack, assn, subn);
        this.msg = util.getMsg();
        String dest = updateSubmissions(assn);
        clearBeanAttributes();
        return "AdminPage";
    }
    
    
    public void clearBeanAttributes(){
        assn = 0;
        ID = 0;
        subn = 0;
        firstName = "";
        lastName = "";
        assignmentName = "";
        feedBack = "";
        fileName = "";
    }

    public void setComments(String feedBack) {
        this.feedBack = feedBack;
    }

    public int getAssn() {
        return assn;
    }

    public void setAssn(int assn) {
        this.assn = assn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSubn() {
        return subn;
    }

    public void setSubn(int subn) {
        this.subn = subn;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object[] getListStudentSubmissions() {
        return listStudentSubmissions;
    }

    public void setListStudentSubmissions(Object[] listStudentSubmissions) {
        this.listStudentSubmissions = listStudentSubmissions;
    }

    public Object[] getListSubmissions() {
        return listSubmissions;
    }

    public void setListSubmissions(Object[] listSubmissions) {
        this.listSubmissions = listSubmissions;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
