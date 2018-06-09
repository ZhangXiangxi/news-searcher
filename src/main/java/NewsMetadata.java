/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsMetadata {
    private int id;
    private String title;
    private String publication;
    private String author;
    private int year;
    private int month;
    public NewsMetadata() {}
    public NewsMetadata(NewsRecord record) {
        id = record.getId();
        title = record.getTitle();
        publication = record.getPublication();
        author = record.getAuthor();
        year = record.getYear();
        month = record.getMonth();
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
}
