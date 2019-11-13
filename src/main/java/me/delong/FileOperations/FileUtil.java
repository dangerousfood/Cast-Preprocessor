package me.delong.FileOperations;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by josephdelong on 12/18/16.
 */
public class FileUtil {
    final static Charset UTF8 = Charset.forName("UTF-8");


    public static void writeFile(String path, String output) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(path), UTF8);
        try {
            writer.write(output);
        } finally {
            writer.close();
        }
    }

    public static void readFile(String path) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(path), UTF8);
        try {
            int c = reader.read();
            System.out.println(c);
        } finally {
            reader.close();
        }
    }
}
