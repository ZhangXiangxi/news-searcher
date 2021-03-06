package xiangxi;

import xiangxi.invertedIndex.InvertedIndexTable;
import xiangxi.newsTable.NewsTable;
import xiangxi.recordClean.RawRecordTransformer;
import xiangxi.wordTable.WordTable;

import java.io.File;
import java.io.RandomAccessFile;


/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class Main {
    public static final String CLEAN_ARTICLES_PATH = "../cleanedArticles.csv";
    public static final String NEWS_CONTENT_PATH = "../newsContent.dat";
    public static void main(String[] args) {
        boolean hasDictionary = true;
        boolean hasNewsTable = true;
        boolean hasInvertedTable = true;
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
            NewsTable newsTable = new NewsTable(NEWS_CONTENT_PATH);
            newsTable.writeNewsFromFile(CLEAN_ARTICLES_PATH);
        }
        if (!hasInvertedTable) {
            InvertedIndexTable invertedIndexTable = new InvertedIndexTable();
            invertedIndexTable.makeInvertedIndexTable(CLEAN_ARTICLES_PATH);
        }
    }
}
