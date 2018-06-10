package xiangxi.invertedIndex;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class InvertedIndexDAOTest {
    InvertedIndexDAO dao;
    @Before
    public void setUp() {
        dao = new InvertedIndexDAO();
    }
    @Test
    public void testInsert() {
        InvertedIndexEntry one = new InvertedIndexEntry(3,5,7, true);
        InvertedIndexEntry another = new InvertedIndexEntry(3,9,4, false);
        dao.insert(one);
        dao.insert(another);
    }
    @Test
    public void testSearch() {
        var entries = dao.search(3);
        for(var entry : entries) {
            System.out.println(entry.wordID);
            System.out.println(entry.newsID);
            System.out.println(entry.frequency);
            System.out.println(entry.isTitle);
        }
    }
}