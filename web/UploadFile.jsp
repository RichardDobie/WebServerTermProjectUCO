<%-- 
    Document   : UploadFile
    Created on : May 1, 2013, 10:23:23 PM
    Author     : Doobifier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Upload File</h1>
        <form action="upload" method="post" enctype="multipart/form-data">
            Upload a file: <input type="file" name="filename"/><br/>
            <input type="submit" value="Upload"/>
        </form> 
    </body>
</html>
