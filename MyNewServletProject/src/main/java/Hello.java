import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/servlet")
public class Hello extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	
    	PrintWriter pw = res.getWriter();
    	String username = req.getParameter("username");
    	int age =Integer.parseInt(req.getParameter("age"));
    	pw.printf("%s is %d years old", username, age);
//        pw.println("Hello World");
    }
}
