import java.util.* ;
import java.io.* ;
public class BasicSim {
    static HashMap<String, Integer> gateValue  = new HashMap<String, Integer>() ;
    static ArrayList<String> inputList = new ArrayList<String>() ;
    static ArrayList<String> outputList = new ArrayList<String>() ;
    static ArrayList<String[]> gateList = new ArrayList<String[]>() ;    
    
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis() ;
        String benchFile = "c432.bench.txt";
        parseBenchFile(benchFile) ; // build data structure
        String ipFile = "c432_1k_ip.txt";
        String opFile = "c432_1k_op.txt" ;
        simulation(ipFile,opFile) ; // generate the result
        System.out.printf("Total time=%.3f sec(s)\n",
						(System.currentTimeMillis()-start)/1000.0) ;
    }
    // ���������
    public static void simulation(String ipFile, String opFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(ipFile)) ;
        BufferedWriter bw = new BufferedWriter(new FileWriter(opFile)) ;        
        String ipvs = "", opvs="" ;
	   // Ū����J�T���ɮ�(xxx_ip.txt)�A�öi���޿�h�B��A���Ϳ�X�C
        while ((ipvs=br.readLine())!=null) {
            ipvs = ipvs.trim() ; // �p"01010011"����J
            fillInput(ipvs) ;  // �Nipvs���Ѭ�0, 1, �K�A��J�q������J�}
            for (int i = 0 ; i<gateList.size(); i++) { // evaluate gates one by one
                doSim(gateList.get(i)) ; // �̷ӨC���޿�h�S�ʡA�i��B��
            }
            opvs = gatherOutput(ipvs, bw) ; // �`����X�A�üg���ɮ�
        }
        br.close(); bw.close() ;
    }

    public static void fillInput(String ipvs) {
        if (ipvs.length() != inputList.size()) {
            throw new RuntimeException("Input Size mismatch:"+ipvs.length()+","+inputList.size()) ;
        }
        for (int i = 0 ; i<ipvs.length(); i++) {
            if (ipvs.charAt(i)=='0')
                gateValue.put(inputList.get(i),0) ;
            else 
                gateValue.put(inputList.get(i),1) ;                
        }
    }
    // �U���޿�h���B��A�ϥ�if-else�A�Ĳv??????
    public static void doSim(String[] gateInfo) {
        String gName = gateInfo[0] ;
        String gateType = gateInfo[1] ;
        int v = 0 ;
        if (gateType.equalsIgnoreCase("AND")) {
            v = AND(gateInfo) ;
        } else if (gateType.equalsIgnoreCase("OR")) {
            v = OR(gateInfo) ;
        } else if (gateType.equalsIgnoreCase("NAND")) {
            v = NAND(gateInfo) ;
        } else if (gateType.equalsIgnoreCase("NOR")) {
            v = NOR(gateInfo) ;
        } else if (gateType.equalsIgnoreCase("XOR")) {
            v = XOR(gateInfo) ;
        } else if (gateType.equalsIgnoreCase("XNOR")) {
            v = XNOR(gateInfo) ;
        } else if (gateType.equalsIgnoreCase("BUF")) {
            v = BUF(gateInfo) ;
        } else if (gateType.equalsIgnoreCase("NOT")) {
            v = NOT(gateInfo) ;
        } else {
            throw new RuntimeException("Unknown Gate:"+gName+","+gateType);
        }
        gateValue.put(gName, v) ;
    }

    public static String gatherOutput(String ipvs, BufferedWriter bw) throws Exception {    
        String opvs = "" ;
        for (int i = 0 ; i<outputList.size(); i++) {
            opvs+=gateValue.get(outputList.get(i)) ;
        }
        bw.write(ipvs+" "+opvs);
        bw.newLine();   
        return opvs ;
    }
    
// ------ Gate Value Evaluation Fuctions ----------
    public static int AND(String[] gateInfo) {
        int v = 0 ; 
        for (int i = 2 ; i<gateInfo.length; i++) 
            if ( (v=gateValue.get(gateInfo[i])) ==0)
                return 0 ;
        return  1;
    }
    public static int OR(String[] gateInfo) {
        int v = 0 ; 
        for (int i = 2 ; i<gateInfo.length; i++) 
            if ( (v=gateValue.get(gateInfo[i]))==1) 
                return 1 ;
        return  0;        
    }
    public static int XOR(String[] gateInfo) {
        int v1 = gateValue.get(gateInfo[2]), v2 = gateValue.get(gateInfo[3]) ;
        return (v1==v2)?0:1 ;
    }    
    public static int NAND(String[] gateInfo) { return (AND(gateInfo)==0)?1:0 ; }
    public static int NOR(String[] gateInfo) { return (OR(gateInfo)==0)?1:0 ;}    
    public static int XNOR(String[] gateInfo) { return (XOR(gateInfo)==0)?1:0 ; } 
    public static int BUF(String[] gateInfo) {return gateValue.get(gateInfo[2]);} 
    public static int NOT(String[] gateInfo) { return (BUF(gateInfo)==0)?1:0 ; }

// ------ Parsing Circuit File and Build Data Structure ----------
// ****** �ɤO�kŪ���ä��ιq���ɡA���͸�Ƶ��c�A�����A�ݭn�ק� ******
    public static void parseBenchFile(String benchFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(benchFile)) ;
        String aLine = "" ;
        String gName = "", gType ="" ;
        
        while ((aLine=br.readLine())!=null) {
            if (aLine.startsWith("#")|| aLine.trim().length()==0 ) continue ; 
            if (aLine.startsWith("INPUT")) {
                String[] tt = aLine.split("\\(") ;
                gName = tt[1].replace(")","") ;
                gateValue.put(gName, null);
                inputList.add(gName) ;
            }
            else if (aLine.startsWith("OUTPUT")) {
                String[] tt = aLine.split("\\(") ;
                gName = tt[1].replace(")","") ;
                gateValue.put(gName,null) ;
                outputList.add(gName) ;
            }  else {
                aLine = aLine.replace(" ","") ;
                aLine = aLine.replace("=",",") ;
                aLine = aLine.replace("(",",") ;
                aLine = aLine.replace(")","") ;
                String[] tt = aLine.split(",") ;
                gateValue.put(tt[0], null) ;
                gateList.add(tt) ;
            } 
        }
        br.close() ;
    }
}
