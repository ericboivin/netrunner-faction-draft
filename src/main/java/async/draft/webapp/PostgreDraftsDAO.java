package async.draft.webapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
 
public class PostgreDraftsDAO implements IDraftDAO
{
	private DataSource dataSource;
 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
 
	public void create(Draft draft){
 
		String sql = "INSERT INTO drafts " +
				"(code) VALUES (?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, draft.getCode());
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
 
	public Draft getDraftByID(int draftId){
 
		String sql = "SELECT * FROM drafts WHERE id = ?";
 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			Draft draft = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				draft = new Draft(
					rs.getInt("id"),
					rs.getString("code")
				);
			}
			rs.close();
			ps.close();
			return draft;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
