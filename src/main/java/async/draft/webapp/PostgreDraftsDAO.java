package async.draft.webapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.postgresql.util.PGobject;

public class PostgreDraftsDAO implements IDraftDAO
{
	private DataSource dataSource;
 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void saveDraft(Draft draft){
		String sql = "INSERT INTO draftjson " +
				"(code,json) VALUES (?,CAST(? AS json))";
		Connection conn = null;

		try {
			ObjectWriter ow = new ObjectMapper().writer();
			String json = ow.writeValueAsString(draft);
			
			PGobject jsonObject = new PGobject();
			jsonObject.setType("json");
			jsonObject.setValue(json);
			
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, draft.getCode());
			ps.setObject(2, jsonObject);
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public void updateDraft(Draft draft){
		String sql = "UPDATE draftjson SET json=CAST(? AS json)" +
				" WHERE code = ?";
		Connection conn = null;

		try {
			ObjectWriter ow = new ObjectMapper().writer();
			String json = ow.writeValueAsString(draft);
			
			PGobject jsonObject = new PGobject();
			jsonObject.setType("json");
			jsonObject.setValue(json);
			
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, jsonObject);
			ps.setString(2, draft.getCode());
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public Draft getDraft(String code){
		String sql = "SELECT * FROM draftjson WHERE code = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			
			Draft draft = new Draft();
			String json = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				 json = rs.getString("json");
			}
			rs.close();
			ps.close();
			
			ObjectMapper mapper = new ObjectMapper();
			draft = mapper.readValue(json, Draft.class);
			
			return draft;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
		return null;
	}
}
