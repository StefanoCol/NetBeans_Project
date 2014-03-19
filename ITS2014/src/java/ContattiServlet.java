/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(urlPatterns = {"/Contattiold"})
public class ContattiServlet extends HttpServlet {

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
            out.println("<head><title>Contatti</title></head>");
        
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String format =  request.getParameter("format");
        if(format==null) format="html";
        else if(format.equals("")) format="html";
        
        PrintWriter out = response.getWriter();
        
        if(format.equals("html")){        
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Contatti</title></head>");
            out.println("<body>");
            out.println("<h1>Contatti</h1>");
            out.println("<table border='1' id='contatti'>");
            out.println("<thead>");
            out.println("<tr><th>Cognome</th><th>Nome</th><th>Telefono</th></tr>");
            out.println("</thead>");
            out.println("<tbody>");

            FileReader streamContatti = 
                    new FileReader("C:\\Remp\\contatti.csv");
            BufferedReader readerContatti=
                    new BufferedReader(streamContatti);     //leggiamo il file riga per riga

            readerContatti.readLine();                      //salto la prima riga di intestazione

            while(true){
                String line=readerContatti.readLine();
                if(line==null)break;
                if(line.equals(""))break;
                String[] values = line.split(",");
                out.printf("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", values[0], values[1], values[2]);
            }


            readerContatti.close();                         //Devo chiudere sempre il file!!

            out.println("</tbody>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
        else if(format.equals("json")){
            response.setContentType("text/json");
            out.println("[");
            
            FileReader streamContatti = 
                    new FileReader("C:\\Remp\\contatti.csv");
            BufferedReader readerContatti=
                    new BufferedReader(streamContatti);     //leggiamo il file riga per riga

            readerContatti.readLine();                      //salto la prima riga di intestazione
            
            boolean first=true;
            while(true){
                String line=readerContatti.readLine();
                if(line==null)break;
                if(line.equals(""))break;
                String[] values = line.split(",");
                if (first==false){
                    out.printf(", ");
                }else{
                    first=false;
                }
                out.printf("{cognome:'%s', nome:'%s', email:'%s'}", values[0], values[1], values[2]);
            }


            readerContatti.close();                         //Devo chiudere sempre il file!!
            
            out.println("]");
        }
        
        processRequest(request, response);
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
