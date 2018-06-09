import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class DictionaryTest {
    @Test
    public void testAddingContent() {
        Dictionary dictionary = new Dictionary();
        dictionary.addContent("This is an content example. The example is \"kind\" of strange...");
        assertTrue(dictionary.dictionary.contains("kind"));
        assertTrue(dictionary.dictionary.contains("example"));
        assertTrue(dictionary.dictionary.contains("this"));
        assertFalse(dictionary.dictionary.contains("This"));
    }

}