/**
 * Created by Xiangxi on 2018/6/8.
 * Contact him on xiangxi.zhang.cs@gmail.com
 */
// This class will transform the raw data into a more organised form
public class RawRecordTransformer {
    public String outputFile;
    public String[] inputFiles;
    public boolean useUrlData;

    public RawRecordTransformer(String outputFile, String[] inputFiles, boolean useUrlData) {
        this.outputFile = outputFile;
        this.inputFiles = inputFiles;
        this.useUrlData = useUrlData;
        openOutputFile();
        for(var path : inputFiles)
            writeOneFile(path);
    }
    private void openOutputFile() {
        System.out.println(outputFile);
    }
    private void writeOneFile(String inputPath) {
        System.out.println(inputPath);
    }
}
