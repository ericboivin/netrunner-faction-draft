package async.draft.webapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
 
@Component
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
			ps.setInt(1, draftId);
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
	
	@Cacheable("draft")
	public Draft getDraftByCode(String draftCode){
		 
		String sql = "SELECT * FROM drafts WHERE code = ?";
 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, draftCode);
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
	
	@Cacheable("pick")
	public DraftPick getPickByToken(String token){
		 
		String sql = "SELECT * FROM pick WHERE token = ?";
 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, token);
			DraftPick pick = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				pick = new DraftPick();
				pick.setDraftCode(rs.getString("draftcode"));
				pick.setPlayerName(rs.getString("playername"));
				pick.setSide(rs.getString("side"));
				pick.setPick(rs.getString("pick"));
				pick.setNumber(rs.getInt("picknumber"));
				pick.setToken(token);
			}
			rs.close();
			ps.close();
			return pick;
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
	
	public void saveNewPick(DraftPick pick){
		String sql = "INSERT INTO pick " +
				"(draftcode,playername,side,token,picknumber) VALUES (?,?,?,?,?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, pick.getDraftCode());
			ps.setString(2, pick.getPlayerName());
			ps.setString(3, pick.getSide());
			ps.setString(4, pick.getToken());
			ps.setInt(5, pick.getNumber());
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
	
	public List<DraftPick> getPicksForDraft(String draftCode){
		String sql = "SELECT * FROM pick WHERE draftcode = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, draftCode);
			DraftPick pick;
			List<DraftPick> picks = new ArrayList<DraftPick>();
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				pick = new DraftPick();
				pick.setDraftCode(draftCode);
				pick.setPlayerName(rs.getString("playername"));
				pick.setSide(rs.getString("side"));
				pick.setPick(rs.getString("pick"));
				pick.setNumber(rs.getInt("picknumber"));
				pick.setToken(rs.getString("token"));
				picks.add(pick);
			}
			rs.close();
			ps.close();
			return picks;
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
