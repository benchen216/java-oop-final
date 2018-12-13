import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class Main {
    static int count=0;
    public static void main(String [] argv) throws IOException {
        FileReader fr = new FileReader("c6288.bench.txt");
        BufferedReader br = new BufferedReader(fr);
        String myline = "";
        Matcher m;
        Stack<String>myOutput = new Stack<String>();
        HashSet<String> myInput = new HashSet<String>();
        HashMap <String,String[]> gate = new HashMap<String, String[]>();

        //
        LinkedList<MyBench> myBenches = new LinkedList<MyBench>();


        while ((myline=br.readLine())!=null) {
            m=Pattern.compile("^#").matcher(myline);
            if(m.find())continue;
            m=Pattern.compile("^INPUT\\((.+)\\)").matcher(myline);
            if(m.find()){
                System.out.println("input "+m.group(1));
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
            count=0;
            //for (String t:gate.get(myOutput.pop())) System.out.println(t);
            System.out.println("\n"+"iii");
            mynode(myOutput.pop(),gate,myInput,myBenches);

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
    static class MyBench{
        String output;
        String doaction;
        String[] input;
        MyBench(String output, String doaction, String[] input){

        }
    }
    static void mynode(String mygate,HashMap gate,HashSet myInput,LinkedList myBenches){
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
        String [] test={"s","s"};
        //myBenches.add(new MyBench("test","test",test));
        for (int i=1;i<tem.length;i++){
            //System.out.print(tem[i]);
            //System.out.print("---"+tem[i]+"["+count+"]");
            mynode(tem[i],gate,myInput,myBenches);
        }
        //@System.out.print(")");
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


class dosome{

}