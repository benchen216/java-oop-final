import java.io.*;
import java.util.*;
import java.util.regex.*;
//import com.sun.management.OperatingSystemMXBean;
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
                //System.out.println("input "+m.group(1));
                myInput.add(m.group(1));
                //System.out.println("input "+myOutput.get(myInput.indexOf(m.group(1))));
                continue;
            }
            m=Pattern.compile("^OUTPUT\\((.+)\\)").matcher(myline);
            if(m.find()){
                myOutput.add(m.group(1));
                //System.out.println("output "+m.group(1));
                continue;
            }
            m=Pattern.compile("(.+)\\s\\=\\s(.+)\\((.+)\\)").matcher(myline);
            if(m.find()) {
                String []tem=m.group(3).split(", ");
                String[] my = new String[tem.length+1];
                my[0]=m.group(2);
                gate.put(m.group(1),my);
                //System.out.print(m.group(1)+"["+m.group(2)+"] ");
                for (int i = 1;i<=m.group(3).split(", ").length;i++){
                    my[i]=tem[i-1];
                    //System.out.print(count[end-1]+" ");
                }
                //System.out.println();
            }
        }
        bench_br.close();
        ArrayList<String> myInput2 = new ArrayList<>(myInput);


        //use
        Iterator iterator4 =  myOutput.iterator();
        while (iterator4.hasNext()){
            count=0;
            //for (String t:gate.get(myOutput.pop())) System.out.println(t);
            //System.out.println("\n"+"iii");
            mynode((String) iterator4.next(),gate,myInput2);




            //end = System.currentTimeMillis();
            //System.out.println(end - start);
        }
        System.out.println();
        //for(String s : myInput) System.out.print(s + " ");
        /*Iterator iterator =  unorder.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next()+" ");
        }*/

        System.out.println();

System.out.println("xxxxxx");

        end = System.currentTimeMillis();
        System.out.println(end - start);

        //count

        String myline2 ="";
        ArrayList<String> myin = new ArrayList<>();

        while ((myline2=br1.readLine())!=null){
            myin.add(myline2);
        }
        /*System.out.println("iiiii");
        end = System.currentTimeMillis();
        System.out.println(end - start);*/
        //int [][]result1 = new int[myin.size()][myOutput.size()];
        String[] myout = new String[myin.size()];
        for (String t :myInput) {
            myInput2.remove(t);
        }

        int tem = (int)myin.size()/4;
        Thread t1=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem,0);
        Thread t2=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem, tem);
        Thread t3=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem,tem*2);
        Thread t4=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,tem,tem*3);
       // Thread t5=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,count,count*4);
        //Thread t6=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,count,count*5);
        //Thread t7=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,count,count*6);
       // Thread t8=new myTread2(myInput2,myin,myInput,gate,myOutput,myout,count,count*7);

        //t8.count();

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
        //for(int end = myin.size()-1 ; end>myin.size()-11;end--) System.out.println(myout[end]);
        pw.close();
        //fw.close();


       /*while ((myline2=br1.readLine())!=null){
            for (int end=0;end<myline2.length();end++){
                runInput.put(myInput.get(end), Character.getNumericValue(myline2.charAt(end)));
            }
           for (String t :myInput) {
               RunNode.inputtoresult(t, runInput, result);
           }
            for (String tem2 : myInput2) {
                //System.out.print(iterator2.next()+" ");
                RunNode.mynode2(tem2, gate, result);
            }
            //bw.write(myline2);
            pw.print(myline2);
           //bw.write(" ");
           pw.print(" ");
            for (String s : myOutput) {
                //System.out.print(result.get(iterator6.next()));
                pw.print(result.get(s));
                //bw.write(result.get(s));
            }
            //System.out.println();
           //bw.newLine();
            pw.println();

            runInput.clear();
            result.clear();
        }
        pw.close();
       //bw.close();
       fw.close();*/


        end = System.currentTimeMillis();
        long useTime = end - start;
        System.out.printf("Total time=%.3f sec(s)\n",(end - start)/1000.0);







        /*System.out.println();
        Iterator iter = gate.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            System.out.print(key);
            for (String t:val)System.out.print(" "+t+" ");
            System.out.println();
        }*/

    }

    static void mynode(String mygate,HashMap gate,ArrayList<String> myInput){
        //if(unorder.contains(mygate))return;

        //System.out.print("---"+mygate+"["+count+"]");
        //@System.out.print("["+count+"]");
        count++;
        if(myInput.contains(mygate)){
            //System.out.print("xxx");
            //@System.out.print(mygate);
            return;
        }
        String[] tem= (String[]) gate.get(mygate);
        //@System.out.print(count[0]+"(");
        //String [] test={"s","s"};
        //myBenches.add(new MyBench("test","test",test));
        for (int i=1;i<tem.length;i++){
            //System.out.print(count[end]);
            //
            //System.out.print("---"+count[end]+"["+count+"]");
            mynode(tem[i],gate,myInput);

        }
        myInput.add(mygate);
        //@System.out.print(")");
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
            //System.out.println(myin.get(j));
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
                //this.out=this.out+result.get(k).toString();
            }
            //System.out.println(result);
            myout[j]=sBuffer.toString();
            //System.out.println(myout[j]);
            //this.out;
            //this.out="";
            sBuffer=new StringBuffer("");
            runInput.clear();
            result.clear();
        }
    }
}
