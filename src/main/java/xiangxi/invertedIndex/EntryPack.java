package xiangxi.invertedIndex;

import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class EntryPack {
    public ArrayList<String> wordList;
    public IntArrayList freqList;
    public int news_id;
    public boolean isTitle;

    public EntryPack(ArrayList<String> wordList, IntArrayList freqList, int news_id, boolean isTitle) {
        this.wordList = wordList;
        this.freqList = freqList;
        this.news_id = news_id;
        this.isTitle = isTitle;
    }
}
