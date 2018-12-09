import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String [] argv) throws IOException {
        FileReader fr = new FileReader("c1908.bench.txt");
        BufferedReader br = new BufferedReader(fr);
        String myline = "";
        Matcher m;
        Stack<String>myOutput = new Stack<String>();
        HashSet<String> myInput = new HashSet<String>();
        HashMap <String,String[]> gate = new HashMap<String, String[]>();

        while ((myline=br.readLine())!=null) {
            m=Pattern.compile("^#").matcher(myline);
            if(m.find())continue;
            m=Pattern.compile("^INPUT\\((.+)\\)").matcher(myline);
            if(m.find()){
                myInput.add(m.group(1));
                //System.out.println("input "+myOutput.get(myInput.indexOf(m.group(1))));
                continue;
            }
            m=Pattern.compile("^OUTPUT\\((.+)\\)").matcher(myline);
            if(m.find()){
                myOutput.add(m.group(1));
                System.out.println("output "+m.group(1));
                continue;
            }
            m=Pattern.compile("(.+)\\s\\=\\s(.+)\\((.+)\\)").matcher(myline);
            if(m.find()) {
                String []tem=m.group(3).split(", ");
                String[] my = new String[tem.length+1];
                my[0]=m.group(2);
                gate.put(m.group(1),my);
                System.out.print(m.group(1)+"["+m.group(2)+"] ");
                for (int i = 1;i<=m.group(3).split(", ").length;i++){
                    my[i]=tem[i-1];
                    System.out.print(tem[i-1]+" ");
                }
                System.out.println();
            }
        }
        fr.close();
        while (!myOutput.isEmpty()){
            //for (String t:gate.get(myOutput.pop())) System.out.println(t);
            System.out.println("\n"+"iii");
            mynode(myOutput.pop(),gate,myInput);

        }


        //for(String s : myInput) System.out.print(s + " ");
        /*Iterator iterator =  myInput.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next()+" ");
        }

        System.out.println();
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
    static void mynode(String mygate,HashMap gate,HashSet myInput){
        System.out.print("---"+mygate);
        if(myInput.contains(mygate)){
            //System.out.print("xxx");
            return;
        }
        String[] tem= (String[]) gate.get(mygate);
        System.out.println(tem[0]);
        for (int i=1;i<tem.length;i++){
            //System.out.print("---"+tem[i]);
            mynode(tem[i],gate,myInput);
        }

    }
}
class Bench{
    /*public static void read() throws IOException{
        FileReader fr = new FileReader("c17.bench.txt");
        BufferedReader br = new BufferedReader(fr);
        String myline = "";
        Matcher m;
        while ((myline=br.readLine())!=null) {
            m=Pattern.compile("^#").matcher(myline);
            if(m.find())continue;
            m=Pattern.compile("^INPUT.+(\\d+).").matcher(myline);
            if(m.find()) System.out.println("input"+m.group(1));
            m=Pattern.compile("^OUTPUT.+(\\d+).").matcher(myline);
            if(m.find()) System.out.println("output"+m.group(1));
            m=Pattern.compile(".+(\\d+).+\\=.([a-zA-Z]+).+(\\d+).+(\\d+).+").matcher(myline);
            if(m.find()) System.out.println("ge "+m.group(1)+" gate "+m.group(2)+" g1 "+m.group(3)+" g2 "+m.group(4));
        }
        fr.close();
    }*/
}
class myBench{
    myBench(){

    }
}
class gates{
    static int xor(int x,int y){
        return x^y;
    }
    static int and(int[]myinput){
        int z=1;
        for (int i:myinput)z=z&i;
        return z;
    }
    static int or(int[]myinput){
        int z=0;
        for (int i:myinput)z=z&i;
        return z;
    }
    static int nand(int[]myinput){
        return not(and(myinput));
    }
    static int not(int x){
        if(x==0)return 1;
        return 0;
    }
    static int nor(int[]myinput){
        return not(or(myinput));
    }
    static int buf(int x){
        return x;
    }
}