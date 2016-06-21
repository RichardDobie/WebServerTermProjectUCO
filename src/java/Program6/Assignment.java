package Program6;


import java.util.Date;
import java.io.Serializable;


public class Assignment implements Serializable {
    private int assN;
    private String name;
    private String description;
    private Date duedate;
    private String filenames[];
    private static Utilities util;
    
    public Assignment (int assN, String name, String description, Date duedate){
        this.assN = assN;
        this.name = name;
        this.description = description;
        this.duedate = duedate;
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

    public String[] getFilenames() {
        return filenames;
    }

    public void setFilenames(String[] filenames) {
        this.filenames = filenames;
    }

    public int getAssN() {
        return assN;
    }

    public void setAssN(int assN) {
        this.assN = assN;
    }
}
