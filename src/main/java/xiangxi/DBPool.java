package xiangxi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class DBPool {
    private Connection conn;
    public void createConn(){
        try{
            String url="jdbc:mysql://localhost/new_schema?useUnicode=true&character_set_server=utf8mb4&charset&characterEncoding=utf8mb4";
            String user = "modugi";
            String password = "modugi";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("The database is successfully connected!");
        }catch(Exception e){
            System.out.println("Failed to connect the database!");
            e.printStackTrace();
        }
    }
    public  Connection getConn(){
        if(conn == null){
            createConn();
        }
        return conn;
    }
}
