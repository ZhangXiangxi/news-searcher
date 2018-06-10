package xiangxi.newsTable;

import com.opencsv.bean.CsvToBeanBuilder;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.junit.Test;
import xiangxi.Main;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsTableTest {
    @Test
    public void generateContentLengthDistribution() {
        IntArrayList lengths = new IntArrayList(150000);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(Main.CLEAN_ARTICLES_PATH));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            for (var record : csvToBean) {
                lengths.add(record.getContent().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(lengths);
        int count = 0;
        for(int i = lengths.size()-1; i>=0; i--) {
            System.out.println(lengths.getInt(i));
            if (count++>50)
                break;
        }
        count = 0;
        for(int i = 0; i < lengths.size(); i++) {
            int length =lengths.getInt(i);
            System.out.println(length);
            if (length > 50)
                break;
        }
    }
    @Test
    public void testReadFile() {
        NewsTable newsTable = new NewsTable(Main.NEWS_CONTENT_PATH);
        System.out.println(newsTable.readFileBetweenPosition(638394079, 638394080));
    }
}