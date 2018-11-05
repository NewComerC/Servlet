/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mbs;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author spring
 */
public class Servlet1 extends HttpServlet {

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

        //set session
        HttpSession session = request.getSession(true);
        myBean ub = new myBean();
        session.setAttribute("ub", ub);
        List<Map> list = new ArrayList<Map>();
        Statement st;
        String status = null;
        //failure returns to index.jsp, success spawns cw
        //the userbean is used to transfer information
        if (request.getParameter("log") != null) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                //set connection
                String connectionURL = "jdbc:derby://localhost:1527/JiaminCheng";
                Connection conn = DriverManager.getConnection(connectionURL, "IS2560", "IS2560");
                st = conn.createStatement();

                String userName = request.getParameter("name"), pwd = null;
                int userId = -1;
                System.out.println(userName);
                //select the information of the user
                String q1 = new String("SELECT * FROM DEMO where NAME='" + request.getParameter("name") + "'");
                ResultSet rs = st.executeQuery(q1);

                //if the user exists
                if (rs.next()) {
                    pwd = rs.getString("PWD");
                    userId = rs.getInt("id");

                    //generate the log id
                    Random rand = new Random();
                    int id = rand.nextInt(3000) + 500000;
                    
                    //generate current date in sql format
                    java.util.Date date = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

//                    System.out.print(pwd);
                    // if the password is right
                    if (request.getParameter("password").equals(pwd)) {
                        status = "LOGIN SUCCESS";

                        //insert the new log record
                        String q3 = new String("insert into LOGINFO\n" + "values ("
                                + id + ","
                                + userId + ",'"
                                + sqlDate + "','"
                                + status + "')");
                        st.execute(q3);

                        //select the top 5 log records of the current user 
                        String q2 = new String("SELECT l.id as OpeId,d.ID as id,d.NAME as name,l.status as status,l.TIME as time FROM\n"
                                + "DEMO as d,LOGINFO as l\n"
                                + "where d.ID=l.USERID and d.ID="
                                + userId
                                + "\norder by l.Time desc");

                        ResultSet rs2 = st.executeQuery(q2);
                        while (rs2.next()) {
                            //set the log info to be shown in the next page
                            Map map = new HashMap<>();
                            map.put("id", String.valueOf(rs2.getInt("id")));
                            map.put("name", rs2.getString("name"));
                            map.put("time", rs2.getDate("time").toString());
                            map.put("status",rs2.getString("status"));
                            map.put("opeId", String.valueOf(rs2.getInt("OpeId")));
                            System.out.println(map);
                            list.add(map);
                        }

//                        System.out.print(list);
                        //set session information
                        ub.setStatus(status);
                        ub.setName(userName);
                        ub.setId(userId);

                        session.setAttribute("list", list);
                        //get to the next page 
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/JSP2.jsp");
                        rd.forward(request, response);
                    } else {
                        //insert the error log
                        status = "PASSWORD ERROR";
                        ub.setStatus(status);
                        String q3 = new String("insert into LOGINFO\n" + "values ("
                                + id + ","
                                + userId + ",'"
                                + sqlDate + "','"
                                + status + "')");
                        st.execute(q3);

                        //get to the login failure page
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/JSP0.jsp");
                        rd.forward(request, response);
                    }
                //the user does not exist in the record
                } else {
                    
                    ub.setStatus("USER NOT FOUND");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/JSP0.jsp");
                    rd.forward(request, response);
                }

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, null, ex);
            }
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
