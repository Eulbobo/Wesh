package fr.eulbobo.bf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

import fr.eulbobo.bf.support.BF;

public class BrainFuck {

    private final byte[] memory = new byte[30000];
    private int pointer = 0;
    private int[] ins, outs;

    private final List<BF> commands = new ArrayList<>();
    private final Consumer<Character> output;

    /**
     * Default constructor for the Wesh class. This constructor takes a file with
     * the code and uses it to execute as a BrainFuck program.
     *
     * @param codex a wesh code file
     * @exception Exception Description of Exception
     */
    public BrainFuck(final File codex, final Consumer<Character> output, final Function<String, BF> stringToBf) throws Exception {
        open(codex, stringToBf);
        this.output = output;

        if (!test()) {
            throw new Exception("Unbalanced code.");
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
    private void open(final File f, final Function<String, BF> cmdToBf) throws IOException {
        try (Scanner scn = new Scanner(f)) {
            while (scn.hasNext()) {
                BF cmd = cmdToBf.apply(scn.next());
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
            case O: {
                x++;
                inb++;
                break;
            }

            case C: {
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

            case O: {
                ins[inAt++] = i;
                outAt = inAt;
                break;
            }

            case C: {
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

            case P: {
                memory[pointer]++;
                break;
            }

            case M: {
                memory[pointer]--;
                break;
            }

            case G: {
                pointer = pointer == 29999 ? 0 : pointer + 1;
                break;
            }

            case L: {
                pointer = pointer == 0 ? 29999 : pointer - 1;
                break;
            }

            case O: {
                if (memory[pointer] == 0) {
                    i = getEndLoop(i);

                    if (i == -1) {
                        throw new Exception("Unable to find the end for the current loop");
                    }
                }

                break;
            }

            case C: {
                i = getStartLoop(i) - 1;

                if (i == -1) {
                    throw new Exception("Unable to find the beginning for the current loop");
                }

                break;
            }

            case PT: {
                output.accept((char) memory[pointer]);
                break;
            }

            case CM: {
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
