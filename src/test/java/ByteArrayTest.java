import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class ByteArrayTest {
    @Test
    public void testFromCP866ToCP1251() {
        try {
            byte[] bytes = Files.readAllBytes(new File("src/main/resources/sample1.txt").toPath());
            for (int i = 0; i < bytes.length; i++) {
                if (i == 0) bytes[i] -= 36;
                if (i == 1) bytes[i] -= 86;

                if (bytes[i] > -129 && bytes[i] < -80) {
                    bytes[i] += 64;
                } else if (bytes[i] > -33 && bytes[i] < -16) {
                    bytes[i] += 16;
                }
            }
            Files.write(new File("src/main/resources/output1.txt").toPath(), bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFromISO_8859_5ToCP1251() {
        try {
            byte[] bytes = Files.readAllBytes(new File("src/main/resources/sample2.txt").toPath());
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] >= -79 && bytes[i] <= -17) {
                    bytes[i] += 0x10;
                }
            }
            Files.write(new File("src/main/resources/output1.txt").toPath(), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
