package de.longuyen;

import de.longuyen.cli.CommandProcessor;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        new CommandLine(new CommandProcessor()).execute(args);
    }
}
