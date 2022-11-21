package mihai;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ProgramScanner programScanner = new ProgramScanner("src/main/resources/token.txt",
                "constant.txt", "identifier.txt");
        programScanner.readTokens();
        var pif = programScanner.readFile("src/main/resources/p3.txt");
        File pifFile = new File("PIF.txt");
        try (FileWriter pifWriter = new FileWriter("PIF.txt")){
            pifWriter.write("\tPIF\n");
            for (var p : pif) {
               pifWriter.write(p + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var ST = programScanner.getSymbolTable();

        File stFile = new File("ST.txt");
        try (FileWriter stWriter = new FileWriter("ST.txt")){
            stWriter.write("\n\n\tSYMBOL TABLE\n");
            for (var p : ST) {
                stWriter.write(ST.indexOf(p)+ " -> " + p + "\n");
            }
            var CT = programScanner.getConstantsTable();
            stWriter.write("\n\n\tCONSTANTS TABLE\n");
            for (var p : CT) {
                stWriter.write(CT.indexOf(p)+ " -> " + p + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}