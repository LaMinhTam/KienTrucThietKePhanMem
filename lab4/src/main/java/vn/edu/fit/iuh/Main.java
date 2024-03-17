package vn.edu.fit.iuh;

import jdepend.xmlui.JDepend;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream("C:\\Users\\Administrator\\LaMinhTam\\workspace\\intellij\\KienTrucThietKePhanMem\\lab4\\report\\report.xml"));
        JDepend jDepend = new JDepend(printWriter);
        jDepend.addDirectory("C:\\Users\\Administrator\\LaMinhTam\\workspace\\intellij\\KienTrucThietKePhanMem\\lab4");
        jDepend.analyze();
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd \"C:\\Users\\Administrator\\LaMinhTam\\workspace\\intellij\\KienTrucThietKePhanMem\\lab4\\jdepend-ui\" && dir");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }

    }
}