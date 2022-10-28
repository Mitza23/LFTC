package mihai;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import static mihai.Constants.CONSTANT;
import static mihai.Constants.IDENTIFIER;

public class ProgramScanner {
    String tokensFile;
    List<String> tokens;
    List<Pair<String, Integer>> pif;
    List<String> symbolTable;
    List<String> constantsTable;

    String delimiters = " ;()[]{}";

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
                String line = scanner.nextLine().strip();
                tokens.add(line);
            }
            tokens.add(" ");
//            for(var t : tokens) {
//                System.out.println("[" + t + "]");
//            }
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
        return token.matches("\"[a-zA-Z0-9]+\"|'[a-zA-Z0-9]'|[0-9]|[1-9][0-9]*");
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

    public String readFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int lineCount = 0;
            String PIF = "";
            while (scanner.hasNext()) {
                String toPrint = "";
                String line = scanner.nextLine();
                lineCount += 1;
                var tokenizer = new StringTokenizer(line, delimiters, true);
                while(tokenizer.hasMoreTokens()) {
                    var token = tokenizer.nextToken();
                    if (!token.equals(" ")) {
                        token = token.strip();
//                        System.out.println("[" + token + "]");
                        toPrint += "[" + token + "]  ";
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
                                throw new SyntaxError("Syntax error: unidentified token [" + token + "] on line " + lineCount);
                            }
                        }
                    }
                }
                PIF += toPrint + "\n";
            }
            return PIF;
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
