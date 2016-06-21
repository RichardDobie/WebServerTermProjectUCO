<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Beat Dropbox</title>
        <link rel="stylesheet" href="BeatDropbox.css">
    </head>
    <%
        request.getSession();
        Program6.LoginBean user = (Program6.LoginBean) session.getAttribute("loginBean");
        if(user == null){
            response.sendRedirect("index.xhtml");
        }
        int ID = user.getID();
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
        String sql = "Select * from files where ID = " + ID ;
        ResultSet rs;
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
    %>    
    <body>
        <h1>Beat Dropbox!</h1>
        <h2>Welcome <%= user.getFirstName() %> <%= user.getLastName() %></h2>
        <hr>
        
        <b>Your Information:</b><br>
        <b>E-mail</b>: <%= user.getEmailAddress() %><br>
        <b>Phone:</b> <%= user.getPhoneNumber() %><br>
        <b>Gender:</b> <%= user.getGender() %><br>
        <b>Languages:</b> <%= user.getLanguageString() %><br>
        <b>Home Town:</b> <%= user.getHometown() %><br>
        <hr>
        <form action="upload" method="post" enctype="multipart/form-data">
            Upload a file: <input type="file" name="filename"><br>
            <input type="submit" value="Upload">
        </form>
        <br>
        <hr>
        <table border="1">
            <tr>
                <th>Assignment</th>
                <th>Download</th>
                <th>Delete</th>
            </tr>
            <%
                String filename = "";
                while ( rs.next() ){
                    filename = rs.getString("filename");
            %>
            <tr>
                <td><%= filename %></td>
                <td>
                    <form action="download" method="post">
                        <input type="hidden" name="filename" value="<%= filename %>">
                        <input type="submit" value="Download">
                    </form>
                </td>
                <td>
                    <form action="delete.jsp" method="post">
                        <input type="hidden" name="filename" value="<%= filename %>">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
        <hr>
         <form action="logout" method="post">
            <input type="submit" value="Log Out">
         </form>
    </body>
</html>
