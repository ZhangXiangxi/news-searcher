import org.junit.Before;
import org.junit.Test;
import xiangxi.wordTable.WordDAO;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class WordDAOTest {
    public WordDAO wordDAO;

    @Before
    public void setUp() {
        wordDAO = new WordDAO();
    }
    @Test
    public void testWrite() {
        System.out.println(wordDAO.insertSqlBuilder("Test", 4));
        wordDAO.insertWord(4, "Test");
    }
    @Test
    public void testSearchByID() {
        System.out.println(wordDAO.searchWordWithID(4));
    }
    @Test
    public void testSearchByWord() {
        System.out.println(wordDAO.searchIDWithWord("Test"));
    }
}