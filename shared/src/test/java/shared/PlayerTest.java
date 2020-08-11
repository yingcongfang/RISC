package shared;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
  @Test
  void set_name_test() {
    Player test = new Player();
    test.setName("Drew");
    System.out.println(test.getName());
  }

  @Test
  void one_round_order_test() {
    String str = "d\n";
    InputStream is = new ByteArrayInputStream(str.getBytes());
    System.setIn(is);
    Player player1 = new Player("Amy");
  }
}
