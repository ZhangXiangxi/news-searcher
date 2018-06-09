package xiangxi;

import xiangxi.recordClean.RawRecordTransformer;
import xiangxi.wordTable.Dictionary;

import java.io.File;


/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class Main {
    public static void main(String[] args) {
        boolean hasDictionary = true;
        System.out.println("xiangxi.Main!");
        if (!new File("../cleanedArticles.csv").exists()) {
            String outputPath = "../cleanedArticles.csv";
            String[] inputPaths = new String[]{"../articles1.csv", "../articles2.csv", "../articles3.csv"};
            RawRecordTransformer rawRecordTransformer = new RawRecordTransformer(outputPath, inputPaths, false);
        }
        if (!hasDictionary) {
            Dictionary dictionary = new Dictionary();
            dictionary.addContentFromNews("../cleanedArticles.csv");
            dictionary.makeDictionary();
            assert (dictionary.isReady);
            dictionary.saveDictionary();
        }
    }
}
