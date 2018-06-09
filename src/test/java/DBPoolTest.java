import org.junit.Test;
import xiangxi.DBPool;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class DBPoolTest {
    @Test
    public void testGetConnection() {
        DBPool pool = new DBPool();
        pool.getConn();
    }
}