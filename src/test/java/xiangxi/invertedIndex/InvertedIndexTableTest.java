package xiangxi.invertedIndex;

import org.junit.Test;
import xiangxi.Main;

import java.util.ArrayList;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class InvertedIndexTableTest {
    @Test
    public void testBlock() throws Exception{
        BlockingQueue<EntryPack> queue = new ArrayBlockingQueue<>(20);
        InvertedIndexTable invertedIndexTable = new InvertedIndexTable(Main.CLEAN_ARTICLES_PATH, queue);
        ArrayList<IndexInserterThread> consumers = new ArrayList<>();
        for(int i = 0; i < 40; i++) {
            consumers.add(new IndexInserterThread(queue));
        }
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(invertedIndexTable);
        for(var thread : consumers)
            service.execute(thread);
        do {
            Thread.sleep(10 * 1000);
        } while (true);
    }
}