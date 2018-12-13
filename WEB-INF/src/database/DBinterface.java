package database;

import java.sql.*;

import java.util.Map;
import java.util.HashMap;

public class DBinterface {

  Connection conn = null;

  // Public constructor
  public DBinterface()
  {
    try
    {
      conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement statement = conn.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

//      statement.executeUpdate("CREATE TABLE IF NOT EXISTS Content (work_group string, work_text string)");
    }
    catch(SQLException e)
    {
      System.err.printf("Error in constructor: %s\n", e.getMessage());
    }
    finally
    {
      try
      {
        if(conn != null)
          conn.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.printf("Error in close: %s\n", e.getMessage());
      }
    }
  }

  // Public methods
  
  // TODO fix insert/get functions to allow for multiple content/work_texts per group
  public void insert(String work_group, String work_text)
  {
    try
    {
      // create a database connection
      conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement statement = conn.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate(String.format("INSERT INTO Content VALUES('%s', '%s')", work_text, work_group));
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.printf("Error in insert: %s\n", e.getMessage());
    }
  }
  
  // TODO add functinality for individual work_text_number
  public void update(String work_group, String work_text) {
    String sql = "UPDATE Content SET work_text = ? "
               + "WHERE work_group = ?";

    try (Connection connection = this.connect();
      PreparedStatement pstmt = connection.prepareStatement(sql)) {

      // set the corresponding param
      pstmt.setString(1, work_text);
      pstmt.setString(2, work_group);
      // update 
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.err.printf("Error in update: %s\n", e.getMessage());
    }
  }

  // TODO 
  public void delete(String group, int work_text_number)
  {

  }

  public Map<String, String> get(String group){
    String sql = "SELECT * FROM Content";
    Map<String,String> valmap = new HashMap<String,String>();
   
    try (Connection connection = this.connect();
      Statement stmt  = connection.createStatement();
      ResultSet rs    = stmt.executeQuery(sql)){
      
      // loop through the result set
      while (rs.next()) {
        valmap.put(rs.getString("work_group"),rs.getString("work_text"));
      }
    } catch (SQLException e) {
      System.err.printf("Error in get: %s\n", e.getMessage());
    }
   
    return valmap;
  }

  // Private methods
  
  private Connection connect() {
    // SQLite connection string
    String url = "jdbc:sqlite:test.db";
    conn = null;
    try {
      conn = DriverManager.getConnection(url);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }

  public static void main(String[] args)
  {
    DBinterface db = new DBinterface();

    // Does not yet allow multiple inserts. Builds and runs, but results in "Lorem ipsum..."
    db.insert("TG1", "Lorem ipsum...");
    db.insert("TG2", "Lorem ipsum...2");
    db.insert("TG3", "Lorem ipsum...3");
    Map<String, String> dbMap = db.get("TG1");

    System.out.printf("TG1: %s\n", dbMap.get("TG1"));
  }
}
