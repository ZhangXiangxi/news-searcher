import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
// This class is an dictionary for words for files
public class Dictionary {
    public ArrayList<WordEntry> dictionary;
    public Trie<String, Integer> wordCount;
    public WordDAO wordDAO;
    boolean isReady = false;
    public Dictionary() {
        dictionary = new ArrayList<>();
        wordCount = new PatriciaTrie<Integer>();
        wordDAO = new WordDAO();
    }
    public void makeDictionary() {
        for(var pair : wordCount.entrySet()) {
            WordEntry entry = new WordEntry(pair.getKey(), pair.getValue());
            dictionary.add(entry);
        }
        Collections.sort(dictionary);
        isReady = true;
    }
    public void saveDictionary() {
        assert(isReady);
        int count = 0;
        for(var entry : dictionary)
            wordDAO.insertWord(count++, entry.word);
    }
    public void addContent(String content) {
        for(var word : cleanContent(content)) {
            if (!wordCount.containsKey(word))
                wordCount.put(word, 1);
            else {
                int frequency = wordCount.get(word);
                wordCount.put(word, frequency+1);
            }
        }
    }
    public void addContentFromNews(String path) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            for (var record : csvToBean) {
                addContent(record.getTitle());
                addContent(record.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ArrayList<String> cleanContent(String content) {
        var result = new ArrayList<String>();
        boolean readState = false; // mark whether the automata is reading words or not.
        StringBuilder builder = new StringBuilder();
        for(char c : content.toCharArray()) {
            if (!readState && !Character.isAlphabetic(c))
                continue;
            if (!readState && Character.isAlphabetic(c)) {
                builder.setLength(0);
                readState = true;
                builder.append(Character.toLowerCase(c));
                continue;
            }
            if (readState && Character.isAlphabetic(c)) {
                builder.append(Character.toLowerCase(c));
                continue;
            }
            if (readState && !Character.isAlphabetic(c)) {
                result.add(builder.toString());
                readState = false;
            }
        }
        return result;
    }

    public class WordEntry implements Comparable{
        public String word;
        public int frequency;

        public WordEntry(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public int compareTo(Object o) {
            if (!o.getClass().equals(WordEntry.class))
                return 1;
            assert(o.getClass().equals(WordEntry.class));
            WordEntry another = (WordEntry)o;
            if (another.frequency != frequency)
                return another.frequency-frequency;
            return word.compareTo(another.word);
        }
    }
}
