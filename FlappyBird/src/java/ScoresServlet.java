/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(urlPatterns = {"/score"})
public class ScoresServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet scores</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet scores at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private Connection getConnection(){
    Connection con=null;
    try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1;instanceName=SQLSERVER2012;databaseName=FlappyBird;user=flappybird;password=flappybird;");
            
        } catch (SQLException ex) {
            Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
            
}
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //processRequest(request, response);
        
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Username, Score, Timestamp FROM dbo.topFive order by Score desc");    //iteratore aperto su una connessione odbc
            //è un navigatore, leggo i risultati un pò per volta
        
            response.setCharacterEncoding("utf-8");
            
            writer.print("[");

            boolean first = true;
            while (true) {               
                
                if(!rs.next()) break;

                if (first) {
                    first = false;
                } else {
                    writer.println(",");
                }

                writer.print("{");
                writer.printf(" \"username\": \"%s\"", rs.getString("Username"));
                writer.printf(", \"score\": \"%s\"", rs.getString("Score"));
                writer.printf(", \"timestamp\": \"%s\"", rs.getString("Timestamp"));
                writer.print(" }");
            }

            //readerContatti.close();

            writer.print("]");
            con.close();
            
            } catch (SQLException ex) {
                writer.print("Errore");
            Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
        
    }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);  
        
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        ScoreViewModel contatto = (ScoreViewModel)gson.fromJson(reader, ScoreViewModel.class);
        
        try {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection con = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1;instanceName=SQLSERVER2012;databaseName=FlappyBird;user=flappybird;password=flappybird;");
            Statement stmt = con.createStatement();
            
            String sql = "insert into dbo.Scores (Username, Score) "
                    + "VALUES("
                    + "'" + contatto.username + "'"
                    + ", '" + contatto.score + "'"
                    + ")";
            
            stmt.execute(sql);
            
            JsonWriter writer = new JsonWriter(response.getWriter());
            gson.toJson(
                    Risposta.OK, 
                    Risposta.class, 
                    writer
            );
            
            
            con.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(ScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        
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
