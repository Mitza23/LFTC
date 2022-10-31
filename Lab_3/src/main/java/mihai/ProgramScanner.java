package mihai;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import static mihai.Constants.CONSTANT;
import static mihai.Constants.IDENTIFIER;

/**
 * ProgramScanner class is used to read an input program and break it down into tokens
 */
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

    /**
     * Reads predefined tokens such as separators, keywords and operators
     */
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

    /**
     * Checks if a token is part of the tokens.in file
     *
     * @param token
     * @return
     */
    public boolean isReserved(String token) {
        return tokens.contains(token);
    }

    /**
     * Checks to see if a toke is an identifier according to the rules of the language
     *
     * @param token
     * @return
     */
    public boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z]+[a-zA-Z0-9]*");
    }

    /**
     * Checks to see if a token is a constant according to the rules of the language
     *
     * @param token
     * @return
     */
    public boolean isConstant(String token) {
        return token.matches("\"[a-zA-Z0-9]+\"|\'[a-zA-Z0-9]\'|[0-9]|[1-9][0-9]*");
    }

    /**
     * Adds a new constant to the constants table or just returns its position if its already present
     *
     * @param token
     * @return
     */
    public int addConstant(String token) {
        for (int i = 0; i < constantsTable.size(); i++) {
            if (constantsTable.get(i).equals(token)) {
                return i;
            }
        }
        constantsTable.add(token);
        return constantsTable.size() - 1;
    }

    /**
     * Adds a new symbol to the symbols table or just returns its position if its already present
     *
     * @param token
     * @return
     */
    public int addSymbol(String token) {
        for (int i = 0; i < symbolTable.size(); i++) {
            if (symbolTable.get(i).equals(token)) {
                return i;
            }
        }
        symbolTable.add(token);
        return symbolTable.size() - 1;
    }

    public List<String> getSymbolTable() {
        return symbolTable;
    }

    public List<String> getConstantsTable() {
        return constantsTable;
    }

    public String getDelimiters() {
        return delimiters;
    }

    /**
     * This function is intended to read a program file and break it down into tokens by generating the PIF
     * while populating the constants table and the symbol table
     *
     * @param filename
     * @return
     */
    public List<Pair<String, Integer>> readFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int lineCount = 0;
            String PIF = "";
            while (scanner.hasNext()) {
                String toPrint = "";
                String line = scanner.nextLine();
                lineCount += 1;
                var tokenizer = new StringTokenizer(line, delimiters, true);
                while (tokenizer.hasMoreTokens()) {
                    var token = tokenizer.nextToken();
                    if (!token.equals(" ")) {
                        token = token.strip();
//                        System.out.println("[" + token + "]");
                        toPrint += "[" + token + "]  ";
                        if (isReserved(token)) {
                            pif.add(new Pair<>(token, -1));
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
            return pif;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
