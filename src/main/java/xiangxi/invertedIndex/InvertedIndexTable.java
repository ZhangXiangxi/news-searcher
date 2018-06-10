package xiangxi.invertedIndex;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import xiangxi.newsTable.NewsRecord;
import xiangxi.wordTable.WordDAO;
import xiangxi.wordTable.WordTable;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeSet;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class InvertedIndexTable {
    private Trie<String, Integer> wordToID;
    private WordDAO wordDAO;
    private InvertedIndexDAO invertedIndexDAO;
    public TreeSet<String> stopWordSet;
    public InvertedIndexTable() {
        wordDAO = new WordDAO();
        invertedIndexDAO = new InvertedIndexDAO();
        wordToID = wordDAO.getFirstWords(5000);
        stopWordSet = WordTable.makeUpStopWordSet();
    }

    public void makeInvertedIndexTable(String inputPath) {
        System.out.println("making inverted index table");
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(inputPath));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            for (var record : csvToBean) {
                count++;
                if (count % 10 == 0)
                    System.out.println(count);
                generateInvertedEntry(record.getTitle(), true, record.getId());
                generateInvertedEntry(record.getContent(), false, record.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void generateInvertedEntry(String text, boolean isTitle, int news_id) {
        var wordList = WordTable.cleanContent(text);
        Trie<String, Integer> wordFrequency = new PatriciaTrie<>();
        for (var word : wordList) {
            if (stopWordSet.contains(word))
                continue;
            if (!wordFrequency.containsKey(word))
                wordFrequency.put(word, 1);
            else {
                wordFrequency.put(word, wordFrequency.get(word)+1);
            }
        }
        for(var entry : wordFrequency.entrySet()) {
            InvertedIndexEntry invertedEntry = new InvertedIndexEntry(wordToWordID(entry.getKey()), news_id, entry.getValue(), isTitle);
            invertedIndexDAO.insert(invertedEntry);
        }
    }
    private int wordToWordID(String word) {
        if (wordToID.containsKey(word))
            return wordToID.get(word);
        return wordDAO.searchIDWithWord(word);
    }
}
