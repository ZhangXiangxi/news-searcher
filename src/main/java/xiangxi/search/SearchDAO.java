package xiangxi.search;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import xiangxi.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class SearchDAO {
    Connection connection;
    double ratio;
    public SearchDAO(double ratio) {
        this.ratio = ratio;
        connection = new DBPool().getConn();
    }
    public IntArrayList searchForWords(ArrayList<String> words) {
        HashMap<Integer, Double> scores = new HashMap<>();
        try {
            String sql = "SELECT * FROM inverted_indexes WHERE word_id = ? LIMIT 50;";
            PreparedStatement ps = connection.prepareStatement(sql);
            for(String word : words) {
                ps.setString(1, word);
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()) {
                    int id = resultSet.getInt("news_id");
                    boolean isTitle = resultSet.getBoolean("is_title");
                    double ratio = isTitle ? this.ratio : 1.0;
                    int freq = resultSet.getInt("freq");
                    if (!scores.containsKey(id))
                        scores.put(id, freq * ratio);
                    else {
                        double score = scores.get(id);
                        scores.put(id, score + freq * ratio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<IntScore> result = new ArrayList<>();
        for(var entry : scores.entrySet()) {
            result.add(new IntScore(entry.getKey(), entry.getValue()));
        }
        Collections.sort(result);
        for(var entry : result) {
            System.out.println(entry.id);
            System.out.println(entry.score);
        }
        IntArrayList resultArray = new IntArrayList();
        int count = 0;
        for(var entry : result) {
            count++;
            if (count > 20)
                break;
            resultArray.add(entry.id);
        }
        return resultArray;
    }
    public class IntScore implements Comparable {
        int id;
        double score;

        public IntScore(int id, double score) {
            this.id = id;
            this.score = score;
        }

        @Override
        public int compareTo(Object o) {
            if (o.getClass() != IntScore.class)
                return 0;
            IntScore another = (IntScore) o;
            if (score - another.score > 0)
                return -1;
            if (score - another.score < 0)
                return +1;
            return 0;
        }
    }
}
