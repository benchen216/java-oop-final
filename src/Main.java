import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.regex.*;
public class Main {

    static gateinter and = new myand();
    static gateinter or = new myor();
    static gateinter xor = new myxor();
    static gateinter not = new mynot();
    static gateinter buf = new mybuf();
    static gateinter nor = new mynor();
    static gateinter nand = new mynand();
    public static void main(String [] argv) throws IOException, InterruptedException {
        //String[] bench={"C432","C499","C880","C1355","C1908","C2670","C3540","C5315","C6288","C7552"};
        //String[] inN = {"1k","10k","100k"};
        //for(String b:bench) {
        //    for (String kc : inN) {
        //BufferedReader bench_br = new BufferedReader(new FileReader(b + ".bench.txt"));
        //PrintWriter pw = new PrintWriter(new FileWriter(b + "_"+kc+"_op.txt"));
        //BufferedReader br1 = new BufferedReader(new FileReader(b + "_"+kc+"_ip.txt"));
                long start = System.currentTimeMillis();
                long end;


        BufferedReader bench_br = new BufferedReader(new FileReader("c432.bench.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("c432_10m_op.txt"));
        BufferedReader br1 = new BufferedReader(new FileReader("c432_10m_ip.txt"));
                String myline = "";
                Matcher m;

                Stack<String> myOutput = new Stack<>();
                ArrayList<String> myInput = new ArrayList<>();
                HashMap<String, String[]> gate = new HashMap<>();
                HashMap<String, gateinter> gate2 = new HashMap<>();
                while ((myline = bench_br.readLine()) != null) {
                    m = Pattern.compile("^#").matcher(myline);
                    if (m.find()) continue;
                    m = Pattern.compile("^INPUT\\((.+)\\)").matcher(myline);
                    if (m.find()) {
                        myInput.add(m.group(1));
                        continue;
                    }
                    m = Pattern.compile("^OUTPUT\\((.+)\\)").matcher(myline);
                    if (m.find()) {
                        myOutput.add(m.group(1));
                        continue;
                    }
                    m = Pattern.compile("(.+)\\s\\=\\s(.+)\\((.+)\\)").matcher(myline);
                    if (m.find()) {
                        String[] tem = m.group(3).split(", ");
                        String[] my = new String[tem.length + 1];
                        my[0] = m.group(2);
                        gate.put(m.group(1), my);
                        for (int i = 1; i <= m.group(3).split(", ").length; i++) {
                            my[i] = tem[i - 1];
                        }
                    }
                }
                bench_br.close();
                ArrayList<String> myInput2 = new ArrayList<>(myInput);

                //use
                Iterator iterator4 = myOutput.iterator();
                while (iterator4.hasNext()) {
                    mynode((String) iterator4.next(), gate, myInput2);
                }
                String myline2 = "";
                ArrayList<String> myin = new ArrayList<>();

                while ((myline2 = br1.readLine()) != null) {
                    myin.add(myline2);
                }

                String[] myout = new String[myin.size()];
                for (String t : myInput) {
                    myInput2.remove(t);
                }

        for(String t:gate.keySet()){
            String c = gate.get(t)[0];
            switch (c) {
                case "nand":
                    gate2.put(t,nand);
                    break;
                case "or":
                    gate2.put(t,or);
                    break;
                case "nor":
                    gate2.put(t,nor);
                    break;
                case "and":
                    gate2.put(t,and);
                    break;
                case "xor":
                    gate2.put(t,xor);
                    break;
                case "not":
                    gate2.put(t,not);
                    break;
                case "buf":
                    gate2.put(t,buf);
                    break;
                default:
                    break;
            }

           // System.out.println(Arrays.toString(gate2.get(t)));
        }
                /*
                HashMap<String,Object[]> gate2 = new HashMap<>();
                for(String t:gate.keySet()){
                    gate2.put(t,)
                }


*/

        //Object t = mynode3;
                int threadNo = 1;
                int tem = myin.size() / threadNo;
                Thread[] threads = new Thread[threadNo];
                for (int i = 0; i < threadNo; i++) {
                    threads[i] = new myTread2(myInput2, myin, myInput, gate, myOutput, myout, tem, tem * i,gate2);
                    threads[i].start();
                }
                for (Thread i : threads) {
                    i.join();
                }

                for (int k = 0; k < myin.size(); ++k) {
                    pw.println(myin.get(k) + " " + myout[k]);
                }

                pw.close();

                end = System.currentTimeMillis();
                System.out.printf("Total time=%.3f sec(s)\n", (end - start) / 1000.0);
                //System.out.print((end - start) / 1000.0);
                //System.out.print(",");
           // }
         //   System.out.println(b);
       // }

    }

    static void mynode(String mygate,HashMap gate,ArrayList<String> myInput){
        if(myInput.contains(mygate)){
            return;
        }
        String[] tem= (String[]) gate.get(mygate);
        for (int i=1;i<tem.length;i++){
            mynode(tem[i],gate,myInput);
        }
        myInput.add(mygate);
    }
    static void mynode3(String tem){

    }
}
class myTread2 extends Thread{

    private HashMap<String,Integer> result = new HashMap<>();
 private HashMap<String,Integer> runInput= new HashMap<>();
     ArrayList<String> myInput2;
     ArrayList<String> myin;
     ArrayList<String> myInput;
    HashMap<String, String[]> gate;
     Stack<String> myOutput;
     int count, end;
    HashMap<String,gateinter> gate2;
     String[] myout;
    StringBuffer sBuffer = new StringBuffer();
    myTread2(ArrayList<String> myInput2, ArrayList<String> myin, ArrayList<String> myInput, HashMap<String,
            String[]> gate, Stack<String> myOutput, String[] myout, int count, int end,HashMap<String,gateinter> gate2){
        this.myInput2 = myInput2;
        this.myin = myin;
        this.myInput = myInput;
        this.gate = gate;
        this.myOutput = myOutput;
        this.count = count;
        this.end = end;
        this.myout = myout;
       this.gate2 = gate2;
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
                RunNode.mynode2(t, gate, result,gate2);
            }
            for (String k: myOutput){
                sBuffer.append(result.get(k));
            }
            myout[j]=sBuffer.toString();
            sBuffer=new StringBuffer();
            runInput.clear();
            result.clear();
        }
    }
}
class  myor implements gateinter{
    public int dogate(int[] x) {
        for (int i:x){
            if(i==1)return 1;
        }
        return 0;
    }
}
class myand implements gateinter{

    public int dogate(int[] x) {
        for (int i:x){
            if(i==0)return 0;
        }
        return 1;
    }
}
class myxor implements gateinter{
    public int dogate(int[] x) {
        return x[0]^x[1];
    }
}
class mynot implements gateinter{
    public int dogate(int[] x) {
        return x[0]==0?1:0;
    }
}
class mynand implements gateinter{
    public int dogate(int[] x) {
        for (int i:x){
            if(i==0)return 1;
        }
return 0;
    }
}
class mybuf implements gateinter{

    public int dogate(int[] x) {
        return x[0];
    }
}
class mynor implements gateinter{

    public int dogate(int[] x) {
        for (int i:x){
            if(i==1)return 0;
        }
return 1;
    }
}

