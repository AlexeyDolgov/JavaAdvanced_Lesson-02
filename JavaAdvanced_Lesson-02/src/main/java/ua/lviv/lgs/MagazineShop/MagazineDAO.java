package ua.lviv.lgs.MagazineShop;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MagazineDAO {
	private Connection connection;
	private PreparedStatement preparedStatement;

	private String CREATE = "insert into magazine(`title`, `description`, `publish_date`, `subscription_price`) values (?, ?, ?, ?)";
	private String READ_ALL = "select * from magazine";
	private String READ_BY_ID = "select * from magazine where id = ?";
	private String UPDATE_BY_ID = "update magazine set title = ?, description = ?, publish_date = ?, subscription_price = ? where id = ?";
	private String DELETE_BY_ID = "delete from magazine where id = ?";

	public MagazineDAO(Connection connection) {
		this.connection = connection;
	}

	public void insert(Magazine magazine) throws SQLException {
		preparedStatement = connection.prepareStatement(CREATE);
		preparedStatement.setString(1, magazine.getTitle());
		preparedStatement.setString(2, magazine.getDescription());
		preparedStatement.setDate(3, Date.valueOf(magazine.getPublishDate()));
		preparedStatement.setDouble(4, magazine.getSubscriptionPrice());

		int rows = preparedStatement.executeUpdate();
		System.out.printf("Adding " + magazine + "... %d row added!\n", rows);
	}

	public List<Magazine> readAll() throws SQLException {
		preparedStatement = connection.prepareStatement(READ_ALL);

		ResultSet result = preparedStatement.executeQuery();

		List<Magazine> magazineList = new ArrayList<>();
		while (result.next()) {
			magazineList.add(MagazineMapper.map(result));
		}

		return magazineList;
	}

	public Magazine readByID(int id) throws SQLException {
		preparedStatement = connection.prepareStatement(READ_BY_ID);
		preparedStatement.setInt(1, id);

		ResultSet result = preparedStatement.executeQuery();
		result.next();

		return MagazineMapper.map(result);
	}

	public void updateByID(Magazine magazine) throws SQLException {
		preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
		preparedStatement.setString(1, magazine.getTitle());
		preparedStatement.setString(2, magazine.getDescription());
		preparedStatement.setDate(3, Date.valueOf(magazine.getPublishDate()));
		preparedStatement.setDouble(4, magazine.getSubscriptionPrice());
		preparedStatement.setInt(5, magazine.getId());

		int rows = preparedStatement.executeUpdate();
		System.out.printf("Updating " + magazine + "... %d row updated!\n", rows);
	}

	public void delete(int id) throws SQLException {
		preparedStatement = connection.prepareStatement(DELETE_BY_ID);
		preparedStatement.setInt(1, id);

		int rows = preparedStatement.executeUpdate();
		System.out.printf("Deleting magazine with ID#" + id + "... %d row deleted!\n", rows);
	}
}
