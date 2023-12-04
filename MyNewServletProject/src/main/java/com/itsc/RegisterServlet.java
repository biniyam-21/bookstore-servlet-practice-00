package com.itsc;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String query = "insert into books(bookname, bookedition, bookprice) values(?, ?, ?)";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");

        String bookPriceParam = req.getParameter("bookPrice");
        float bookPrice = 0.0f; // Default value or handle it accordingly

        if (bookName != null && !bookName.isEmpty()) {
            try {
                if (bookPriceParam != null && !bookPriceParam.isEmpty()) {
                    bookPrice = Float.parseFloat(bookPriceParam);
                }

                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookregister", "root", "root");
                     PreparedStatement ps = conn.prepareStatement(query)) {

                    ps.setString(1, bookName);
                    ps.setString(2, bookEdition);
                    ps.setFloat(3, bookPrice);

                    int count = ps.executeUpdate();

                    if (count == 1) {
                        pw.println("<h2> Book registered successfully.</h2");
                    } else {
                        pw.println("<h2> Book Not registered successfully.</h2");
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                    pw.println("<h1>" + se.getMessage() + "</h1>");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                pw.println("<h1>Invalid book price format.</h1>");
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            pw.println("<h1>Book name cannot be null or empty.</h1>");
        }
        pw.print("<br>");
        pw.println("<a href='home.html'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='booklist'>Book List</a>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }
}
