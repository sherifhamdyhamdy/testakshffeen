/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sget.akshef.maps.AddressConverter;
import com.sget.akshef.maps.GoogleResponse;
import com.sget.akshef.maps.Result;

/**
 *
 * @author Vip
 */
public class LocationIndicator extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LocationIndicator</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LocationIndicator at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
            System.out.println("servet is here");
          String lng=  request.getParameter("lng");
          String lat=  request.getParameter("lat");
         Enumeration enum1= request.getParameterNames();
         while(enum1.hasMoreElements())
         {
            String param= enum1.nextElement().toString();
           String name= request.getParameter(param);
                System.out.println("param=== "+param);
           System.out.println("name=== "+name);
           
         }
           System.out.println("lat=== "+lat);
           System.out.println("lng=== "+lng);
           
           
            GoogleResponse res1 = new AddressConverter().convertFromLatLong(lat+","+lng);//30.006363999999998 31.202961499999997
  if(res1.getStatus().equals("OK"))
  {
   for(Result result : res1.getResults())
   {
    System.out.println("address is :"  +result.getFormatted_address());
   }
  }
  else
  {
   System.out.println(res1.getStatus());
  }
   //request.getServletContext().getRequestDispatcher("/aksf_units.xhtml").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-speresponsecific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    

    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
         System.out.println("servet is here");
          String lng=  request.getParameter("lng");
          String lat=  request.getParameter("lat");
         Enumeration enum1= request.getParameterNames();
         while(enum1.hasMoreElements())
         {
            String param= enum1.nextElement().toString();
           String name= request.getParameter(param);
                System.out.println("param=== "+param);
           System.out.println("name=== "+name);
           
         }
           System.out.println("lat=== "+lat);
           System.out.println("lng=== "+lng);
           
           
           
           //get Adress
           
           GoogleResponse res1 = new AddressConverter().convertFromLatLong("30.14384,31.23494");//30.006363999999998 31.202961499999997
  if(res1.getStatus().equals("OK"))
  {
   for(Result result : res1.getResults())
   {
    System.out.println("address is :"  +result.getFormatted_address());
   }
  }
  else
  {
   System.out.println(res1.getStatus());
  }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
