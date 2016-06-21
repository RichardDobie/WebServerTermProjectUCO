<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Deleting...</title>
    </head>
    <%
        String filename = request.getParameter("filename");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
        Statement stmt = con.createStatement();
        String query = "delete from files where filename='" + filename + "'";
        stmt.execute(query);
        con.close();
        response.sendRedirect("BeatDropbox.jsp");
    %>
    <body>
        <h1>Deleting...</h1>
    </body>
</html>
