package xiangxi.invertedIndex;

import com.mysql.jdbc.MysqlDataTruncation;
import xiangxi.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class InvertedIndexDAO {
    public Connection connection;
    public InvertedIndexDAO() {
        connection = new DBPool().getConn();
    }
    public void insert(InvertedIndexEntry entry) {
        try {
            String sql = "INSERT INTO inverted_indexes VALUE (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entry.getWordID());
            preparedStatement.setInt(2, entry.getNewsID());
            preparedStatement.setInt(3, entry.getFrequency());
            preparedStatement.setBoolean(4, entry.isTitle());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<InvertedIndexEntry> search(int wordID) {
        ArrayList<InvertedIndexEntry> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM inverted_indexes WHERE word_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, wordID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new InvertedIndexEntry(resultSet.getInt("word_id"),
                        resultSet.getInt("news_id"), resultSet.getInt("freq"),
                        resultSet.getBoolean("is_title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
