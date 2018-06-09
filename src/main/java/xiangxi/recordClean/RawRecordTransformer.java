package xiangxi.recordClean;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import xiangxi.newsTable.NewsRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
// This class will transform the raw data into a more organised form
public class RawRecordTransformer {
    public String outputFile;
    public String[] inputFiles;
    public boolean useUrlData;
    StatefulBeanToCsv<NewsRecord> beanToCsv = null;

    public RawRecordTransformer(String outputFile, String[] inputFiles, boolean useUrlData) {
        this.outputFile = outputFile;
        this.inputFiles = inputFiles;
        this.useUrlData = useUrlData;
        openOutputFile();
        for(var path : inputFiles)
            writeOneFile(path);
    }
    private void openOutputFile() {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(outputFile));
            beanToCsv = new StatefulBeanToCsvBuilder<NewsRecord>(writer).withOrderedResults(false).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeOneFile(String inputPath) {
        assert(beanToCsv != null);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(inputPath));
            var csvToBean = new CsvToBeanBuilder<NewsRecordRaw>(reader).withType(NewsRecordRaw.class).build();
            for (var rawRecord : csvToBean) {
                if (rawRecord.getContent().length() != 0) {
                    var record = new NewsRecord(rawRecord);
                    beanToCsv.write(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
