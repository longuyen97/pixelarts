package de.longuyen.cli;

import picocli.CommandLine;

/**
 * Entry point of the CLI. Good for testing purpose
 */
public class CommandEntryPoint {
    public void process(final String [] args) {
        new CommandLine(new MainCommand()).execute(args);
    }
}
