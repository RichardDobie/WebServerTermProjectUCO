
package Program6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.annotation.Resource;
import java.sql.DriverManager;
import javax.faces.model.SelectItem;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private String firstName;
    private String firstNameError;
    private String password; 
    private String passwordError;
    private String confirmPassword;
    private String confirmPasswordError;
    private String lastName;
    private String lastNameError;
    private String emailAddress;
    private String emailAddressError;
    private String phoneNumber;
    private String phoneNumberError;
    private String gender;
    private String genderError;
    private String languageString;
    private List<SelectItem> languages;
    private String languagesError;
    private String hometown;
    private String hometownError;
    private String isAdmin;
    private boolean registered;
    private String errorMessage;
    private int ID;
    private ResultSet rs;
    private Utilities util = new Utilities();
    private Object listStudents[];
    private List<Student> student = new ArrayList();
    
    public LoginBean() {
        
    }
    
    public String logIn(){
        boolean errorFound = false;
        if (!emailAddress.contains("@")) {
            errorFound = true;
            emailAddressError = "Please enter a valid e-mail address. Example: name@email.com";
        } else {
            emailAddressError = "";
        }
        if (password.isEmpty()){
            errorFound = true;
            passwordError = "Please enter a password";
        } else {
            passwordError = "";
        }
        if (errorFound) {
            return "index";
        }
        clearErrorMessages();
        registered = false;
        try {
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
        Statement stmt = con.createStatement();
        String query = "select ID, firstname, lastname, phonenumber, gender, languages, hometown, isadmin from newusers where email='" + emailAddress + "' and password='"+ password + "'";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()){
            ID = rs.getInt("ID");
            firstName = rs.getString("firstname");
            lastName = rs.getString("lastname");
            phoneNumber = rs.getString("phonenumber");
            gender = rs.getString("gender");
            languageString = rs.getString("languages");
            hometown = rs.getString("hometown");
            isAdmin = rs.getString("isadmin");
            if (isAdmin != null){
                updateStudents();
                return "AdminPage";
            }
            else {
            return "StudentPage";
            }
        } else {
            return "loginfailed.jsp";
        }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    
    public String register() throws SQLException {
        boolean errorFound = false;
        if (firstName.isEmpty()) {
            errorFound = true;
            firstNameError = "Please enter your first name";
        } else {
            firstNameError = "";
        }
        if (lastName.isEmpty()) {
            errorFound = true;
            lastNameError = "Please enter your last name";
        } else {
            lastNameError = "";
        }
        if (!emailAddress.contains("@")) {
            errorFound = true;
            emailAddressError = "Email incorrect. Example: name@email.com";
        } else {
            emailAddressError = "";
        }
        if (!password.equals(confirmPassword)){
            confirmPasswordError = "Passwords do not match";
            errorFound = true;
        }
        if ((phoneNumber.length() < 8) || (!phoneNumber.contains("-"))) {
            errorFound = true;
            phoneNumberError = "Phone number is incorrect. Example: xxx-xxxx";
        } else {
            phoneNumberError = "";
        }
        if ((gender == null) || (gender.isEmpty())) {
            errorFound = true;
            genderError = "Please select a gender.";
        } else {
            genderError = "";
        }
        if (languages.isEmpty()) {
            errorFound = true;
            languagesError = "Please select one or more languages.";
        } else {
            languagesError = "";
        }
        if (hometown.equals("--Choose One--")) {
            errorFound = true;
            hometownError = "Please select your hometown.";
        } else {
            hometownError = "";
        }
        if (errorFound) {
            return "Register";
        } else {
            clearErrorMessages();
            String destination = registerNewUser();
            return destination;
        }
    }
    
    public String registerNewUser() throws SQLException {
        registered = false;
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
        try {
            con.setAutoCommit(false);
            boolean committed = false;
            try {
                try{
                    PreparedStatement ps = con.prepareStatement("insert into NewUsers"
                        + "(firstname, lastname, email, phonenumber, gender, languages, hometown, password) values"
                        + "(?,?,?,?,?,?,?,?)");    
                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, emailAddress);
                    ps.setString(4, phoneNumber);
                    ps.setString(5, gender);
                    ps.setString(6, languages.toString());
                    ps.setString(7, hometown);
                    ps.setString(8, password);
                    boolean results = ps.execute();
                } catch (SQLException e){
                    return e.getMessage();
                }
                con.commit();
                registered = true;
                return "committed";
            } catch (SQLException e) {
                if(!committed){
                    con.rollback();
                    return e.getMessage();     
                }      
            }        
        } finally {
            if(registered == true){
                return "complete";
            }
        }
        return null;
    }
    
    public String setupForUpdate(int ID, String fname, String lname, String ea, String pw, String pn, String g, String ls, String ht){
        this.ID = ID;
        this.firstName = fname;
        this.lastName = lname;
        this.emailAddress = ea;
        this.password = pw;
        this.phoneNumber = pn;
        this.gender = g;
        this.languageString = ls;
        this.hometown = ht;
        return "editStudentInfo";
    }
    
    public String editStudent(){
        util.dbEditStudent(ID, firstName, lastName, emailAddress, password);
        errorMessage = util.getMsg();//+ "  " + duedate;
        updateStudents();
        if (isAdmin != null){
            return "AdminPage";
        } else {
            return "StudentPage";
        }
    }
    
    public void updateStudents(){
        String sql = "select * from newusers where isadmin is null";
        Student tempStudent;
        rs = util.dbQuery(sql);
        student.clear();
        try{
            while (rs.next()){
                ID = rs.getInt("id");
                firstName = rs.getString("firstname");
                lastName = rs.getString("lastname");
                emailAddress = rs.getString("email");
                password = rs.getString("password");
                phoneNumber = rs.getString("phonenumber");
                gender = rs.getString("gender");
                languageString = rs.getString("languages");
                hometown = rs.getString("hometown");
                tempStudent = new Student(ID, firstName, lastName, emailAddress, password, phoneNumber, gender, languageString, hometown);
                boolean b = student.add(tempStudent);
                listStudents = student.toArray();
        }
                    
        } catch (SQLException e){
            errorMessage += e.getMessage();
        }
    }
    
    public String cancel(){
        if (isAdmin != null){
            return "AdminPage";
        } else {
            return "StudentPage";
        }
        
    }
    
    public String killSession(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }
    
    public void clearErrorMessages(){
        firstNameError = "";
        lastNameError = "";
        emailAddressError = "";
        phoneNumberError = "";
        genderError = "";
        languagesError = "";
        hometownError = "";
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNameError() {
        return firstNameError;
    }

    public void setFirstNameError(String firstNameError) {
        this.firstNameError = firstNameError;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNameError() {
        return lastNameError;
    }

    public void setLastNameError(String lastNameError) {
        this.lastNameError = lastNameError;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddressError() {
        return emailAddressError;
    }

    public void setEmailAddressError(String emailAddressError) {
        this.emailAddressError = emailAddressError;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberError() {
        return phoneNumberError;
    }

    public void setPhoneNumberError(String phoneNumberError) {
        this.phoneNumberError = phoneNumberError;
    }

   public List<SelectItem> getGenderOptions() {
        return Utility.genderOptions();
    }
  
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderError() {
        return genderError;
    }

    public void setGenderError(String genderError) {
        this.genderError = genderError;
    }
    
    public List<SelectItem> getLanguageOptions() {
        return Utility.languageOptions();
    }
 
    public List<SelectItem> getLanguages() {
        return languages;
    }

    public void setLanguages(List<SelectItem> languages) {
        this.languages = languages;
    }

    public String getLanguagesError() {
        return languagesError;
    }

    public void setLanguagesError(String languagesError) {
        this.languagesError = languagesError;
    }
    
    public List<SelectItem> getHometownOptions() {
        return Utility.hometownOptions();
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getHometownError() {
        return hometownError;
    }

    public void setHometownError(String hometownError) {
        this.hometownError = hometownError;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError;
    }

    public void setConfirmPasswordError(String confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLanguageString() {
        return languageString;
    }

    public void setLanguageString(String languageString) {
        this.languageString = languageString;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object[] getListStudents() {
        return listStudents;
    }

    public void setListStudents(Object[] listStudents) {
        this.listStudents = listStudents;
    }
}
