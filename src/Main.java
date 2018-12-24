import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    static int count=0;
    public static void main(String [] argv) throws IOException {
        long start = System.currentTimeMillis();
        long end ;
        FileReader fr = new FileReader("c432.bench.txt");
         BufferedReader br = new BufferedReader(fr);
        FileWriter fw=new FileWriter("c432_1m_op.txt");
        PrintWriter pw=new PrintWriter(fw);
        //BufferedWriter bw = new BufferedWriter(fw);
        FileReader frIp = new FileReader("c432_1m_ip.txt");
        BufferedReader br1 = new BufferedReader(frIp);
        String myline = "";
        Matcher m;
        Stack<String>myOutput = new Stack<>();

        //hashcode srt testing

        ArrayList<String> myInput = new ArrayList<>();
        HashMap <String,String[]> gate = new HashMap<>();
        HashMap<String,Integer> result = new HashMap<>();
        HashMap<String,Integer> runInput= new HashMap<>();
        while ((myline=br.readLine())!=null) {
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
                    //System.out.print(tem[i-1]+" ");
                }
                //System.out.println();
            }
        }
        fr.close();
        ArrayList<String> myInput2 = new ArrayList<>(myInput);


        //use
        Iterator iterator4 =  myOutput.iterator();
        while (iterator4.hasNext()){
            count=0;
            //for (String t:gate.get(myOutput.pop())) System.out.println(t);
            //System.out.println("\n"+"iii");
            mynode((String) iterator4.next(),gate,myInput2);




            end = System.currentTimeMillis();
            System.out.println(end - start);
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

        //start

        String myline2 = "";
        ArrayList<String> myin = new ArrayList<>();
/*
        while ((myline2=br1.readLine())!=null){
            myin.add(myline2);
        }
        //int [][]result1 = new int[myin.size()][myOutput.size()];
        String[] myout = new String[myin.size()];
        for(int i=0;i<myin.size();i++){
            Thread t1=new myTread(myInput2,myin.get(i),myInput,gate,myOutput,myout,i);
            t1.start();

            /*try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(((myTread) t1).out);
        }
        for (int k=0;k<myin.size();k++){
            pw.println(myin.get(k)+" "+myout[k]);
        }
        pw.close();
        fw.close();*/
        for (String t :myInput) {
            myInput2.remove(t);
        }
       while ((myline2=br1.readLine())!=null){
            for (int i=0;i<myline2.length();i++){
                runInput.put(myInput.get(i), Character.getNumericValue(myline2.charAt(i)));
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
       fw.close();


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
        //@System.out.print(tem[0]+"(");
        //String [] test={"s","s"};
        //myBenches.add(new MyBench("test","test",test));
        for (int i=1;i<tem.length;i++){
            //System.out.print(tem[i]);
            //
            //System.out.print("---"+tem[i]+"["+count+"]");
            mynode(tem[i],gate,myInput);

        }
        myInput.add(mygate);
        //@System.out.print(")");
    }


}
