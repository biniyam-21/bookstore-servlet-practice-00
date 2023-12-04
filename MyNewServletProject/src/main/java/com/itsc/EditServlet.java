package com.itsc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    private static final String query = "update books set bookname=?, bookedition=?, bookprice=? where id = ?";

   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // get PrintWriter
        PrintWriter pw = resp.getWriter();
        // set content type
        resp.setContentType("text/html");

        // get the id of the record
        int id = Integer.parseInt(req.getParameter("id"));

        // get the edited data
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");

        // Handle null or empty string for bookPrice
        String bookPriceParam = req.getParameter("bookPrice");
        float bookPrice = 0.0f; // Default value or handle it accordingly

        if (bookPriceParam != null && !bookPriceParam.isEmpty()) {
            try {
                bookPrice = Float.parseFloat(bookPriceParam);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Handle the parsing exception if needed
            }
        }

        // load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // generate the connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql:///bookregister", "root", "root");

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            ps.setInt(4, id);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record is edited successfully.</h2>");
            } else {
                pw.println("<h2>Record not edited.</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
        pw.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
    	pw.println("<script src='https://code.jquery.com/jquery-3.5.1.slim.min.js'></script>");
    	pw.println("<script src='https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js'></script>");
    	pw.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script>");
        pw.println("<a href='home.html' class='btn btn-success'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='booklist' class=\"btn btn-primary\">Book List</a>");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
