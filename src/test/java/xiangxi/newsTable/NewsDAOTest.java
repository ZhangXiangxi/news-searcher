package xiangxi.newsTable;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsDAOTest {
    @Test
    public void testRead() {
        NewsDAO dao = new NewsDAO();
        var entry = dao.searchMetaByID(42632);
        assertNotNull(entry);
        System.out.println(entry.getId());
        System.out.println(entry.getStartContentPosition());
        System.out.println(entry.getEndContentPosition());
    }
}