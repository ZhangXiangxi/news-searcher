package xiangxi.newsTable;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Xiangxi on 2018/6/9.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
public class NewsTable {
    private FileOutputStream outputStream;
    private RandomAccessFile contentFile = null;
    private boolean writeToFile;
    private NewsDAO newsDAO;
    long beginCount = 0;
    public NewsTable(String outputPath) {
        writeToFile = !new File(outputPath).exists();
        newsDAO = new NewsDAO();
        try {
            if (!writeToFile)
                contentFile = new RandomAccessFile(outputPath, "r");
            else
                outputStream = new FileOutputStream(new File(outputPath), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readFileBetweenPosition(long beginPosition, long endPosition) {
        assert(contentFile != null);
        try {
            contentFile.seek(beginPosition);
            return contentFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void writeNewsFromFile(String path) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
            var csvToBean = new CsvToBeanBuilder<NewsRecord>(reader).withType(NewsRecord.class).build();
            int count = 0;
            for (var record : csvToBean) {
                writeNews(record);
                count++;
                if (count % 200 == 0)
                    System.out.println(count);
            }
            if (outputStream != null)
                outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeNews(NewsRecord record) {
        byte[] cleanContent = (record.getContent().replace("\n", " ") + "\n").getBytes();
        try {
            if (writeToFile)
                outputStream.write(cleanContent);
            else {
                NewsMetadata newsMetadata = new NewsMetadata(record.getId(), record.getTitle(), record.getPublication(),
                        record.getAuthor(), record.getYear(), record.getMonth(), beginCount, beginCount+cleanContent.length);
                beginCount += cleanContent.length;
                newsDAO.insertRecord(newsMetadata);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
