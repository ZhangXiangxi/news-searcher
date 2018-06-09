import org.junit.Test;
import xiangxi.wordTable.WordTable;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class WordTableTest {
    @Test
    public void testSort() {
        WordTable wordTable = new WordTable();
        wordTable.addContent("This is a test content.The content is meant to be a test of a class.");
        wordTable.makeDictionary();
        assertTrue(wordTable.isReady);
        for (var entry : wordTable.dictionary) {
            System.out.println(entry.frequency + " " + entry.word);
        }
    }

    @Test
    public void testWithRealData() {
        WordTable wordTable = new WordTable();
        wordTable.addContentFromNews("../cleanedArticles.csv");
        wordTable.makeDictionary();
        assertTrue(wordTable.isReady);
        int count = 0;
        for (var entry : wordTable.dictionary) {
            System.out.println(entry.frequency + " " + entry.word);
            if (count++ > 10)
                break;
        }
    }

    @Test
    public void testStopWordSet() {
        WordTable wordTable = new WordTable();
        for(var word : wordTable.stopWordSet) {
            System.out.println(word);
        }
    }
}