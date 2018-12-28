import java.util.HashMap;

public class RunNode {
    static void inputtoresult(String mytest,HashMap<String,Integer> runInput,HashMap<String,Integer>  result){
        result.put(mytest,runInput.get(mytest));
    }
    static void mynode2(String mytest, HashMap gate, HashMap<String,Integer> result){
        String[] tem= (String[]) gate.get(mytest);
        int[] tem2 = new int[tem.length-1];
        for (int i=1;i<tem.length;i++){
            tem2[i-1]=  result.get(tem[i]);
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
