package fr.eulbobo.bf;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Test;

import fr.eulbobo.bf.support.BF;

public class BrainFuckTest {

    @Test
    public void should_say_hello_world_when_reading_test_file() throws Exception{
        URL url = BrainFuckTest.class.getClassLoader().getResource("Bf.txt");
        File input = Paths.get(url.toURI()).toFile();
        StringBuilder sb = new StringBuilder();


        BrainFuck bf = new BrainFuck(input, sb::append, BF::valueOf);
        bf.execute();

        assertEquals("On devrait lire HelloWorld!", "Hello World!\n", sb.toString());
    }

}
