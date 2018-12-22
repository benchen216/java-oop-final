class MyThread extends Thread {
    public String x;

    public MyThread(int x){
        // turn to string
        this.x = String.valueOf(x+1);
    }

    public void run(){
        for (int i=1;i<10000000;i++)
        System.out.println("Hello I'm " + x);
    }
}

public class Example2 {
    public static void main(String[] args){
        Thread t1 = new MyThread(1);
        Thread t2 = new MyThread(2);
        Thread t3 = new MyThread(3);
        Thread t4 = new MyThread(4);
        Thread t5 = new MyThread(5);



        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        System.out.println(((MyThread) t1).x);
    }
}