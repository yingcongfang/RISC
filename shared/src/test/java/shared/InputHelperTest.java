package shared;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class InputHelperTest {
  @Test
  void test_InputString() {
    String input = "Hello World!";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    String actual_input = InputHelper.getInputStringFromConsole();
    System.setIn(stdin);
    assertEquals(input, actual_input);
  }
}
