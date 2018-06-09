package xiangxi.newsTable;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsTable {
    public NewsDAO newsDAO;
    public NewsTable() {
        newsDAO = new NewsDAO();
    }
    public void writeNewsFromFile(String path) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            for (var record : csvToBean) {
                writeNews(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeNews(NewsRecord record) {
        newsDAO.insertRecord(record);
    }
}
