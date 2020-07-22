package de.longuyen;

import picocli.CommandLine;
import de.longuyen.cli.CommandProcessor;

public class Main {
    public static void main(String[] args) {
        new CommandLine(new CommandProcessor()).execute(args);
    }
}
