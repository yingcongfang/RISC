package shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitsTest {
  @Test
  public void testUpgrade() {
    Player A = new Player();
    Unit units = new Unit();

    // assertTrue(units.upgradeUnit());
    assertEquals(units.getBonus(), 1);
    assertEquals(A.getTech_resource(), 47);
    // assertTrue(units.upgradeUnit());
    assertEquals(units.getBonus(), 3);
    assertEquals(A.getTech_resource(), 39);
    // assertTrue(units.upgradeUnit());
    assertEquals(units.getBonus(), 5);
    assertEquals(A.getTech_resource(), 20);
    // assertFalse(units.upgradeUnit());
  }
}
