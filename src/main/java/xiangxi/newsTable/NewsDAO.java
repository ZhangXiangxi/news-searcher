package xiangxi.newsTable;

import xiangxi.DBPool;

import java.sql.Connection;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsDAO {
    public Connection connection;
    public StringBuilder builder;
    public NewsDAO() {
        connection = new DBPool().getConn();
        builder = new StringBuilder();
    }
    public void writeRecord(NewsRecord record) {
        // TODO:
    }
}
