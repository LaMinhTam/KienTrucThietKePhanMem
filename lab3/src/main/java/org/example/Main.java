package org.example;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import jdepend.framework.PackageFilter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws IOException {
        JDepend d = new JDepend();
        d.addDirectory("C:\\Users\\Administrator\\LaMinhTam\\workspace\\intellij\\KienTrucThietKePhanMem\\lab3");
        Collection<JavaPackage> cols = d.analyze();
        cols.forEach(pkg -> {
            System.out.printf("Pakage %s, Ca= %d; Ce=%d;\n", pkg.getName(),
                    pkg.getAfferents().size(), pkg.getEfferents().size());
        });

        try (PrintWriter out = new PrintWriter(new FileOutputStream("results.xml"))) {
            jdepend.xmlui.JDepend xml = new jdepend.xmlui.JDepend(out);
            xml.addDirectory("C:\\Users\\Administrator\\LaMinhTam\\workspace\\intellij\\KienTrucThietKePhanMem\\lab3");
            PackageFilter f = PackageFilter.all();
            f.including("org.example");
            f.accept("org");
            f.excluding("org");
            xml.setFilter(f);
            xml.analyze();
        }

    }
}