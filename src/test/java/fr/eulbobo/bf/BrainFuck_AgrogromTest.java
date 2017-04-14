package fr.eulbobo.bf;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Test;

import fr.eulbobo.bf.agrogrom.AgrogromCommands;

public class BrainFuck_AgrogromTest {

    @Test
    public void should_say_hello_world_when_reading_test_file() throws Exception{
        URL url = BrainFuck_AgrogromTest.class.getClassLoader().getResource("Agrogrom.txt");
        File input = Paths.get(url.toURI()).toFile();
        StringBuilder sb = new StringBuilder();


        BrainFuck wesh = new BrainFuck(input, sb::append, AgrogromCommands::bfFromValue);
        wesh.execute();

        assertEquals("On devrait lire HelloWorld!", "Hello World!\n", sb.toString());
    }

}
