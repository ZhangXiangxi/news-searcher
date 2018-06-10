package xiangxi.invertedIndex;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import xiangxi.DBPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class IndexInserterThread implements Runnable{
    private final BlockingQueue<EntryPack> consumerQueue;
    private Connection connection;
    public IndexInserterThread(BlockingQueue<EntryPack> queue) {
        this.consumerQueue = queue;
        connection = new DBPool().getConn();
    }
    @Override
    public void run() {
        do {
            try {
                EntryPack entryPack = consumerQueue.take();
                insert(entryPack);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } while(true);
    }
    public void insert(EntryPack pack) {
        try {
            var wordList = pack.wordList;
            var freqList = pack.freqList;
            int news_id = pack.news_id;
            boolean isTitle = pack.isTitle;
            var ps = connection.prepareStatement("INSERT INTO inverted_indexes VALUE (?,?,?,?)");
            connection.setAutoCommit(false);
            assert(wordList.size() == freqList.size());
            for(int i = 0; i < wordList.size(); i++) {
                ps.setString(1, wordList.get(i));
                ps.setInt(2, news_id);
                ps.setInt(3, freqList.getInt(i));
                ps.setBoolean(4, isTitle);
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
