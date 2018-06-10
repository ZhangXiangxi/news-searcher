package xiangxi.invertedIndex;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class InvertedIndexEntry {
    int wordID;
    int newsID;
    int frequency;
    boolean isTitle;    // whether the word lies in the title or not;

    public InvertedIndexEntry(int wordID, int newsID, int frequency,boolean isTitle) {
        this.wordID = wordID;
        this.newsID = newsID;
        this.isTitle = isTitle;
        this.frequency = frequency;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
