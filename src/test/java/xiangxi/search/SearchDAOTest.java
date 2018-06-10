package xiangxi.search;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class SearchDAOTest {
    @Test
    public void test() {
        SearchDAO searchDAO = new SearchDAO(3.0);
        ArrayList<String> words = new ArrayList<>();
        for (var slice : "who is the most powerful people in silicon valley".split(" "))
            words.add(slice);
        searchDAO.searchForWords(words);
    }
}