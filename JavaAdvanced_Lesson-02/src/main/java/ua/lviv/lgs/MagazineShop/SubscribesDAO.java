package ua.lviv.lgs.MagazineShop;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscribesDAO {
	private Connection connection;
	private PreparedStatement preparedStatement;

	private String CREATE = "insert into subscribes(`user_id`, `magazine_id`, `subscribe_status`, `subscribe_date`, `subscribe_period`) values (?, ?, ?, ?, ?)";
	private String READ_ALL = "select * from subscribes";
	private String READ_BY_ID = "select * from subscribes where id = ?";
	private String UPDATE_BY_ID = "update subscribes set user_id = ?, magazine_id = ?, subscribe_status = ?, subscribe_date = ?, subscribe_period = ? where id = ?";
	private String DELETE_BY_ID = "delete from subscribes where id = ?";

	public SubscribesDAO(Connection connection) {
		this.connection = connection;
	}

	public void insert(Subscribes subscribes) throws SQLException {
		preparedStatement = connection.prepareStatement(CREATE);
		preparedStatement.setInt(1, subscribes.getUserID());
		preparedStatement.setInt(2, subscribes.getMagazineID());
		preparedStatement.setBoolean(3, subscribes.getSubscribeStatus());
		preparedStatement.setDate(4, Date.valueOf(subscribes.getSubscribeDate()));
		preparedStatement.setInt(5, subscribes.getSubscribePeriod());

		int rows = preparedStatement.executeUpdate();
		System.out.printf("Adding " + subscribes + "... %d row added!\n", rows);
	}

	public List<Subscribes> readAll() throws SQLException {
		preparedStatement = connection.prepareStatement(READ_ALL);

		ResultSet result = preparedStatement.executeQuery();

		List<Subscribes> subscribesList = new ArrayList<>();
		while (result.next()) {
			subscribesList.add(SubscribesMapper.map(result));
		}

		return subscribesList;
	}

	public Subscribes readByID(int id) throws SQLException {
		preparedStatement = connection.prepareStatement(READ_BY_ID);
		preparedStatement.setInt(1, id);

		ResultSet result = preparedStatement.executeQuery();
		result.next();

		return SubscribesMapper.map(result);
	}

	public void updateByID(Subscribes subscribes) throws SQLException {
		preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
		preparedStatement.setInt(1, subscribes.getUserID());
		preparedStatement.setInt(2, subscribes.getMagazineID());
		preparedStatement.setBoolean(3, subscribes.getSubscribeStatus());
		preparedStatement.setDate(4, Date.valueOf(subscribes.getSubscribeDate()));
		preparedStatement.setInt(5, subscribes.getSubscribePeriod());
		preparedStatement.setInt(6, subscribes.getId());

		int rows = preparedStatement.executeUpdate();
		System.out.printf("Updating " + subscribes + "... %d row updated!\n", rows);
	}

	public void delete(int id) throws SQLException {
		preparedStatement = connection.prepareStatement(DELETE_BY_ID);
		preparedStatement.setInt(1, id);

		int rows = preparedStatement.executeUpdate();
		System.out.printf("Deleting subscribe with ID#" + id + "... %d row deleted!\n", rows);
	}
}
