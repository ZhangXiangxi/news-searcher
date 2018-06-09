import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
// This class is an dictionary for words for files
public class Dictionary {
    public Set<String> dictionary;
    public Dictionary() {
        dictionary = new TreeSet<>();
    }
    public void addContent(String content) {
        dictionary.addAll(cleanContent(content));
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
}
