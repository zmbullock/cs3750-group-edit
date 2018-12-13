import java.sql.*;

public class DatabaseCreator
{
  private static void create()
  {
    Connection conn = null;
    try
    {
      conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement statement = conn.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("CREATE TABLE IF NOT EXISTS Content (work_text string, work_group string)");
      
      String work_group = "";
      String work_text = "";
      for(char i = 'A'; i <= 'D'; i++)
      {
        for(int j = 1; j <= 4; j++)
        {
          work_group = String.format("%c%d", i, j);
          statement.executeUpdate(String.format("INSERT INTO Content VALUES('%s', '%s')", work_text, work_group));
        }
      }
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

  public static void main(String[] args)
  {
    create();    
  }
}
