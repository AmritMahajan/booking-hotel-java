import java.sql.*;
import java.util.ArrayList;

public class trial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt = null;
		int total_amount=0;
		  try {
		         Class.forName("org.postgresql.Driver");
		         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
		         stmt = c.createStatement();
		         
		         
		         String sql =( "SELECT SUM(price) from public.\"Food\" where room_no = ?  ;" );
		         PreparedStatement pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, 1);
		         ResultSet rs = pstmt.executeQuery();
		         while ( rs.next() ) {
		             total_amount=rs.getInt(1);
		          }
		         System.out.println(total_amount);
		         stmt.close();
		         c.close();
		      }catch (Exception e) {
		         e.printStackTrace();
		      }
	}

}
