package de.longuyen.cli;

import picocli.CommandLine;

@CommandLine.Command(version = "Help demo for picocli v2.0", header = "%nAutomatic Help Demo%n",
        description = "Prints usage help and version help when requested.%n",
        footer = "See AutomaticHelpDemo3 for the more compact syntax supported by picocli 3.0.")
public class CommandProcessor implements Runnable {
    @CommandLine.Option(names = {"--file", "-f"}, description = "Target image file for asciifying", required = true)
    private String file;

    @CommandLine.Option(names = {"--interpolation", "-i"}, description = "How much should the image be interpolated")
    private int interpolation = 3;

    @CommandLine.Option(names = {"--window", "-w"}, description = "How big should the window's size be")
    private int windowSize = 3;

    @Override
    public void run() {
        System.out.println("test");
    }
}
