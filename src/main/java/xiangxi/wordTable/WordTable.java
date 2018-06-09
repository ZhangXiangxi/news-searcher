package xiangxi.wordTable;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import xiangxi.newsTable.NewsRecord;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
// This class is an dictionary for words for files
public class WordTable {
    public ArrayList<WordEntry> dictionary;
    public Trie<String, Integer> wordCount;
    public WordDAO wordDAO;
    public TreeSet<String> stopWordSet;
    public static final String[] STOP_WORDS = { "a", "about", "above", "after", "again", "against", "all", "am", "an",
            "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both",
            "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from",
            "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers",
            "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into",
            "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on",
            "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "she",
            "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their",
            "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're",
            "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd",
            "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while",
            "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've",
            "your", "yours", "yourself", "yourselves", "t"};
    public boolean isReady = false;
    public WordTable() {
        dictionary = new ArrayList<>();
        wordCount = new PatriciaTrie<Integer>();
        wordDAO = new WordDAO();
        stopWordSet = new TreeSet<>();
        makeUpStopWordSet();
    }

    private void makeUpStopWordSet() {
        for (var stopWord : STOP_WORDS) {
            stopWordSet.addAll(Arrays.asList(stopWord.split("'")));
        }
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
        int count = 1;
        for(var entry : dictionary)
            wordDAO.insertWord(count++, entry.word);
    }
    public void addContent(String content) {
        for(var word : cleanContent(content)) {
            if (stopWordSet.contains(word))
                continue;
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
            if (!readState && !isEnglishLetter(c))
                continue;
            if (!readState && isEnglishLetter(c)) {
                builder.setLength(0);
                readState = true;
                builder.append(Character.toLowerCase(c));
                continue;
            }
            if (readState && isEnglishLetter(c)) {
                builder.append(Character.toLowerCase(c));
                continue;
            }
            if (readState && !isEnglishLetter(c)) {
                result.add(builder.toString());
                readState = false;
            }
        }
        return result;
    }
    private boolean isEnglishLetter(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
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
