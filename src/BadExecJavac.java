import java.util.*;
import java.io.*;
public class BadExecJavac
{
    public static void main(String args[]) throws IOException {
        String[] cmd = {
                "/bin/sh",
                "-c",
                "ls",
                "-al"
        };

        Process p = Runtime.getRuntime().exec(cmd);
        InputStream i = p.getInputStream();
        byte[] b = new byte[100];
        i.read(b, 0, b.length);
        System.out.println(new String(b));
        /*
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("javac");
            int exitVal = proc.exitValue();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t)
        {
            t.printStackTrace();
        }*/
    }
}