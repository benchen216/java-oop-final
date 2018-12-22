import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    static int count=0;
    public static void main(String [] argv) throws IOException {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        FileReader fr = new FileReader("c432.bench.txt");
        BufferedReader br = new BufferedReader(fr);
        String myline = "";
        Matcher m;
        Stack<String>myOutput = new Stack<String>();
        ArrayList<String> myInput = new ArrayList<>();
        HashMap <String,String[]> gate = new HashMap<String, String[]>();
        Stack<String> unorder = new Stack<String>();
        ArrayList<String> mytest = new ArrayList<String>();
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
            mynode((String) iterator4.next(),gate,myInput2,unorder);

            while (!unorder.isEmpty()){
                String tem = unorder.pop();
                if(!mytest.contains(tem)) {mytest.add(tem);
                if(!myInput2.contains(tem)){
                    myInput2.add(tem);
                }
                    //System.out.println(tem);
                }
            }


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
        /*while (!unorder.isEmpty()){
            String tem = unorder.pop();
            if(!mytest.contains(tem)) {mytest.add(tem);
            //System.out.println(tem);
                }
        }*/
System.out.println("xxxxxx");

        end = System.currentTimeMillis();
        System.out.println(end - start);

        //start
        FileWriter fw=new FileWriter("c432_1m_ans2.txt");
        PrintWriter pw=new PrintWriter(fw);
        FileReader frIp = new FileReader("c432_1m_ip.txt");
        BufferedReader br1 = new BufferedReader(frIp);
        String myline2 = "";
        while ((myline2=br1.readLine())!=null){
            for (int i=0;i<myline2.length();i++){
                runInput.put(myInput.get(i), Character.getNumericValue(myline2.charAt(i)));
            }
            //m=Pattern.compile("").matcher(myline2);

            Iterator iterator2 = myInput2.iterator();
            while (iterator2.hasNext()){
                //System.out.print(iterator2.next()+" ");
                String tem2 =(String) iterator2.next();
                if(myInput.contains(tem2))inputtoresult(tem2,runInput,result);
                else mynode2(tem2,gate,result);
            }
pw.print(myline2);
            pw.print(" ");
            Iterator iterator6 = myOutput.iterator();
            while (iterator6.hasNext()){
                //System.out.print(result.get(iterator6.next()));
                pw.print(result.get(iterator6.next()));
            }
            //System.out.println();
            pw.println();

            runInput.clear();
            result.clear();
        }
        pw.close();
        fw.close();



        long useTime = end - start;
        System.out.println(end - start);







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

    static void mynode(String mygate,HashMap gate,ArrayList myInput,Stack unorder){
        //if(unorder.contains(mygate))return;
        unorder.push(mygate);
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
            mynode(tem[i],gate,myInput,unorder);

        }
        myInput.add(mygate);
        //@System.out.print(")");
    }
    static void inputtoresult(String mytest,HashMap runInput,HashMap result){
        result.put(mytest,runInput.get(mytest));
    }
    static void mynode2(String mytest,HashMap gate,HashMap result){
        //@System.out.print("["+count+"]");

        if(result.containsKey(mytest)){
            return;
        }
        String[] tem= (String[]) gate.get(mytest);
        int[] tem2 = new int[tem.length-1];
        //tem[1]
        for (int i=1;i<tem.length;i++){
            tem2[i-1]= (int) result.get(tem[i]);
        }

        switch (tem[0]) {
            case "nand":
                result.put(mytest,gates.nand(tem2));
                break;
            case "or":
                result.put(mytest,gates.or(tem2));
                break;
            case "nor":
                result.put(mytest,gates.nor(tem2));
                break;
            case "and":
                result.put(mytest,gates.and(tem2));
                break;
            case "xor":
                result.put(mytest,gates.xor(tem2[0],tem2[1]));
                break;
            case "not":
                result.put(mytest,gates.not(tem2[0]));
                break;
            case "buf":
                result.put(mytest,gates.buf(tem2[0]));
                break;
            default:
                break;
        }
    }
}
