import com.opencsv.bean.CsvBindByName;

/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsRecord {
    @CsvBindByName
    private int id;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String publication;
    @CsvBindByName
    private String author;
    @CsvBindByName
    private int year;
    @CsvBindByName
    private int month;
    @CsvBindByName
    private String content;
    public NewsRecord() {

    }
    public NewsRecord(NewsRecordRaw rawRecord) {
        id = rawRecord.getId();
        title = rawRecord.getTitle();
        publication = rawRecord.getPublication();
        author = rawRecord.getAuthor();
        year = floatStringToInt(rawRecord.getYear());
        month = floatStringToInt(rawRecord.getMonth());
        content = rawRecord.getContent();
    }

    private static int floatStringToInt(String s) {
        if (s.length() == 0)
            return 0;
        else
            return (int) Float.parseFloat(s);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
