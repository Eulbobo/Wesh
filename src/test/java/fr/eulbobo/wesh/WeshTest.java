package fr.eulbobo.wesh;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Test;

public class WeshTest {

    @Test
    public void should_say_hello_world_when_reading_test_file() throws Exception{
        URL url = WeshTest.class.getClassLoader().getResource("HelloWorld.txt");
        File input = Paths.get(url.toURI()).toFile();
        StringBuilder sb = new StringBuilder();


        Wesh wesh = new Wesh(input, sb::append);
        wesh.execute();

        assertEquals("On devrait lire HelloWorld!", "Hello World!\n", sb.toString());
    }

}
