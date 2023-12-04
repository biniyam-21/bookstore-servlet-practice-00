package com.itsc;
import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
	
	private static final String query = "select id, bookname, bookedition, bookprice from books";


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        
      //get the book info
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
//        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
        
        //from chat gpt
        
        String bookPriceParam = req.getParameter("bookPrice");
        float bookPrice = 0.0f; // Default value or handle it accordingly

        if (bookPriceParam != null && !bookPriceParam.isEmpty()) {
            try {
                bookPrice = Float.parseFloat(bookPriceParam);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Handle the parsing exception if needed
            }
        }

        // load the jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }


//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "root", "root");
        // generate the connection s
        try {
        	Connection conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookregister", "root", "root");
        	PreparedStatement ps = conn.prepareStatement(query);
        	ResultSet rs = ps.executeQuery();
        	// ... (previous code)

        	// Add the following lines to include Bootstrap from CDN
        	pw.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
        	pw.println("<script src='https://code.jquery.com/jquery-3.5.1.slim.min.js'></script>");
        	pw.println("<script src='https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js'></script>");
        	pw.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script>");


        	pw.println("<body class ='container-fluid card' style='width: 40rem;'>");
        	pw.println("<table border = '1' class=\"table table-hover\">");
        	pw.println("<tr>");
        	pw.println("<th>Book Id</th>");
        	pw.println("<th>Book Name</th>");
        	pw.println("<th>Book Edition</th>");
        	pw.println("<th>Book Price</th>");
        	pw.println("<th>Edit</th>");
        	pw.println("<th>Delete</th>");
        	pw.println("</tr>");
        	while(rs.next()) {
        		pw.println("<tr>");
        		pw.println("<td>" + rs.getInt(1) + "</td>");
        		pw.println("<td>" + rs.getString(2) + "</td>");
        		pw.println("<td>" + rs.getString(3) + "</td>");
        		pw.println("<td>" + rs.getFloat(4) + "</td>");
        		pw.println("<td><a href ='editScreen?id=" + rs.getInt(1) +
        		"'>edit</a></td>");
        		pw.println("<td><a href ='deleteurl?id=" + rs.getInt(1) +
        		"'>delete</a></td>");
        		pw.println("</tr>");
        	}
        	pw.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
        	e.printStackTrace();
        	pw.println("<h1>" + e.getMessage() + "</h1>");
        	}
        	pw.println("<a href='home.html' class=\"btn btn-success\">Home</a>");
        	pw.println("</body>");
        
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }
}
