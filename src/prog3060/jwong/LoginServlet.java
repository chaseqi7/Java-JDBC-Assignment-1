package prog3060.jwong;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		boolean auth = false;
		try {
			auth = TestConnection(request.getParameter("username"), request.getParameter("password"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (auth) {
			response.sendRedirect(request.getContextPath() + "/GeoAreaList.jsp");
			session.setAttribute("error", "");
		} else {
			response.sendRedirect(request.getContextPath() + "/Login.jsp");
	        session.setAttribute("error", "Invalid Database Credentials");
		}
		
	}
	
	public static boolean TestConnection(String username, String password) throws SQLException
	{
		// Loads Derby Driver
		try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        }
        catch (ClassNotFoundException e)
        {
 
            e.printStackTrace();
 
            throw new SQLException(e);
 
        }
	    Properties tempConnectionProperties = new Properties();
	    tempConnectionProperties.put("user", username);
	    tempConnectionProperties.put("password", password);
	    String CONNECTION_STRING = "jdbc:derby://localhost:1527/CanadaCensusDB;user=" + username + ";" + "password="+ password + "";
	    
	    try(Connection tempConnection = DriverManager.getConnection(CONNECTION_STRING, tempConnectionProperties))
	    {
	    	tempConnection.rollback();
			tempConnection.close();
	    	return true;
	    	
	    } catch (SQLException e)
        {
	    	e.getErrorCode();
	    	e.printStackTrace();
	    	return false;
        }
	}

}
