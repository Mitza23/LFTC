package mihai;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ProgramScanner {
    String tokensFile;
    List<String> tokens;
    List<Pair<String, Integer>> pif;

    SymbolTable symbolTable;

    public void readTokens() {
        try(Scanner scanner = new Scanner(new File(tokensFile))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                tokens.add(line);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void readFile(String filename) {
        try(Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                var words = line.split("\"\\(a-zA-Z0-9\\)+\"|\\(a-zA-Z0-9\\)");
                for(var word : words) {
                    if(tokens.contains(word)) {
                        pif.add(new Pair<>(word, -1));
                    }
                    else {

                        symbolTable.addVariable();
                    }
                }
            }

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
