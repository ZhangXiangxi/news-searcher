import com.opencsv.bean.CsvToBeanBuilder;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import org.junit.Test;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class MainTest {
    @Test
    public void testTest(){
        System.out.println("Test!");
    }
    @Test
    public void testJava10() {
        ArrayList<String> testString = new ArrayList<>();
        testString.add("This");
        testString.add("is");
        testString.add("a");
        testString.add("test");
        testString.add("string");
        for(var s : testString) {
            System.out.println(s);
        }
        var iter = testString.iterator();
        System.out.println(iter.next());
    }
    @Test
    public void testUniquenessOfId() {
        IntAVLTreeSet idSet = new IntAVLTreeSet();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("../cleanedArticles.csv"));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            int count = 0;
            for (NewsRecord record : csvToBean) {
                idSet.add(record.getId());
                count++;
            }
            assertEquals(count, idSet.size());
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    @Test
    public void testMaxContentAndTitleSize() {
        int titleMax = 0;
        int contentMax = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get("../cleanedArticles.csv"));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();

            for (NewsRecord record : csvToBean) {
                int titleSize = record.getTitle().length();
                int contentSize = record.getContent().length();
                titleMax = titleSize > titleMax ? titleSize : titleMax;
                contentMax = contentSize > contentMax ? contentSize : contentMax;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(titleMax);
        System.out.println(contentMax);
    }
}