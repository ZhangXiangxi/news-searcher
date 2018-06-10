package xiangxi.newsTable;

/**
 * Created by Xiangxi on 2018/6/10.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsMetadata {
    private int id;

    private String title;

    private String publication;

    private String author;

    private int year;

    private int month;

    private long startContentPosition;

    private long endContentPosition;

    public NewsMetadata(int id, String title, String publication, String author, int year, int month, long startContentPosition, long endContentPosition) {
        this.id = id;
        this.title = title;
        this.publication = publication;
        this.author = author;
        this.year = year;
        this.month = month;
        this.startContentPosition = startContentPosition;
        this.endContentPosition = endContentPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getStartContentPosition() {
        return startContentPosition;
    }

    public void setStartContentPosition(long startContentPosition) {
        this.startContentPosition = startContentPosition;
    }

    public long getEndContentPosition() {
        return endContentPosition;
    }

    public void setEndContentPosition(long endContentPosition) {
        this.endContentPosition = endContentPosition;
    }
}
