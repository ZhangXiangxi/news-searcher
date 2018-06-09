package xiangxi.newsTable;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import xiangxi.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public void insertRecord(NewsRecord record) {
        try {
            String content = record.getContent();
            int contentLength = content.length();
            int pieceNumber;
            if (contentLength % 4096 == 0)
                pieceNumber = contentLength / 4096;
            else
                pieceNumber = contentLength / 4096 + 1;
            for(int i = 0; i < pieceNumber; i++) {
                int endIndex = i * 4096 + 4096;
                endIndex = endIndex > contentLength ? contentLength : endIndex;
                String piece = content.substring(i * 4096, endIndex);
                String sql = "INSERT INTO news_table VALUE (?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, record.getId());
                preparedStatement.setString(2, record.getTitle());
                preparedStatement.setString(3, record.getPublication());
                preparedStatement.setString(4, record.getAuthor());
                preparedStatement.setInt(5, record.getYear());
                preparedStatement.setInt(6, record.getMonth());
                preparedStatement.setString(7, piece);
                preparedStatement.setInt(8, i);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (MysqlDataTruncation e) {
            System.out.println(record.getAuthor());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
