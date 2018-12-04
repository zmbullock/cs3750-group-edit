package net.sqlitetutorial;
 
import java.sql.*;

public class DBinterface {
 
 //change to new connection string
 
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    /**
     * Update data of a warehouse specified by the id
     *
     * @param id
     * @param name name of the warehouse
     * @param capacity capacity of the warehouse
     */
     
    public void update(String group, String content) {
        String sql = "UPDATE Content SET work_text = ? "
                + "WHERE work_group = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, content);
            pstmt.setString(2, group);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public Map get(String group){
        String sql = "SELECT * FROM Content";
        Map<String,String> valmap = new HashMap<String,String>();
     
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                valmap.put(rs.getString("work_group"),rs.getString("work_text"));
            }
            return valmap;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     
    }
 
}
