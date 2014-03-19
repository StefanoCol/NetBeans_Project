/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.FileReader;
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
@WebServlet(urlPatterns = {"/ContattiSQL"})
public class ContattiSQL extends HttpServlet {

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
            out.println("<title>Servlet ContattiSQL</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ContattiSQL at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    
    private Connection getConnection(){
    Connection con=null;
    try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1;instanceName=SQLSERVER2012;databaseName=ITS2014;user=its2014;password=its2014;");
            
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
            
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
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            Gson gson = new Gson();
            ContattoViewModel contatto = (ContattoViewModel)gson.fromJson(reader, ContattoViewModel.class);
            
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            String query = "DELETE dbo.Contatti WHERE ContattoId=" + request.getParameter("ContattoId");
            ResultSet rs = stmt.executeQuery(query);
            
        } catch (IOException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            Gson gson = new Gson();
            ContattoViewModel contatto = (ContattoViewModel)gson.fromJson(reader, ContattoViewModel.class);
            String cognome = contatto.cognome;
            String nome = contatto.nome;
            String email = contatto.email;
            
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            String query = "UPDATE dbo.Contatti SET Cognome='" + cognome + "', Nome='" + nome + "', email='" + email + "' WHERE ContattoId=" + request.getParameter("ContattoId");
            ResultSet rs = stmt.executeQuery(query);
            
        } catch (IOException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String contattoId=request.getParameter("ContattoId");
         if (contattoId == null || contattoId.equals("")){
             doGetAll(request, response);
         }else{
             doGetSingle(contattoId, request, response);
         }
         
     }
    
     protected void doGetSingle(String contattoId, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         Connection con = getConnection();
         Statement stmt = null;
         ResultSet rs=null;
        try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = stmt.executeQuery("SELECT ContattoId, Cognome, Nome, EMail FROM dbo.Contatti"
                    + " Where ContattoId = " + contattoId);
            rs.next();
            ContattoViewModel viewModel = new ContattoViewModel();
            viewModel.cognome = rs.getString(2);
            viewModel.nome=rs.getString(3);
            viewModel.email=rs.getString(4);
            
            JsonWriter writer = new JsonWriter(response.getWriter());
            Gson gson = new Gson();
            gson.toJson(
                    viewModel
                    , ContattoViewModel.class
                    , writer
            );
            
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     }
    
    protected void doGetAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String s = request.getParameterNames().toString();
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ContattoId, Cognome, Nome, EMail FROM dbo.Contatti");    //iteratore aperto su una connessione odbc
            //è un navigatore, leggo i risultati un pò per volta
        
            response.setCharacterEncoding("utf-8");
            
            writer.print("[");

            /*FileReader fileContatti
                    = new FileReader("C:\\temp\\contatti.csv");
            BufferedReader readerContatti
                    = new BufferedReader(fileContatti);
            // salta la riga di intestazione
            readerContatti.readLine();*/

            boolean first = true;
            while (true) {
                
                
                
                if(!rs.next()) break;
                
                //String line = readerContatti.readLine();
                /*if (line == null) {
                    break;
                }
                if (line.equals("")) {
                    break;
                }*/

                //String[] values = line.split(",");

                if (first) {
                    first = false;
                } else {
                    writer.println(",");
                }

                writer.print("{");
                writer.printf(" \"ContattoId\": \"%s\"", rs.getString("ContattoId"));
                writer.printf(", \"cognome\": \"%s\"", rs.getString("Cognome"));
                writer.printf(", \"nome\": \"%s\"", rs.getString("Nome"));
                writer.printf(", \"email\": \"%s\"", rs.getString("EMail"));
                writer.print(" }");
            }

            //readerContatti.close();

            writer.print("]");
            con.close();
            
            } catch (SQLException ex) {
                writer.print("Errore :(");
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
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
        
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        ContattoViewModel contatto = (ContattoViewModel)gson.fromJson(reader, ContattoViewModel.class);
        
        try {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection con = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1;instanceName=SQLSERVER2012;databaseName=ITS2014;user=its2014;password=its2014;");
            Statement stmt = con.createStatement();
            
            String sql = "insert into dbo.Contatti (Cognome, Nome, EMail) "
                    + "VALUES("
                    + "'" + contatto.cognome + "'"
                    + ", '" + contatto.nome + "'"
                    + ", '" + contatto.email + "'"
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
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
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
