package result.parse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.read.biff.BiffException;
import jxl.write.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.io.File.separator;
import static jxl.format.BorderLineStyle.THIN;
import static jxl.format.VerticalAlignment.CENTRE;

/**
 * @author phantom
 */
public class ParseCategory {
    //记录没有被杀死的变异体
    private Set<String> notkilledmutants;

    //记录杀死的变异体
    private Set<String> killedmutants;

    //记录每一个MR杀死变异体信息的对象
    private MRBin[] mrBins;

    //记录每一个category中没有杀死的变异体
    private BinList[] notkilledBinLists;

    //记录每一个category中杀死的变异体
    private BinList[] killedBinLists;

    //记录每一个category中没有杀死的变异体个数
    private int[] notkilledrecord;

    //记录每一个category中杀死的变异体个数
    private int[] killedrecord;

    //存放一个循环中没有杀死别的循环杀死的变异体
    List<String> mutantslist = new ArrayList<>();

    /*
    * 该方法从logfile文件中根据SUTName读取数据，将每一个程序对应的变异体category信息以及
    * 没有杀死的变异体信息记录下来
    * */
    public void parseCategory(String SUTName){
        notkilledmutants = new HashSet<>();
        killedmutants = new HashSet<>();
        mrBins = new MRBin[31];//i 从26到30对应的mR分别为：MR10、MR11、MR16、MR18、MR19
        for (int i = 0; i < 31; i++) {
            mrBins[i] = new MRBin();
        }

        //创建EXCEL表格并且创建表头信息
        creatExcelTitle(SUTName);
        //读取10个文件获取没有检测出的变异体
        for (int i = 0; i < 10; i++) {
            //逐个读取文件
            String path = System.getProperty("user.dir") + separator + "logfile" + separator + "mutantBekilledInfo_"
                    + SUTName + String.valueOf(i) + ".txt" ;
            File file = new File(path);
            //开始提取数据
            try{
                if (!file.exists()){
                    System.out.println("文件不存在");
                }
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String temp ;
                //逐行提取相关信息
                while((temp = bufferedReader.readLine()) != null){
                    String[] info = temp.split(":");//将一行信息分成两部分：变异体信息以及MR信息
                    //对没有被检测出的变异体进行识别
                    if (info.length == 1){//表明该变异体没有被检测
                        String[] mutantInfo = info[0].split("\\.");
                        String mutant = mutantInfo[2];//没有被杀死的变异体的名称
                        if (!mutant.equals("A_original"))//将源程序排除
                            notkilledmutants.add(mutant);
                    }else {//该变异体体被杀死了
                        //获取该变异体的名字
                        String[] mutantInfo = info[0].split("\\.");
                        String mutant = mutantInfo[2];//没有被杀死的变异体的名称
                        //判断该变异体是否在notkilledmutants
                        if (notkilledmutants.contains(mutant)){
                            mutantslist.add(mutant);
                        }
                        killedmutants.add(mutant);
                        //创建一个暂时存储某一行杀死变异体的MR
                        Set<String> tempset = new HashSet<>();
                        tempset.clear();
                        String[] mrInfo = info[1].split(";");
                        for (int j = 0; j < mrInfo.length; j++) {
                            tempset.add(mrInfo[j]);
                        }
                        //对tempset中的mr进行遍历判断是哪一个MR然后将mutant信息添加到对应的MRBin中
                        for (String mr : tempset) {
                            switch (mr){
                                case "MR1":
                                    mrBins[0].add(mutant);
                                    break;
                                case "MR2":
                                    mrBins[1].add(mutant);
                                    break;
                                case "MR3":
                                    mrBins[2].add(mutant);
                                    break;
                                case "MR4":
                                    mrBins[3].add(mutant);
                                    break;
                                case "MR5":
                                    mrBins[4].add(mutant);
                                    break;
                                case "MR6":
                                    mrBins[5].add(mutant);
                                    break;
                                case "MR7":
                                    mrBins[6].add(mutant);
                                    break;
                                case "MR8":
                                    mrBins[7].add(mutant);
                                    break;
                                case "MR9":
                                    mrBins[8].add(mutant);
                                    break;
                                case "MR10_1":
                                    mrBins[9].add(mutant);
                                    break;
                                case "MR10_2":
                                    mrBins[10].add(mutant);
                                    break;
                                case "MR10_3":
                                    mrBins[11].add(mutant);
                                    break;
                                case "MR11_1":
                                    mrBins[12].add(mutant);
                                    break;
                                case "MR11_2":
                                    mrBins[13].add(mutant);
                                    break;
                                case "MR11_3":
                                    mrBins[14].add(mutant);
                                    break;
                                case "MR12":
                                    mrBins[15].add(mutant);
                                    break;
                                case "MR13":
                                    mrBins[16].add(mutant);
                                    break;
                                case "MR14":
                                    mrBins[17].add(mutant);
                                    break;
                                case "MR15":
                                    mrBins[18].add(mutant);
                                    break;
                                case "MR16_1":
                                    mrBins[19].add(mutant);
                                    break;
                                case "MR16_2":
                                    mrBins[20].add(mutant);
                                    break;
                                case "MR17":
                                    mrBins[21].add(mutant);
                                    break;
                                case "MR18_1":
                                    mrBins[22].add(mutant);
                                    break;
                                case "MR18_2":
                                    mrBins[23].add(mutant);
                                    break;
                                case "MR19_1":
                                    mrBins[24].add(mutant);
                                    break;
                                case "MR19_2":
                                    mrBins[25].add(mutant);
                                    break;
                            }
                        }
                    }

                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//for循环结束了
        //处理notkilled信息
        for (int i = 0; i < mutantslist.size(); i++) {
            if (notkilledmutants.contains(mutantslist.get(i))){
                notkilledmutants.remove(mutantslist.get(i));
            }
        }

        notkilledBinLists = new BinList[8];
        killedBinLists = new BinList[8];
        notkilledrecord = new int[8];
        killedrecord = new int[8];
        for (int i = 0; i < 8; i++) {
            notkilledBinLists[i] = new BinList();
            killedBinLists[i] = new BinList();
            notkilledrecord[i] = 0 ;
            killedrecord[i] = 0 ;
        }
        //对两个集合中的元素进行解析
        parseSet(notkilledmutants,killedmutants);
        //对每一个集合中的元素进行解析，获得每一个MR杀死分类中变异体的信息
        for (int i = 0; i < 31; i++) {
            if (i < 26){
                mrBins[i].parseSet();
            }else {
                if (i == 26){
                    mrBins[26].addSet(mrBins[9].getSet());
                    mrBins[26].addSet(mrBins[10].getSet());
                    mrBins[26].addSet(mrBins[11].getSet());
                    mrBins[26].parseSet();
                }else if (i == 27){
                    mrBins[27].addSet(mrBins[12].getSet());
                    mrBins[27].addSet(mrBins[13].getSet());
                    mrBins[27].addSet(mrBins[14].getSet());
                    mrBins[27].parseSet();
                }else if (i == 28){
                    mrBins[28].addSet(mrBins[19].getSet());
                    mrBins[28].addSet(mrBins[20].getSet());
                    mrBins[28].parseSet();
                }else if (i == 29){
                    mrBins[29].addSet(mrBins[22].getSet());
                    mrBins[29].addSet(mrBins[23].getSet());
                    mrBins[29].parseSet();
                }else if (i == 30){
                    mrBins[30].addSet(mrBins[24].getSet());
                    mrBins[30].addSet(mrBins[25].getSet());
                    mrBins[30].parseSet();
                }
            }
        }

        //向EXCEL中写入数据
        writeDateIntoEXCEL(SUTName);
    }

    private void writeDateIntoEXCEL(String SUTName){
        String path = System.getProperty("user.dir") + separator + "datafile" + separator
                + "categoryinfo_" + SUTName + ".xls";
        File file = new File(path);
        try{
            //首先设置表格属性
            WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 12);
            WritableCellFormat wcf_center = new WritableCellFormat(normalFont);
            wcf_center.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(jxl.format.Alignment.CENTRE); // 文字水平对齐


            //获得源文件中的内容
            Workbook originalWorkBook = Workbook.getWorkbook(file);
            //在原来的基础上写入数据
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file,originalWorkBook);
            //获得要添加内容的sheet
            WritableSheet sheet = writableWorkbook.getSheet(0);
            //获取表中一共又多少列
            int columns = sheet.getColumns();
            //初始化categories信息
            String[] categories = {"1", "2", "3", "4", "5", "6", "7", "8"};
            //初始化Operators信息
            String[] operators = {"AORB,AORS,AOIU,AOIS", "ROR", "COR,COI,COD", "LOI", "SDL,VDL,CDL,ODL", "RCXC,SAN,ELPA",
                                    "RSK", "RXO,EELO,ELPA"};
            String[] SimpleLinear = {"4","4","2","0","3","0","1","0"};
            String[] SimpleTree = {"3","7","3","0","14","1","0","0"};
            String[] SequentialHeap = {"92","37","11","14","33","0","2","0"};
            String[] FineGrainedHeap = {"44","24","15","11","30","13","0","5"};
            String[] SkipQueue = {"2","6","4","2","5","1","0","0"};

            //逐列添加数据
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < 8; j++) {
                    switch (i){
                        case 0:  //记录category信息
                            sheet.addCell(new Label(i,1 + j,categories[j],wcf_center));
                            break;
                        case 1: //记录每一个分类中的变异算子
                            sheet.addCell(new Label(i,1 + j,operators[j],wcf_center));
                            break;
                        case 2: //记录每一个实验对象对应分类中的变异体个数
                            if (SUTName.equals("SimpleLinear")){
                                sheet.addCell(new Label(i,1 + j,SimpleLinear[j],wcf_center));
                                break;
                            }else if (SUTName.equals("SimpleTree")){
                                sheet.addCell(new Label(i,1 + j,SimpleTree[j],wcf_center));
                                break;
                            }else if (SUTName.equals("SequentialHeap")){
                                sheet.addCell(new Label(i,1 + j,SequentialHeap[j],wcf_center));
                                break;
                            }else if (SUTName.equals("FineGrainedHeap")){
                                sheet.addCell(new Label(i,1 + j,FineGrainedHeap[j],wcf_center));
                                break;
                            }else if (SUTName.equals("SkipQueue")){
                                sheet.addCell(new Label(i,1 + j,SkipQueue[j],wcf_center));
                                break;
                            }
                        case 3: //记录每一个分类中被杀死变异体的个数
                            sheet.addCell(new Label(i,1 + j,String.valueOf(killedrecord[j]),wcf_center));
                            break;
                        case 4: //记录每一个分类中被杀死变异体的具体编号
                                String str = "";
                                for (int l = 0; l < killedBinLists[j].size(); l++) {
                                    str += killedBinLists[j].get(l) + ",";
                                }
                                sheet.addCell(new Label(i,1 + j,str,wcf_center));
                            break;
                        case 5:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(notkilledrecord[j]),wcf_center));
                            break;
                        case 6:
                            String notstr = "";
                            for (int l = 0; l < notkilledBinLists[j].size(); l++) {
                                notstr += notkilledBinLists[j].get(l) + ",";
                            }
                            sheet.addCell(new Label(i,1 + j,notstr,wcf_center));
                            break;
                        case 7:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[0].getDateFromRecord(j)),wcf_center));
                            break;
                        case 8:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[1].getDateFromRecord(j)),wcf_center));
                            break;
                        case 9:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[2].getDateFromRecord(j)),wcf_center));
                            break;
                        case 10:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[3].getDateFromRecord(j)),wcf_center));
                            break;
                        case 11:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[4].getDateFromRecord(j)),wcf_center));
                            break;
                        case 12:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[5].getDateFromRecord(j)),wcf_center));
                            break;
                        case 13:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[6].getDateFromRecord(j)),wcf_center));
                            break;
                        case 14:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[7].getDateFromRecord(j)),wcf_center));
                            break;
                        case 15:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[8].getDateFromRecord(j)),wcf_center));
                            break;
                        case 16:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[9].getDateFromRecord(j)),wcf_center));
                            break;
                        case 17:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[10].getDateFromRecord(j)),wcf_center));
                            break;
                        case 18:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[11].getDateFromRecord(j)),wcf_center));
                            break;
                        case 19:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[26].getDateFromRecord(j)),wcf_center));
                            break;
                        case 20:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[12].getDateFromRecord(j)),wcf_center));
                            break;
                        case 21:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[13].getDateFromRecord(j)),wcf_center));
                            break;
                        case 22:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[14].getDateFromRecord(j)),wcf_center));
                            break;
                        case 23:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[27].getDateFromRecord(j)),wcf_center));
                            break;
                        case 24:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[15].getDateFromRecord(j)),wcf_center));
                            break;
                        case 25:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[16].getDateFromRecord(j)),wcf_center));
                            break;
                        case 26:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[17].getDateFromRecord(j)),wcf_center));
                            break;
                        case 27:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[18].getDateFromRecord(j)),wcf_center));
                            break;
                        case 28:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[19].getDateFromRecord(j)),wcf_center));
                            break;
                        case 29:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[20].getDateFromRecord(j)),wcf_center));
                            break;
                        case 30:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[28].getDateFromRecord(j)),wcf_center));
                            break;
                        case 31:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[21].getDateFromRecord(j)),wcf_center));
                            break;
                        case 32:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[22].getDateFromRecord(j)),wcf_center));
                            break;
                        case 33:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[23].getDateFromRecord(j)),wcf_center));
                            break;
                        case 34:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[29].getDateFromRecord(j)),wcf_center));
                            break;
                        case 35:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[24].getDateFromRecord(j)),wcf_center));
                            break;
                        case 36:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[25].getDateFromRecord(j)),wcf_center));
                            break;
                        case 37:
                            sheet.addCell(new Label(i,1 + j,String.valueOf(mrBins[30].getDateFromRecord(j)),wcf_center));
                            break;
                    }

                }
            }
            //每一个categories的信息添加完之后对每一列求和
            //求变异体数量只和
            int tatle = 0;
            if (SUTName.equals("SimpleLinear")){
                for (int i = 0; i < SimpleLinear.length; i++) {
                    tatle += Integer.valueOf(SimpleLinear[i]);
                }
            }else if (SUTName.equals("SimpleTree")){
                for (int i = 0; i < SimpleTree.length; i++) {
                    tatle += Integer.valueOf(SimpleTree[i]);
                }
            }else if (SUTName.equals("SequentialHeap")){
                for (int i = 0; i < SequentialHeap.length; i++) {
                    tatle += Integer.valueOf(SequentialHeap[i]);
                }
            }else if (SUTName.equals("FineGrainedHeap")){
                for (int i = 0; i < FineGrainedHeap.length; i++) {
                    tatle += Integer.valueOf(FineGrainedHeap[i]);
                }
            }else if (SUTName.equals("SkipQueue")){
                for (int i = 0; i < SkipQueue.length; i++) {
                    tatle += Integer.valueOf(SkipQueue[i]);
                }
            }
            //求出杀死的变异体总数
            int killednumber = 0 ;
            for (int i = 0; i < killedrecord.length; i++) {
                killednumber += killedrecord[i];
            }
            //求出没有杀死的变异体总数
            int notkillednumber = 0 ;
            for (int i = 0; i < notkilledrecord.length; i++) {
                notkillednumber += notkilledrecord[i];
            }

            String[] contents = {"总计：", "",String.valueOf(tatle),String.valueOf(killednumber),"",String.valueOf(notkillednumber),
                     "",mrBins[0].getsum(),mrBins[1].getsum(),mrBins[2].getsum(),mrBins[3].getsum(),mrBins[4].getsum(),mrBins[5].getsum(),
                    mrBins[6].getsum(),mrBins[7].getsum(),mrBins[8].getsum(),mrBins[9].getsum(),mrBins[10].getsum(),mrBins[11].getsum(),mrBins[26].getsum(),
                    mrBins[12].getsum(),mrBins[13].getsum(),mrBins[14].getsum(),mrBins[27].getsum(),mrBins[15].getsum(),mrBins[16].getsum(),
                    mrBins[17].getsum(),mrBins[18].getsum(),mrBins[19].getsum(),mrBins[20].getsum(),mrBins[28].getsum(),mrBins[21].getsum(),mrBins[22].getsum(),
                    mrBins[23].getsum(),mrBins[29].getsum(),mrBins[24].getsum(),mrBins[25].getsum(),mrBins[30].getsum(),};

            for (int i = 0; i < contents.length; i++) {
                sheet.addCell(new Label(i,9,contents[i],wcf_center));
            }
            originalWorkBook.close();
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }




    /**
     * 对两个集合中的元素进行解析，以便获取每一个category的信息
     * @param notkilledSet 没有杀死的变异体集合
     * @param killedSet 杀死的变异体的集合
     */
    private void parseSet(Set<String> notkilledSet,Set<String> killedSet){
        for (String notkilledname : notkilledSet) {
            String[] tempstr = notkilledname.split("_");
            String subnotkilledname = tempstr[0];
            if (subnotkilledname.equals("AORB") || subnotkilledname.equals("AORS")||subnotkilledname.equals("AOIU")||subnotkilledname.equals("AOIS")){
                notkilledrecord[0] += 1 ;
                notkilledBinLists[0].add(notkilledname);
            }else if (subnotkilledname.equals("ROR")){
                notkilledrecord[1] += 1 ;
                notkilledBinLists[1].add(notkilledname);
            }else if (subnotkilledname.equals("COR")||subnotkilledname.equals("COI")||subnotkilledname.equals("COD")){
                notkilledrecord[2] += 1 ;
                notkilledBinLists[2].add(notkilledname);
            }else if (subnotkilledname.equals("LOI")){
                notkilledrecord[3] += 1 ;
                notkilledBinLists[3].add(notkilledname);
            }else if (subnotkilledname.equals("SDL")||subnotkilledname.equals("VDL")||subnotkilledname.equals("CDL")||subnotkilledname.equals("ODL")){
                notkilledrecord[4] += 1 ;
                notkilledBinLists[4].add(notkilledname);
            }else if (subnotkilledname.equals("RCXC")||subnotkilledname.equals("SAN")||subnotkilledname.equals("ELPA")){
                notkilledrecord[5] += 1 ;
                notkilledBinLists[5].add(notkilledname);
            }else if (subnotkilledname.equals("ASTK")||subnotkilledname.equals("RSK")||subnotkilledname.equals("RFU")){
                notkilledrecord[6] += 1 ;
                notkilledBinLists[6].add(subnotkilledname);
            }else {
                notkilledrecord[7] += 1 ;
                notkilledBinLists[7].add(subnotkilledname);
            }
        }

        for (String killedname : killedSet) {
            String[] tempstr = killedname.split("_");
            String subkilledname = tempstr[0];
            if (subkilledname.equals("AORB") || subkilledname.equals("AORS")||subkilledname.equals("AOIU")||subkilledname.equals("AOIS")){
                killedrecord[0] += 1 ;
                killedBinLists[0].add(killedname);
            }else if (subkilledname.equals("ROR")){
                killedrecord[1] += 1 ;
                killedBinLists[1].add(killedname);
            }else if (subkilledname.equals("COR")||subkilledname.equals("COI")||subkilledname.equals("COD")){
                killedrecord[2] += 1 ;
                killedBinLists[2].add(killedname);
            }else if (subkilledname.equals("LOI")){
                killedrecord[3] += 1 ;
                killedBinLists[3].add(killedname);
            }else if (subkilledname.equals("SDL")||subkilledname.equals("VDL")||subkilledname.equals("CDL")||subkilledname.equals("ODL")){
                killedrecord[4] += 1 ;
                killedBinLists[4].add(killedname);
            }else if (subkilledname.equals("RCXC")||subkilledname.equals("SAN")||subkilledname.equals("ELPA")){
                killedrecord[5] += 1 ;
                killedBinLists[5].add(killedname);
            }else if (subkilledname.equals("ASTK")||subkilledname.equals("RSK")||subkilledname.equals("RFU")){
                killedrecord[6] += 1 ;
                killedBinLists[6].add(subkilledname);
            }else {
                killedrecord[7] += 1 ;
                killedBinLists[7].add(subkilledname);
            }
        }
    }


    /**
     * SUT测试完之后，根据日志文件中信息创建excel，并向excel文件的头部添加标题
     * */
    private void creatExcelTitle(String sutName){
        String[] titles = {"category", "operates", "变异体数量", "杀死的个数", "杀死的编号", "没有杀死的个数", "没有杀死的编号",
                "MR1", "MR2","MR3","MR4","MR5","MR6","MR7","MR8","MR9","MR10_1","MR10_2","MR10_3","MR10",
                "MR11_1","MR11_2","MR11_3","MR11","MR12","MR13","MR14","MR15","MR16_1","MR16_2","MR16",
                "MR17","MR18_1","MR18_2","MR18","MR19_1","MR19_2","MR19"};
        String path = System.getProperty("user.dir") + separator + "datafile" +
                separator + "categoryinfo_" + sutName + ".xls";
        File file = new File(path);
        try{
            //如果文件存在则删除不存在该文件则重新创建一个
            if (file.exists()){
                file.delete();
            }

            if (!file.exists()){
                file.createNewFile();
            }
            //开始创建工作簿并向里面写入数据
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
            WritableSheet sheet = writableWorkbook.createSheet("info",0);
            //设置表格的格式
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL,14);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            titleFormat.setBorder(Border.ALL,THIN);
            titleFormat.setVerticalAlignment(CENTRE); // 文字垂直对齐
            titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
            titleFormat.setWrap(false);//设置文字是否换行
            for (int i = 0; i < titles.length; i++) {
                sheet.addCell(new Label(i,0,titles[i],titleFormat));
                if (i == 1){
                    sheet.setColumnView(i, 28);
                }else {
                    sheet.setColumnView(i, 20);
                }

            }
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ParseCategory parseCategory = new ParseCategory();
        parseCategory.parseCategory("FineGrainedHeap");
    }

}
