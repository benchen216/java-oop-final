import java.util.ArrayList;

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