package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class FileHandler {

    public static Tuple readFile(String fileName) throws IOException {
        InputStream inputStream = Objects.requireNonNull(FileHandler.class.getClassLoader().getResource(fileName)).openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String readLine = "";
        int numberOfLines = 0;

        while(true) {
            readLine = reader.readLine();
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

        reader.close();
        return tuple;
    }
}
