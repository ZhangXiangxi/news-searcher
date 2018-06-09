import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class DictionaryTest {
    @Test
    public void testSort() {
        Dictionary dictionary = new Dictionary();
        dictionary.addContent("This is a test content.The content is meant to be a test of a class.");
        dictionary.makeDictionary();
        assertTrue(dictionary.isReady);
        for (var entry : dictionary.dictionary) {
            System.out.println(entry.frequency + " " + entry.word);
        }
    }

    @Test
    public void testWithRealData() {
        Dictionary dictionary = new Dictionary();
        dictionary.addContentFromNews("../cleanedArticles.csv");
        dictionary.makeDictionary();
        assertTrue(dictionary.isReady);
        int count = 0;
        for (var entry : dictionary.dictionary) {
            System.out.println(entry.frequency + " " + entry.word);
            if (count++ > 10)
                break;
        }
    }
}