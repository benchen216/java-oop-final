class MyThread extends Thread {
    public String x;

    public MyThread(int x){
        // turn to string
        this.x = String.valueOf(x);
    }

    public void run(){
        System.out.println("Hello I'm " + x);
    }
}

public class Example2 {
    public static void main(String[] args){
        for (int i=1;i<6;i++){

        Thread t1 = new MyThread(i);



        t1.start();

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(((MyThread) t1).x);
        }
    }
}