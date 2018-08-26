package prog3060.jwong;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="DisplayServlet", urlPatterns={"/DisplayServlet", ""})
public class DisplayServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;
    static final int MISSING_ARGUMENT_ERROR_CODE = 1;
    static final int UNHANDLED_EXCEPTION_ERROR_CODE = 2;

    static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/CanadaCensusDB;user=tfreitas;password=password";
    static final String CONNECTION_USER = "tfreitas";
    static final String CONNECTION_PASSWORD = "password";

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter tempOutputPrintWriter = response.getWriter())
        {
        	ArrayList<String> names = new ArrayList<String>();
        	names = getNames();
        	
            tempOutputPrintWriter.println("<html>");

            tempOutputPrintWriter.println("<head>");
            tempOutputPrintWriter.println("<title>Servlet Example</title>");
            tempOutputPrintWriter.println("</head>");

            tempOutputPrintWriter.println("<body>");
            tempOutputPrintWriter.println("<div>");
            tempOutputPrintWriter.println("<h1>Servlet Example</h1>");
            tempOutputPrintWriter.println("<p>Enter a message to be stored in the session variable.</p>");

            tempOutputPrintWriter.println("<br>");
            tempOutputPrintWriter.println("<br>");
            tempOutputPrintWriter.println("<form action='./WriteServlet' method='POST'>");
            tempOutputPrintWriter.println("<input type='text' name='message'>");
            tempOutputPrintWriter.println("<input type='submit' value='Enter'>");
            tempOutputPrintWriter.println("</form>");
            for (String name : names) {
            	tempOutputPrintWriter.println("<p>" + name + "</p>");
            }
            tempOutputPrintWriter.println("<br>");

            HttpSession tempSession = request.getSession();
            String tempRetrievedMessage = (String) tempSession.getAttribute("message");
            tempOutputPrintWriter.println("<p>The message: (" + tempRetrievedMessage + ") was retrieved from the session variable.</p>");

            tempOutputPrintWriter.println("<br>");
            tempOutputPrintWriter.println("<br>");

            tempOutputPrintWriter.println("<p>Served at: " + request.getContextPath() + "</p>");

            tempOutputPrintWriter.println("</div>");
            tempOutputPrintWriter.println("</body>");
            tempOutputPrintWriter.println("</html>");

        }

	}
	
	public ArrayList<String> getNames() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		
		// Loads Derby Driver
		try
        {
 
            Class.forName("org.apache.derby.jdbc.ClientDriver");
 
        }
        catch (ClassNotFoundException e)
        {
 
            e.printStackTrace();
 
            throw new ServletException(e);
 
        }
		
		try (java.sql.Connection tempConnection = DriverManager.getConnection(CONNECTION_STRING)) {
			tempConnection.setAutoCommit(false);
			Statement tempStatement = tempConnection.createStatement();
			
			String tempSQLSelectQuery = "SELECT * FROM APP.GEOGRAPHICAREA WHERE CODE = 1";
			ResultSet tempResultSet = tempStatement.executeQuery(tempSQLSelectQuery);
			
			while(tempResultSet.next()) {
				names.add(tempResultSet.getString("NAME"));
				System.out.println(tempResultSet.getString("NAME"));
			}			
			tempConnection.rollback();
			tempConnection.close();
			return names;
        }
        catch (SQLException e)
        {

            e.printStackTrace();

            System.exit(e.getErrorCode());

        }
        catch (Exception e)
        {

            e.printStackTrace();

            System.exit(UNHANDLED_EXCEPTION_ERROR_CODE);

        }
		return names;

	}
}



