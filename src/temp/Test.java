package temp;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static java.io.File.separator;

public class Test {

    public void test(){
        String path1 = System.getProperty("user.dir") + separator + "logfile" + separator + "mutantBekilledInfo_SkipQueue0.txt";
        String path2 = "/home/phantom/IdeaProjects/cpt/logfile/mutantBeKilledInfo_SkipQueue0.txt";
        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path1.equals(path2));
        File file1 = new File(path1);
        File file2 = new File(path2);
        System.out.println(file1.isFile());
        System.out.println(file2.isFile());
//        try{
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file1));
//            String tempStr = "";
//            while ((tempStr = bufferedReader.readLine()) != null){
//                System.out.println(tempStr);
//                break;
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        if (set.contains("1"))
            set.remove("1");
        System.out.println(set.size());
    }
}
