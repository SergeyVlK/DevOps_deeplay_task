//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class App {
    public App() {
    }

    public static void main(String[] args) throws Exception {
        String pattern = "dd.MM.yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "EN"));
        if (args.length >= 2) {
            Files.createDirectories(Path.of(args[0]).getParent());
            int i = 1;

            while(true) {
                String date = simpleDateFormat.format(new Date());
                String out = "" + i + ") " + date + "   " + args[1] + "\n";
                appendUsingOutputStream(args[0], out);
                Thread.sleep(2000L);
                ++i;
            }
        }

        System.out.println("There's no args");
    }

    private static void appendUsingOutputStream(String fileName, String data) {
        OutputStream os = null;

        try {
            os = new FileOutputStream(new File(fileName), true);
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException var12) {
            var12.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

    }
}

