package xiangxi.recordClean;

import com.opencsv.bean.CsvBindByName;

/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsRecordRaw {
    @CsvBindByName
    private int id;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String publication;
    @CsvBindByName
    private String author;
    @CsvBindByName
    private String year;
    @CsvBindByName
    private String month;
    @CsvBindByName
    private String url;
    @CsvBindByName
    private String content;

    public NewsRecordRaw(){}

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
