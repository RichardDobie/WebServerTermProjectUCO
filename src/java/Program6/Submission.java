package Program6;

import java.util.Date;

public class Submission {
    private int subn;
    private int subid;
    private int assn;
    private String name;
    private String firstName;
    private String lastName;
    private String filename;
    private Date subDate;
    private String feedback;
    
    public Submission(int subid, int subn, String firstName, String lastName, String fileName, int assn, Date subDate, String feedback, String name){
        this.subid = subid;
        this.subn = subn;
        this.assn = assn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.filename = fileName;
        this.subDate = subDate;
        this.feedback = feedback;
        this.name = name;
    }

    public int getSubn() {
        return subn;
    }

    public void setSubn(int subn) {
        this.subn = subn;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public int getAssn() {
        return assn;
    }

    public void setAssn(int assn) {
        this.assn = assn;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getSubDate() {
        return subDate;
    }

    public void setSubDate(Date subDate) {
        this.subDate = subDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
