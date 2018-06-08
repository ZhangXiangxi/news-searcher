import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

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
        try {
            Reader reader = Files.newBufferedReader(Paths.get("articles1.csv"));
            CsvToBean<NewsRecordRaw> csvToBean = new CsvToBeanBuilder<NewsRecordRaw>(reader).withType(NewsRecordRaw.class).build();
            Iterator<NewsRecordRaw> newsRecordIterator = csvToBean.iterator();
            int count = 0;
            while(newsRecordIterator.hasNext()) {
                if (count++>5)
                    break;
                NewsRecordRaw record = newsRecordIterator.next();
                System.out.println(record.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
