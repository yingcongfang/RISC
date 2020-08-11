package shared;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class InputHelper {
  public static String getInputStringFromConsole() {

    /* This function will return any String the player input.*/

    String ans = "";
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      ans = reader.readLine();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ans;
  }

  public static String getFileContentAsString(String path) {

    /* This function will read the file in path as a string and return the
     * stirng.
     */

    String ans = "";
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();
      while (line != null) {
        sb.append(line).append("\n");
        line = br.readLine();
      }
      ans = sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // remove the last '\n'
    if (ans != null && ans.length() > 0 && ans.charAt(ans.length() - 1) == '\n') {
      ans = ans.substring(0, ans.length() - 1);
    }
    return ans;
  }

  public static int getFileContentAsInt(String path) {
    String file_content = InputHelper.getFileContentAsString(path);
    int number = Integer.parseInt(file_content);
    return number;
  }

  public static int getInputIntFromConsole() {
    String input_content = "";
    while (!getIntHelper(input_content)) {
      if (input_content.length() != 0) {
        System.out.println("You input is invalid, please type again");
      }
      input_content = InputHelper.getInputStringFromConsole();
    }
    int number = Integer.parseInt(input_content);
    // System.o ut.println(number);
    return number;
  }

  private static boolean getIntHelper(String input_content) {
    if (input_content.length() == 0) {
      return false;
    }
    for (int i = 0; i < input_content.length(); i++) {
      if (!Character.isDigit(input_content.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
