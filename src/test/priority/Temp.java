package test.priority;


public class Temp {

    /*Thread类*/
    class Task extends Thread{
        private volatile boolean isCancel = false;
        public void run(){
            while(!isCancel){
                System.out.println("我没有中断");
            }
            System.out.println("我中断了");
        }

        public void cancel(){
            this.isCancel = true;
        }
    }

    public synchronized void test(){
        Task[] thread = new Task[2];
        for (int i = 0; i < thread.length; i++) {
            thread[i] = new Task();
        }

        for (int i = 0; i < thread.length; i++) {
            thread[i].start();
        }
        for (int i = 0; i < thread.length; i++) {
            try{
                thread[i].join(3000);
                System.out.println();
                if (thread[i].isAlive()){
                    for (int j = 0; j < thread.length; j++) {
                        thread[j].cancel();
                    }
                }
            } catch (InterruptedException e) {
            }
        }




    }



    public static void main(String[] args) {
        Temp temp = new Temp();
        temp.test();

    }



}
