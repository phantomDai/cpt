package result.parse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phantom
 */
public class ParseCategory {
    public ParseCategory() {}



    /*
    * 该方法从logfile文件中根据SUTName读取数据，将每一个程序对应的变异体category信息以及
    * 没有杀死的变异体信息记录下来
    * */
    public void parseCategory(String SUTName){
        //记录SUT对应的category信息
        List<String> categoryInfo = new ArrayList<String>();
        //记录SUT被杀死的category信息
        List<String> killedCategoryInfo = new ArrayList<String>();
        if (SUTName.equals("SimpleLinear")){



        }
    }



}
