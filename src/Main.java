import java.io.*;
import java.util.*;
import java.util.regex.*;
public class Main {
    static int count=0;
    public static void main(String [] argv) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        long end ;
         BufferedReader bench_br = new BufferedReader(new FileReader("c432.bench.txt"));
        PrintWriter pw=new PrintWriter(new FileWriter("c432_10m_op.txt"));
        BufferedReader br1 = new BufferedReader(new FileReader("c432_10m_ip.txt"));
        String myline = "";
        Matcher m;
        Stack<String>myOutput = new Stack<>();
        ArrayList<String> myInput = new ArrayList<>();
        HashMap <String,String[]> gate = new HashMap<>();
        while ((myline=bench_br.readLine())!=null) {
            m=Pattern.compile("^#").matcher(myline);
            if(m.find())continue;
            m=Pattern.compile("^INPUT\\((.+)\\)").matcher(myline);
            if(m.find()){
                myInput.add(m.group(1));
                continue;
            }
            m=Pattern.compile("^OUTPUT\\((.+)\\)").matcher(myline);
            if(m.find()){
                myOutput.add(m.group(1));
                continue;
            }
            m=Pattern.compile("(.+)\\s\\=\\s(.+)\\((.+)\\)").matcher(myline);
            if(m.find()) {
                String []tem=m.group(3).split(", ");
                String[] my = new String[tem.length+1];
                my[0]=m.group(2);
                gate.put(m.group(1),my);
                for (int i = 1;i<=m.group(3).split(", ").length;i++){
                    my[i]=tem[i-1];
                }
            }
        }
        bench_br.close();
        ArrayList<String> myInput2 = new ArrayList<>(myInput);


        //use
        Iterator iterator4 =  myOutput.iterator();
        while (iterator4.hasNext()){
            count=0;
            mynode((String) iterator4.next(),gate,myInput2);
        }
        String myline2 ="";
        ArrayList<String> myin = new ArrayList<>();

        while ((myline2=br1.readLine())!=null){
            myin.add(myline2);
        }

        String[] myout = new String[myin.size()];
        for (String t :myInput) {
            myInput2.remove(t);
        }

        int tem = (int)myin.size()/4;
        Thread t1=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem,0);
        Thread t2=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem, tem);
        Thread t3=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem,tem*2);
        Thread t4=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem,tem*3);
        t1.start();
        t2.start();
        t3.start();
        t4.start();


        /*int threadNo= 4;
        Thread threads[] = new Thread[threadNo];
        for (int i=0;i<threadNo;i++){
            threads = new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem,tem*i);
        }

        for (Thread i:threads){
            i.join();
        }*/



            t1.join();


            t2.join();

            t3.join();

            t4.join();


        for (int k=0;k<myin.size();++k){
            pw.println(myin.get(k)+" "+myout[k]);
        }

        pw.close();

        end = System.currentTimeMillis();
        long useTime = end - start;
        System.out.printf("Total time=%.3f sec(s)\n",(end - start)/1000.0);



    }

    static void mynode(String mygate,HashMap gate,ArrayList<String> myInput){
        count++;
        if(myInput.contains(mygate)){
            return;
        }
        String[] tem= (String[]) gate.get(mygate);
        for (int i=1;i<tem.length;i++){
            mynode(tem[i],gate,myInput);
        }
        myInput.add(mygate);
    }


}
class myTread2 extends Thread{
    HashMap<String,Integer> result = new HashMap<>();
 HashMap<String,Integer> runInput= new HashMap<>();
 String out ="";
     ArrayList<String> myInput2;
     ArrayList<String> myin;
     ArrayList<String> myInput;
    HashMap<String, String[]> gate;
     Stack<String> myOutput;
     int count, end;
     String[] myout;
    StringBuffer sBuffer = new StringBuffer("");
    myTread2(ArrayList<String> myInput2, ArrayList<String> myin, ArrayList<String> myInput, HashMap<String, String[]> gate, Stack<String> myOutput, String[] myout, int count, int end){
        this.myInput2 = myInput2;
        this.myin = myin;
        this.myInput = myInput;
        this.gate = gate;
        this.myOutput = myOutput;
        this.count = count;
        this.end = end;
        this.myout = myout;
    }
    @Override
    public void run(){
        for (int j = this.end; j<this.count +this.end; j++){
            for (int z=0;z<myin.get(j).length();z++){
                this.runInput.put(this.myInput.get(z), Character.getNumericValue(this.myin.get(j).charAt(z)));
            }
            for (String t :myInput) {
                RunNode.inputtoresult(t, runInput, result);
            }
            for (String t:myInput2){
                RunNode.mynode2(t, gate, result);
            }
            for (String k: myOutput){
                sBuffer.append(result.get(k));
            }
            myout[j]=sBuffer.toString();
            sBuffer=new StringBuffer("");
            runInput.clear();
            result.clear();
        }
    }
}
