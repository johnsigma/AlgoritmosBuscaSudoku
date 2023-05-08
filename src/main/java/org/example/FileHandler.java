package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class FileHandler {

    public static Tuple readFile(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String line = "";
        String readLine = "";
        int numberOfLines = 0;

        while(true) {
            readLine = buffRead.readLine();
            if(readLine == null) {
                break;
            }else {
                numberOfLines++;
                line += readLine + "\n";
            }
        }


        if(line.lastIndexOf("\n") == line.length()-1)
            line = line.substring(0, line.length()-1);

        Tuple tuple = new Tuple(line, numberOfLines);

        buffRead.close();
        return tuple;
    }
}
