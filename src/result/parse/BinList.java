package result.parse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phantom
 */
public class BinList {
    private List<String> list;
    public BinList() {
        list = new ArrayList<>();
    }

    public int size(){return list.size();}

    public String get(int index){return list.get(index);}

    public void add (String str){
        list.add(str);
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
