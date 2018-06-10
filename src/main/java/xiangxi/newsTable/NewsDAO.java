package xiangxi.newsTable;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import xiangxi.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsDAO {
    public Connection connection;
    public StringBuilder builder;
    public NewsDAO() {
        connection = new DBPool().getConn();
        builder = new StringBuilder();
    }
    public void insertRecord(NewsMetadata metadata) {
        try {
            String sql = "INSERT INTO news_table VALUE (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, metadata.getId());
            preparedStatement.setString(2, metadata.getTitle());
            preparedStatement.setString(3, metadata.getPublication());
            preparedStatement.setString(4, metadata.getAuthor());
            preparedStatement.setInt(5, metadata.getYear());
            preparedStatement.setInt(6, metadata.getMonth());
            preparedStatement.setLong(7, metadata.getStartContentPosition());
            preparedStatement.setLong(8, metadata.getEndContentPosition());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (MysqlDataTruncation e) {
            System.out.println(metadata.getAuthor());
            System.out.println(metadata.getAuthor().length());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public NewsMetadata searchMetaByID(int id) {
        try {
            String sql = "SELECT * FROM news_table WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return new NewsMetadata(id, resultSet.getString("title"),
                    resultSet.getString("publication"), resultSet.getString("author"),
                    resultSet.getInt("year"), resultSet.getInt("month"),
                    resultSet.getLong("startContentPosition"),
                    resultSet.getLong("endContentPosition"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
