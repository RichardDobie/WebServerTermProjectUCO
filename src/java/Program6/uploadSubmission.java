package Program6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "uploadSubmission", urlPatterns = {"/uploadSubmission"})
public class uploadSubmission extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Program6.LoginBean user = (Program6.LoginBean) session.getAttribute("loginBean");
        Program6.submissionBean subn = (Program6.submissionBean) session.getAttribute("submissionBean");
        PrintWriter out = response.getWriter();
        try {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload fupload = new ServletFileUpload(factory);
                fupload.setFileSizeMax(10485760);
                List items;
                //int ID = Integer.parseInt(request.getParameter("ID"));
                items = fupload.parseRequest(request);
                String fileName;
                String sql = "insert into files (filename, filedata, id, assn) values (?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
                PreparedStatement ps = con.prepareStatement(sql);
                boolean emptyName;
                emptyName = false;
                for (int i=0; i<items.size(); i++){
                    FileItem fileItem = (FileItem) items.get(i);
                    fileName = fileItem.getName();
                    if (fileName.equals("")) {
                        emptyName = true;
                    } 
                    if (!emptyName) {
                        InputStream input = fileItem.getInputStream();
                        ps.setString(1, fileName);
                        ps.setBinaryStream(2, input);
                        ps.setInt(3, subn.getID());
                        ps.setInt(4, subn.getAssn());
                        ps.execute();
                    }
                    else {
                        emptyName = true;
                    }   
                }
                if (emptyName==true) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Could Not Upload</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("Please select a file before pressing Upload<br>");
                    out.println("<a href=\"createSubmission.xhtml\">Return</a>");
                    out.println("</body>");
                    out.println("</html>");
                    con.commit();
                    con.close();
                }
                else {
                    con.commit();
                    con.close();
                    response.sendRedirect("createSubmission.xhtml");
                }
            }
            catch (SQLException e) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error Uploading</title>");
                out.println("</head>");
                out.println("<body>");
                out.println(e.getMessage() + "<br>");
                out.println("<a href=\"createSubmission.xhtml\">Return</a>");
                out.println("</body>");
                out.println("</html>");
           }
           catch (FileUploadException e){
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error Uploading</title>");
                out.println("</head>");
                out.println("<body>");
                out.println(e.getMessage() + "<br>");
                out.println("<a href=\"createSubmission.xhtml\">Return</a>");
                out.println("</body>");
                out.println("</html>");
           }
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
