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
        for (int i:myinput)z=z|i;
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