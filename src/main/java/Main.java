import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Main!");
        if (!new File("../cleanedArticles.csv").exists()) {
            String outputPath = "../cleanedArticles.csv";
            String[] inputPaths = new String[]{"../articles1.csv", "../articles2.csv", "../articles3.csv"};
            RawRecordTransformer rawRecordTransformer = new RawRecordTransformer(outputPath, inputPaths, false);
        }
        try {
            Reader reader = Files.newBufferedReader(Paths.get("../cleanedArticles.csv"));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            int count = 0;
            for (NewsRecord aCsvToBean : csvToBean) {
                var record = aCsvToBean;
                if (count > 50)
                    break;
                if (record.getContent().length() == 0)
                    count++;
            }
            System.out.println(count);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
