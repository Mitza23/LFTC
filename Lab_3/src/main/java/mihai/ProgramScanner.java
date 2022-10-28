package mihai;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static mihai.Constants.CONSTANT;
import static mihai.Constants.IDENTIFIER;

public class ProgramScanner {
    String tokensFile;
    List<String> tokens;
    List<Pair<String, Integer>> pif;
    List<String> symbolTable;
    List<String> constantsTable;

//    SymbolTable symbolTable;


    public ProgramScanner(String tokensFile) {
        this.tokensFile = tokensFile;
        tokens = new ArrayList<>();
        pif = new ArrayList<>();
        symbolTable = new ArrayList<>();
        constantsTable = new ArrayList<>();
    }

    public void readTokens() {
        try (Scanner scanner = new Scanner(new File(tokensFile))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                tokens.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isReserved(String token) {
        return tokens.contains(token);
    }

    public boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z]+[a-zA-Z0-9]*");
    }

    public boolean isConstant(String token) {
        return token.matches("\"[a-zA-Z0-9]+\"|'[a-zA-Z0-9]'|[1-9][0-9]*");
    }

    public int addConstant(String token) {
        for (int i = 0; i < constantsTable.size(); i++) {
            if (constantsTable.get(i).equals(token)) {
                return i;
            }
        }
        constantsTable.add(token);
        return constantsTable.size() - 1;
    }

    public int addSymbol(String token) {
        for (int i = 0; i < symbolTable.size(); i++) {
            if (symbolTable.get(i).equals(token)) {
                return i;
            }
        }
        symbolTable.add(token);
        return symbolTable.size() - 1;
    }

    public void readFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int lineCount = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                lineCount += 1;
//                var words = line.split("[ {}\\[\\]()=;\"]+");
                var words = line.split(" +");
                System.out.println(Arrays.toString(words));
                int index = 0;
                for (var token : words) {
                    if (isReserved(token)) {
                        pif.add(new Pair<>(token, -1));
                        if (token.equals("=")) ;
                    } else {
                        if (isConstant(token)) {
                            int pos = addConstant(token);
                            pif.add(new Pair<>(CONSTANT, pos));
                        } else if (isIdentifier(token)) {
                            int pos = addSymbol(token);
                            pif.add(new Pair<>(IDENTIFIER, pos));
                        } else {
                            throw new SyntaxError("Syntax error: unidentified token " + token + " on line " + lineCount);
                        }
                    }
                    index += 1;
                }
            }

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
