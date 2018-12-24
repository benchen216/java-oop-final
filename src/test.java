import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class test {
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


class DoSome {
    int flag;
    String t;
    ArrayList<String> t2 = new ArrayList<>();
    DoSome(String t){
        //this.flag=flag;
        this.t=t;
    }
    DoSome add(String t2){
        this.t2.add(t2);
        return this;
    }
    void buid(){
        for (String a:t2) {
            System.out.print(a);
        }
    }
}
class MyBench{
    String output;
    String doaction;
    String[] input;
    MyBench(String output, String doaction, String[] input){

    }
}

class myTread extends Thread{
    public HashMap<String,Integer> result = new HashMap<>();
    public String out ="";
    String[] myout;
    Stack<String> myoutput;
    ArrayList<String> myInput2;
    HashMap <String,Integer>runInput = new HashMap<>();
    ArrayList<String> myInput;
    HashMap<String,String[]> gate ;
    int j;
    myTread(ArrayList myinput2, String myline2, ArrayList myInput, HashMap gate,Stack myoutput,String[] myout,int j){
        this.myInput2 = myinput2;
        this.myoutput = myoutput;
        this.myInput = myInput;
        this.gate = gate;
        this.myout = myout;
        for (int i=0;i<myline2.length();i++){
            runInput.put((String) myInput.get(i), Character.getNumericValue(myline2.charAt(i)));
        }
        this.j=j;
        this.gate = gate;
    }

    @Override
    public void run() {
        for (String tem2 : myInput2) {
            if (myInput.contains(tem2)) RunNode.inputtoresult(tem2, runInput, result);
            else RunNode.mynode2(tem2, gate, result);
        }
        for (String i: myoutput){
            out+=result.get(i);
        }
        myout[j]=out;
    }
}