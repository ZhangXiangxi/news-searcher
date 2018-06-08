import org.junit.Test;

import java.util.ArrayList;

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
}