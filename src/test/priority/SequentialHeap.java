package test.priority;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author mph
 */
public class SequentialHeap {
    private final static int TEST_SIZE = 1024;
    private int THREADS=10;
    Vector<Integer> vector = new Vector<Integer>();//存放每次线程执行的结果
    Object mutantInstance ; //变异体对象
    private static final String METHODNAME_ADD = "add" ;//add方法
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法
    private Class clazz = null;//引用待测的对象
    private Constructor constructor = null ;
    Thread[] thread ;
    Method method_add;
    Method method_remove;

    public SequentialHeap(int mythreads){
        this.THREADS = mythreads;
        thread = new Thread[mythreads];
    }

    public void sequenceTestRemoveMin(int[] list,String mymutantFullName){
        try{
            vector.clear();
            clazz = Class.forName(mymutantFullName);
            constructor = clazz.getConstructor(int.class) ;

            mutantInstance = constructor.newInstance(list.length+1024);
            method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
            //向变异体对象中添加元素

            for (int i = 0; i < list.length; i++) {
                method_add.invoke(mutantInstance,list[i],list[i]);
            }
            //单线程移除优先级最高的十个元素
            /*初始化移除元素的方法*/
            method_remove = clazz.getMethod(METHODNAME_REMOVEMIN,null);
            for (int i = 0; i < THREADS; i++) {
                boolean flag = false;//标志位
                while(!flag){
                    try{
                        Object result = method_remove.invoke(mutantInstance,null);
                        flag = addElements(result);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
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



    /**
     *
     * Method: removeMin()
     *
     */
    public void testRemoveMin(int[] list,String mymutantFullName){
        try{
            vector.clear();
            clazz = Class.forName(mymutantFullName);
            constructor = clazz.getConstructor(int.class) ;

            mutantInstance = constructor.newInstance(list.length+1024);
            method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
            //向变异体对象中添加元素

            for (int i = 0; i < list.length; i++) {
                method_add.invoke(mutantInstance,list[i],list[i]);
            }
            //多线程移除优先级最高的十个元素
            /*初始化移除元素的方法*/
            method_remove = clazz.getMethod(METHODNAME_REMOVEMIN,null);
            for (int i = 0; i < THREADS; i++) {
                thread[i] = new RemoveMinThread();
            }
            for (int i = 0; i < THREADS; i++) {
                thread[i].start();
            }
            for (int i = 0; i < THREADS; i++) {
                try{
                    thread[i].join();
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

    public int[] getTop() {
        Collections.sort(vector); //对向量中的元素排序
        int[] templist = new int[vector.size()];
        for (int i = 0; i < templist.length; i++) {
            templist[i] = vector.get(i);
        }
        return templist;
    }


    /*向vector中添加元素,同一时间只能有一个线程访问*/
    synchronized private boolean addElements(Object element){
        if (element == null){
            Random random = new Random();
            vector.add(random.nextInt(824));
            return true;
        }else {
            int temp = (int) element;
            if (vector.contains(temp))
                return false;
            else{
                vector.add(temp);
                return true;
            }
        }
    }

    class RemoveMinThread extends Thread{
        volatile boolean flag = false;//标志位
        public void run(){
            while(!flag){
                try{
                    Object result = method_remove.invoke(mutantInstance,null);
                    flag = addElements(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

