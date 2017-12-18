package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

/**
 * @author phantom
 */
public class MoveMutants {
    public MoveMutants(String SUTname) {
        /*要读取的文件的位置*/
        String readFile = System.getProperty("user.dir") + separator + "temp" + separator + SUTname + ".txt";
        File readfile = new File(readFile);
        if (!readfile.exists()){
            System.out.println("读取文件不存在");
        }
        /*存放要移动的文件的名字*/
        List<String> names = new ArrayList<String>();
        try{
            BufferedReader bf = new BufferedReader(new FileReader(readfile));
            String temp = "";
            while((temp = bf.readLine()) != null){
                names.add(temp);
            }
            /*获取要移动的文件的名字之后，开始移动文件*/
            String endPath = System.getProperty("user.dir") + separator + "excuterWrong" + separator + SUTname;
            for (int i = 0; i < names.size(); i++) {
                String startPath = System.getProperty("user.dir") + separator +"src" + separator + "mutants" + separator +
                        SUTname + separator + names.get(i);
                File starFile = new File(startPath);
                starFile.renameTo(new File(endPath));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        MoveMutants mm = new MoveMutants("SkipQueue");
    }

}
