package fr.eulbobo.wesh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Extended Interpreter for the Wesh BrainFuck programming language
 *
 * @author david moss
 * @author eulbobo
 * @created 02 January 2002
 */
public class Wesh {

    private final byte[] memory = new byte[30000];
    private int pointer = 0;
    private int[] ins, outs;

    private final List<WeshCommands> commands = new ArrayList<>();
    private final Consumer<Character> output;

    /**
     * Default constructor for the Wesh class. This constructor takes a file with
     * the code and uses it to execute as a BrainFuck program.
     *
     * @param codex a wesh code file
     * @exception Exception Description of Exception
     */
    public Wesh(final File codex, final Consumer<Character> output) throws Exception {
        open(codex);
        this.output = output;

        if (!test()) {
            throw new Exception("Unbalanced Wesh code.");
        }
    }

    /**
     * Just for testing.
     *
     * @param args nothing
     */
    public static void main(final String[] args) throws Exception {
        if (args.length > 0) {
            Wesh wesh = new Wesh(new File(args[0]), System.out::print);
            wesh.execute();
        } else {
            System.out.println("You must enter a file name in the commandline to execute it");
            System.exit(1);
        }
    }

    /**
     * Gets the end of a loop according to the index of the code passed to the
     * function
     *
     * @param i The index at which the loop starts
     * @return The endLoop value or -1 if there iss an error
     */
    private int getEndLoop(final int i) {
        for (int o = 0; o < ins.length; o++) {
            if (ins[o] == i) {
                return outs[o];
            }
        }

        return -1;
    }

    /**
     * Gets the stert of a loop according to the index of the code passed to
     * the function
     *
     * @param i The index at which the loop ends
     * @return The endLoop value or -1 if there iss an error
     */
    private int getStartLoop(final int i) {
        for (int o = 0; o < outs.length; o++) {
            if (outs[o] == i) {
                return ins[o];
            }
        }

        return -1;
    }

    /**
     * @param f the file
     */
    private void open(final File f) throws IOException {
        try (Scanner scn = new Scanner(f)) {
            while (scn.hasNext()) {
                WeshCommands cmd = WeshCommands.valueOf(scn.next());
                if (cmd != null) {
                    commands.add(cmd);
                }
            }
        }
    }

    /**
     * Function that tests the code for balance and allocates the correct loop
     * index to an array
     *
     * @return true if the code is balanced
     */
    private boolean test() {
        int x = 0;
        int inb = 0;
        int outb = 0;

        for (int i = 0; i < commands.size(); i++) {
            switch (commands.get(i)) {
            case ZIVA: {
                x++;
                inb++;
                break;
            }

            case WOULAH: {
                outb++;
                break;
            }
            }

            if (outb > inb) {
                return false;
            }
        }

        if (inb != outb) {
            return false;
        }

        ins = new int[x];
        outs = new int[x];
        int inAt = 0;
        int outAt = 0;

        for (int i = 0; i < commands.size(); i++) {
            switch (commands.get(i)) {

            case ZIVA: {
                ins[inAt++] = i;
                outAt = inAt;
                break;
            }

            case WOULAH: {
                for (int o = outAt; o >= 0; o--) {
                    if (outs[o - 1] == 0) {
                        outs[o - 1] = i;
                        break;
                    }
                }

                break;
            }
            }
        }

        return true;
    }

    /**
     * Executes the code in a command prompt
     *
     * @exception Exception when shit happens
     */
    public void execute() throws Exception {
        for (int i = 0; i < commands.size(); i++) {
            switch (commands.get(i)) {

            case WESH: {
                memory[pointer]++;
                break;
            }

            case YO: {
                memory[pointer]--;
                break;
            }

            case HEY: {
                pointer = pointer == 29999 ? 0 : pointer + 1;
                break;
            }

            case GROS: {
                pointer = pointer == 0 ? 29999 : pointer - 1;
                break;
            }

            case ZIVA: {
                if (memory[pointer] == 0) {
                    i = getEndLoop(i);

                    if (i == -1) {
                        throw new Exception("Unable to find the end for the current loop");
                    }
                }

                break;
            }

            case WOULAH: {
                i = getStartLoop(i) - 1;

                if (i == -1) {
                    throw new Exception("Unable to find the beginning for the current loop");
                }

                break;
            }

            case COUSIN: {
                output.accept((char) memory[pointer]);
                break;
            }

            case POTO: {
                try {
                    memory[pointer] = (byte) System.in.read();

                    while (memory[pointer] == (byte) '\r'
                            || memory[pointer] == (byte) '\n') {
                        memory[pointer] = (byte) System.in.read();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            }
        }
    }
}
