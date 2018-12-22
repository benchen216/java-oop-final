class MyThread extends Thread {
    public void run(){
        System.out.println("Hello World in MyThread");
    }
}

public class Example1 {
    public static void main(String[] args){
        Thread t1 = new MyThread();   // 建一個新的 Thread t1
        t1.start();                   // 讓 t1 開始執行
        System.out.println("Hello World in main Thread");
    }
}
