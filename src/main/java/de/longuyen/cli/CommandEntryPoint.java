package de.longuyen.cli;

import picocli.CommandLine;

public class CommandEntryPoint {
    public void process(final String [] args) {
        new CommandLine(new MainCommand()).execute(args);
    }
}
