package result.parse;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author phantom
 * 该类存放某一个MR杀死的category 信息
 */
public class MRBin {
    private Set<Object> set;//存放该MR杀死的变异体

    public MRBin() {
        set = new HashSet<>();
    }

    private int[] record = new int[8];//记录杀死的变异体对应的category信息

    public int[] getRecord() {
        return record;
    }

    //根据index获取对象分类的杀死信息
    public int getDateFromRecord(int index){return record[index];}

    public void setRecord(int[] record) {
        this.record = record;
    }

    //向集合中添加元素，并且自动过滤已经存在的元素
    public void add(String mutant){set.add(mutant);}

    //获得record中的和
    public String getsum(){
        int sum = 0 ;
        for (int i = 0; i < record.length; i++) {
            sum += record[i];
        }
        return String.valueOf(sum);
    }

    public Set<Object> getSet() {
        return set;
    }

    public void setSet(Set<Object> set) {
        this.set = set;
    }

    /**
     * 对set中的变异体进行分类
     */
    public void parseSet(){
        for (int j = 0; j < record.length; j++) {
            record[j] = 0 ;
        }
        for (Object name1 : set) {
            String name = (String)name1;
            String[] tempstr = name.split("_");
            name = tempstr[0];
            if (name.equals("AORB") || name.equals("AORS")||name.equals("AOIU")||name.equals("AOIS")){
                record[0] += 1 ;
            }else if (name.equals("ROR")){
                record[1] += 1 ;
            }else if (name.equals("COR")||name.equals("COI")||name.equals("COD")){
                record[2] += 1 ;
            }else if (name.equals("LOI")){
                record[3] += 1 ;
            }else if (name.equals("SDL")||name.equals("VDL")||name.equals("CDL")||name.equals("ODL")){
                record[4] += 1 ;
            }else if (name.equals("RCXC")||name.equals("SAN")||name.equals("ELPA")){
                record[5] += 1 ;
            }else if (name.equals("ASTK")||name.equals("RSK")||name.equals("RFU")){
                record[6] += 1 ;
            }else {
                record[7] += 1 ;
            }
        }

    }

    public void addSet(Collection<Object> outSet){
        set.addAll(outSet);
    }
}
