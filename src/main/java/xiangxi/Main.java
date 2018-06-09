package xiangxi;

import xiangxi.newsTable.NewsTable;
import xiangxi.recordClean.RawRecordTransformer;
import xiangxi.wordTable.WordTable;

import java.io.File;


/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class Main {
    public static final String CLEAN_ARTICLES_PATH = "../cleanedArticles.csv";
    public static void main(String[] args) {
        boolean hasDictionary = true;
        boolean hasNewsTable = false;
        System.out.println("Main!");
        if (!new File(CLEAN_ARTICLES_PATH).exists()) {
            String outputPath = CLEAN_ARTICLES_PATH;
            String[] inputPaths = new String[]{"../articles1.csv", "../articles2.csv", "../articles3.csv"};
            RawRecordTransformer rawRecordTransformer = new RawRecordTransformer(outputPath, inputPaths, false);
        }
        if (!hasDictionary) {
            WordTable wordTable = new WordTable();
            wordTable.addContentFromNews(CLEAN_ARTICLES_PATH);
            wordTable.makeDictionary();
            assert (wordTable.isReady);
            wordTable.saveDictionary();
        }
        if (!hasNewsTable) {
            NewsTable newsTable = new NewsTable();
            newsTable.writeNewsFromFile(CLEAN_ARTICLES_PATH);
        }
    }
}
