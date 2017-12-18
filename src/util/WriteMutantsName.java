package util;

import java.io.*;

import static java.io.File.separator;

/**
 * 该类实现了从SUTmutants文件下读取所有的mutants名字写到txt文件中以便记录到excel中
 * @author phantom
 */
public class WriteMutantsName {
    public WriteMutantsName() {
    }

    public void writeMutantsName(String SUTname){
        String path = System.getProperty("user.dir") + separator + "excuterWrong" + separator +
                SUTname;
        File file = new File(path);
        String[] mutantsName = file.list();
        //向文件中写入名字
        StringBuffer stringBuffer = new StringBuffer(10);
        for (int i = 0; i < mutantsName.length; i++) {
            stringBuffer.append(mutantsName[i] + "\n");
        }
        String in = System.getProperty("user.dir") + separator + "temp" + separator + SUTname + ".txt";
        try{
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(in)));
            printWriter.write(stringBuffer.toString());
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WriteMutantsName wm = new WriteMutantsName();
        wm.writeMutantsName("SkipQueue");
    }

}
