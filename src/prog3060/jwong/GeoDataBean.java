package prog3060.jwong;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Properties;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.*;

@WebServlet(name = "GeoAreaList", urlPatterns = {"/GeoAreaList"})
public class GeoDataBean extends HttpServlet {
	static final int MISSING_ARGUMENT_ERROR_CODE = 1;
    static final int UNHANDLED_EXCEPTION_ERROR_CODE = 2;

    static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/CanadaCensusDB;user=tfreitas;password=password";
    static final String CONNECTION_USER = "tfreitas";	
    static final String CONNECTION_PASSWORD = "password";     


	private static Connection OpenConnection() throws SQLException
	{
	    Properties tempConnectionProperties = new Properties();
	    tempConnectionProperties.put("user", CONNECTION_USER);
	    tempConnectionProperties.put("password", CONNECTION_PASSWORD);
	
	    Connection tempConnection = DriverManager.getConnection(CONNECTION_STRING, tempConnectionProperties);
	
	    tempConnection.setAutoCommit(false);
	    tempConnection.createStatement().executeUpdate("SET SCHEMA APP");
	
	    return tempConnection;
	
	}
	
	private static PreparedStatement GetSQLSelectPreparedStatement(Connection tempConnection) throws SQLException
	{
	
	    String tempSQLSelectQuery = "SELECT name" +
	            "FROM GEOGRAPHICAREA ";
	
	    PreparedStatement tempPreparedStatement = tempConnection.prepareStatement(tempSQLSelectQuery);
	
	    //tempPreparedStatement.setString(1, tempTeamID);
	
	    return tempPreparedStatement;
	
	}
	public ArrayList<String> getExecuteQueryB() throws ServletException {
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
			
			String tempSQLSelectQuery = "SELECT DISTINCT * FROM APP.GEOGRAPHICAREA JOIN APP.AGE ON APP.GEOGRAPHICAREA.GEOGRAPHICAREAID = APP.AGE.GEOGRAPHICAREA WHERE APP.AGE.CENSUSYEAR = 1 AND APP.AGE.AGEGROUP = 1";
			ResultSet tempResultSet = tempStatement.executeQuery(tempSQLSelectQuery);
			while(tempResultSet.next()) {
				
				names.add("Name: "+tempResultSet.getString("NAME"));
				names.add("Code: "+tempResultSet.getString("CODE"));
				names.add("Level: "+tempResultSet.getString("LEVEL"));
				names.add("Population: "+tempResultSet.getString("COMBINED"));
				try (java.sql.Connection tempChildrenConnection = DriverManager.getConnection(CONNECTION_STRING)) {
					tempChildrenConnection.setAutoCommit(false);
					Statement tempChildrenStatement = tempChildrenConnection.createStatement();
					String tempSQLSelectChildrenQuery = "";
					ArrayList<String> children= new ArrayList<String>();
					if(Integer.parseInt(tempResultSet.getString("LEVEL"))==0 ) {
						tempSQLSelectChildrenQuery="SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 1";
					}
					else if(Integer.parseInt(tempResultSet.getString("LEVEL"))==1) {
						tempSQLSelectChildrenQuery="SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 2 AND SUBSTR(CAST(APP.GEOGRAPHICAREA.ALTERNATIVECODE AS CHAR(10)), 1, 2) = '"+Integer.parseInt(tempResultSet.getString("ALTERNATIVECODE"))+"'";
					}
					else if(Integer.parseInt(tempResultSet.getString("LEVEL"))==2) {
						tempSQLSelectChildrenQuery="SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 3 AND SUBSTR(CAST(APP.GEOGRAPHICAREA.ALTERNATIVECODE AS CHAR(10)), 1, 5) = '"+Integer.parseInt(tempResultSet.getString("ALTERNATIVECODE"))+"'";
					}
					else{
						tempSQLSelectChildrenQuery="SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 3";
					}
					ResultSet tempChildrenSet = tempChildrenStatement.executeQuery(tempSQLSelectChildrenQuery);
					
					while(tempChildrenSet.next()) {
						children.add(tempChildrenSet.getString("NAME"));
					}
					names.add("Children: "+String.join(", ", children));
					tempChildrenConnection.rollback();
					tempChildrenConnection.close();
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

			}
			tempConnection.rollback();
			tempConnection.close();

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
	
	public ArrayList<String> getExecuteQueryC6() throws ServletException {
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
			
			String tempSQLSelectQuery = "SELECT * FROM APP.AGE JOIN APP.AGEGROUP ON APP.AGE.AGEGROUP = APP.AGEGROUP.AGEGROUPID WHERE APP.AGE.CENSUSYEAR = 1 AND APP.AGEGROUP.AGEGROUPID IN(3,9,15,22,28,34,40,46,52,58,64,70,76,83,89,95,101,108,114,120,126) AND APP.AGE.GEOGRAPHICAREA = 1";
			ResultSet tempResultSet = tempStatement.executeQuery(tempSQLSelectQuery);
			
			while(tempResultSet.next()) {
				names.add("Age Range: "+tempResultSet.getString("DESCRIPTION"));
				names.add("Male: "+tempResultSet.getString("MALE"));
				names.add("Female: "+tempResultSet.getString("FEMALE"));
			}			
			tempConnection.rollback();
			tempConnection.close();
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
	
	
	public ArrayList<String> getExecuteQueryC1() throws ServletException {
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
			
			String tempSQLSelectQuery = "SELECT * FROM APP.AGE JOIN APP.AGEGROUP ON APP.AGE.AGEGROUP = APP.AGEGROUP.AGEGROUPID WHERE APP.AGE.CENSUSYEAR = 2 AND APP.AGEGROUP.AGEGROUPID IN(3,9,15,22,28,34,40,46,52,58,64,70,76,83,89,95,101,108,114,120,126) AND APP.AGE.GEOGRAPHICAREA = 1";
			ResultSet tempResultSet = tempStatement.executeQuery(tempSQLSelectQuery);
			
			while(tempResultSet.next()) {
				names.add("Age Range: "+tempResultSet.getString("DESCRIPTION"));
				names.add("Male: "+tempResultSet.getString("MALE"));
				names.add("Female: "+tempResultSet.getString("FEMALE"));
			}			
			tempConnection.rollback();
			tempConnection.close();
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
	
	public ArrayList<String> getexecuteExistingQuery(String query) throws ServletException {
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
			
			String tempSQLSelectQuery = query;
			ResultSet tempResultSet = tempStatement.executeQuery(tempSQLSelectQuery);
			
			while(tempResultSet.next()) {
				names.add(tempResultSet.getString("NAME"));
			}			
			tempConnection.rollback();
			tempConnection.close();
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
	
	public ArrayList<String> getA0Names() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		names = getexecuteExistingQuery("SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 0");
		return names;
	}
	public ArrayList<String> getA1Names() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		names = getexecuteExistingQuery("SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 1");
		return names;
	}
	public ArrayList<String> getA2Names() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		names = getexecuteExistingQuery("SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 2");
		return names;
	}
	public ArrayList<String> getA3Names() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		names = getexecuteExistingQuery("SELECT * FROM APP.GEOGRAPHICAREA WHERE LEVEL = 3");
		return names;
	}
	public ArrayList<String> getA1Pop() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		names = getExecuteQueryB();
		return names;
	}
	public ArrayList<String> getBChildren() throws ServletException{
		ArrayList<String> names = new ArrayList<String>();
		names = getExecuteQueryB();
		return names;
	}

	public ArrayList<String> getAge2016() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		names = getExecuteQueryC6();
		return names;
	}


	public ArrayList<String> getAge2011() throws ServletException {
		ArrayList<String> names = new ArrayList<String>();
		names = getExecuteQueryC1();
		return names;
	}

	
}