package Program6;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileUploadException;

@WebServlet(name = "download", urlPatterns = {"/download"})
public class download extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/octet-stream");
        ServletOutputStream outStream = response.getOutputStream();
        Program6.submissionBean subn = (Program6.submissionBean) session.getAttribute("submissionBean");
        try {
            try {
                String assN = request.getParameter("assn");
                String ID = request.getParameter("id");
                String sql = "select filename, filedata from FILES where assn=? and id=?";
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/WSP2013","username","password");
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, assN);
                ps.setString(2, ID);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String filename = rs.getString("filename");
                Blob fileData = rs.getBlob("filedata");
                byte[] b = new byte[(int)fileData.length()];
                InputStream in = fileData.getBinaryStream(1, fileData.length());
                int read = in.read(b);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                outStream.write(b);  
            }
            catch (SQLException e) {
                
           }
        } finally {            
            //outStream.close();
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
