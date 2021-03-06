package xiangxi.wordTable;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import xiangxi.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
// insert and search a word with MySQL
public class WordDAO {
    Connection connection;
    StringBuilder builder;
    public WordDAO() {
        connection = new DBPool().getConn();
        builder = new StringBuilder();
    }
    public void insertWord(int id, String word) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSqlBuilder(word, id));
            preparedStatement.executeUpdate();
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(id + " " + word);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String searchWordWithID(int id) {
        String sqlString = searchWordByIdSqlBuilder(id);
        String result = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean hasNext = resultSet.next();
            assert(hasNext);
            result =  resultSet.getString("word");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert(!result.equals(""));
        return result;
    }

    public Trie<String, Integer> getFirstWords(int count) {
        Trie<String, Integer> result = new PatriciaTrie<>();
        try {
            String sql = "SELECT * FROM word_table WHERE word_id <= ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, count);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.put(resultSet.getString("word"), resultSet.getInt("word_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int searchIDWithWord(String word) {
        // the word should be pure-alphabetic and in lower case
        int result = -1;
        try {
            String sqlString = "SELECT * FROM new_schema.word_table WHERE word = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                result =  resultSet.getInt("word_id");
            else
                System.out.println(word);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert(result != -1);
        return result;
    }

    public String insertSqlBuilder(String word, int id) {
        builder.setLength(0);
        builder.append("INSERT INTO word_table VALUE (");
        builder.append(id);
        builder.append(", \"");
        builder.append(word);
        builder.append("\");");
        return builder.toString();
    }
    public String searchWordByIdSqlBuilder(int id) {
        builder.setLength(0);
        builder.append("SELECT * FROM word_table WHERE word_id = ");
        builder.append(id);
        builder.append(";");
        return builder.toString();
    }
    public String searchIDWithWordBuilder(String word) {
        builder.setLength(0);
        builder.append("SELECT * FROM word_table WHERE word = \"");
        builder.append(word);
        builder.append("\";");
        return builder.toString();
    }
}
