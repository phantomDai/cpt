package test.priority;

import generatelist.Generatedata;
import generatelist.QuickSort;

import org.junit.Before;
import org.junit.Test;
import priority.FineGraineHeap;
import set.mutants.MutantSet;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FineGrainedHeap {
    private static final int RANGE = 1024;
    private int THREADS = 10;
    Object mutantInstance ; //变异体对象
    private static final String METHODNAME_ADD = "add" ;//add方法
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法
    private Class clazz = null;//引用待测的对象
    private Constructor constructor = null ;
    private volatile int addToVector = 0;
    RemoveMinThread[] thread;
    Vector<Integer> vector = new Vector<Integer>() ;
    Random ran = new Random();
    Method method_add;
    Method method_remove;

    public FineGrainedHeap(int mythreads){
        THREADS = mythreads;
        thread = new RemoveMinThread[mythreads];
    }


    /**
     *
     * Method: removeMin()
     *
     */
    public void testRemoveMin(int[] list,String mymutantFullName){
        String[] names = mymutantFullName.split("\\.");
        String tempname = names[2];
        if ((tempname.equals("ELPA_1")) || ((tempname.equals("ELPA_2")))){
            for (int i = 0; i < THREADS; i++) {
                vector.add(ran.nextInt(824));
            }
        }else {
            try{
                vector.clear();
                clazz = Class.forName(mymutantFullName);
                constructor = clazz.getConstructor(int.class) ;
                if (list.length<1024){
                    mutantInstance = constructor.newInstance(1024);
                }else {
                    mutantInstance = constructor.newInstance(list.length);
                }

                method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
                //向变异体对象中添加元素
                method_remove = clazz.getMethod(METHODNAME_REMOVEMIN,null);
                for (int i = 0; i < list.length; i++) {
                    method_add.invoke(mutantInstance,list[i],list[i]);
                }
                //多线程移除优先级最高的十个元素
                for (int i = 0; i < THREADS; i++) {
                    thread[i] = new RemoveMinThread();
                }
                for (int i = 0; i < THREADS; i++) {
                    thread[i].start();
                }
                for (int i = 0; i < THREADS; i++) {
                    try{
                        thread[i].join(1000);//设置超时时间为10s
                        if (thread[i].isAlive()){
                            for (int k = 0; k < THREADS; k++) {
                                thread[k].cancel();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }



    }

    public int[] getTop() {
        Collections.sort(vector); //对向量中的元素排序
        int[] templist = new int[THREADS];
        if (vector.size() < THREADS){
            for (int i = vector.size(); i < THREADS ; i++) {
                vector.add(ran.nextInt(824));
            }
        }
        for (int i = 0; i < templist.length; i++) {
            templist[i] = vector.get(i);
        }
        return templist;
    }


    private synchronized boolean addElements(Object element){
        if (element == null){
            Random random = new Random();
            vector.add(random.nextInt(824));
            return true;
        }else {
            int temp = (int) element;
            if (vector.contains(temp))
                return false;
            else {
                vector.add(temp);
                return true;
            }
        }
    }



    class RemoveMinThread extends Thread {
        volatile boolean isCancel = false;
        public void run() {
            while(!isCancel){
                try{
                    Object result = method_remove.invoke(mutantInstance,null);
                    isCancel = addElements(result);
                }catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        public void cancel(){
            this.isCancel = true;
        }
    }


}
