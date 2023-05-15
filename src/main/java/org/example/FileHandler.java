package org.example;

import org.example.response.SudokuResponse;

import java.io.*;
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

    public static void writeToFile(String filePath, SudokuResponse sudokuResponse) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Algoritmo: " + sudokuResponse.getResolutionMethod() + "\n");
            writer.write("Complexidade: " + sudokuResponse.getComplexity()+ "\n");
            writer.write("Custo inicial: " + sudokuResponse.getInitialCost()+ "\n");
            writer.write("Custo final: " + sudokuResponse.getFinalCost()+ "\n");
            writer.write("Nós visitados: " + sudokuResponse.getQuantityOfVisitedNodes()+ "\n");
            writer.write("Tempo de execução: " + sudokuResponse.getSpentTime()+" segundos" +"\n");
            writer.write("Estados gerados: " + sudokuResponse.getSteps()+ "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
