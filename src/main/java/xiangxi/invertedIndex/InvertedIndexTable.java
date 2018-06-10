package xiangxi.invertedIndex;

import com.opencsv.bean.CsvToBeanBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import xiangxi.newsTable.NewsRecord;
import xiangxi.wordTable.WordDAO;
import xiangxi.wordTable.WordTable;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class InvertedIndexTable implements Runnable{
    private Trie<String, Integer> wordToID;
    private WordDAO wordDAO;
    private InvertedIndexDAO invertedIndexDAO;
    public TreeSet<String> stopWordSet;
    private final BlockingQueue<EntryPack> producerQueue;
    private String inputPath;
    public InvertedIndexTable() {
        wordDAO = new WordDAO();
        invertedIndexDAO = new InvertedIndexDAO();
        wordToID = wordDAO.getFirstWords(10000);
        stopWordSet = WordTable.makeUpStopWordSet();
        producerQueue = null;
    }
    public InvertedIndexTable(String inputPath, BlockingQueue<EntryPack> queue) {
        wordDAO = new WordDAO();
        invertedIndexDAO = new InvertedIndexDAO();
        wordToID = wordDAO.getFirstWords(10000);
        stopWordSet = WordTable.makeUpStopWordSet();
        this.inputPath = inputPath;
        producerQueue = queue;
    }

    @Override
    public void run() {
        System.out.println("making inverted index table");
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(inputPath));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            for (var record : csvToBean) {
                count++;
                if (count % 20 == 0)
                    System.out.println(count);
                while(producerQueue.remainingCapacity()==0)
                    Thread.sleep(10);
                producerQueue.add(generateEntryPack(record.getTitle(), true, record.getId()));
                while(producerQueue.remainingCapacity()==0)
                    Thread.sleep(10);
                producerQueue.add(generateEntryPack(record.getContent(), false, record.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private EntryPack generateEntryPack(String text, boolean isTitle, int news_id) {
        var wordList = WordTable.cleanContent(text);
        HashMap<String, Integer> wordFrequency = new HashMap<>();
        for (var word : wordList) {
            if (stopWordSet.contains(word))
                continue;
            if (!wordFrequency.containsKey(word))
                wordFrequency.put(word, 1);
            else {
                wordFrequency.put(word, wordFrequency.get(word)+1);
            }
        }
        ArrayList<String> wordIDList = new ArrayList(wordFrequency.size());
        IntArrayList freqList = new IntArrayList(wordFrequency.size());
        for(var entry : wordFrequency.entrySet()) {
            wordIDList.add(entry.getKey());
            freqList.add(entry.getValue());
        }
        return new EntryPack(wordIDList, freqList, news_id, isTitle);
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
        IntArrayList wordIDList = new IntArrayList(wordFrequency.size());
        IntArrayList freqList = new IntArrayList(wordFrequency.size());
        for(var entry : wordFrequency.entrySet()) {
            wordIDList.add(wordToWordID(entry.getKey()));
            freqList.add(entry.getValue());
        }
        invertedIndexDAO.insert(wordIDList, freqList, news_id, isTitle);
    }
    private int wordToWordID(String word) {
        if (wordToID.containsKey(word))
            return wordToID.get(word);
        return wordDAO.searchIDWithWord(word);
    }
}
