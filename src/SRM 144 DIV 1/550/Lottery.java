import java.util.*;

public class Lottery {

  public String[] sortByOdds(String[] rules) {
    Map<String, Long> results = new HashMap<String, Long>();
    for (int i = 0; i < rules.length; i++) {
      String[] parts = rules[i].split(":");
      String name = parts[0];
      String[] ruleDetails = parts[1].split(" ");
      int choices = Integer.parseInt(ruleDetails[1]);
      int blanks = Integer.parseInt(ruleDetails[2]);
      boolean sorted = ruleDetails[3].equals("T");
      boolean unique = ruleDetails[4].equals("T");

      long rating = getRating(choices, blanks, sorted, unique);
      System.out.println(name + " " + rating);
      results.put(name, rating);
    }

    return sortNames(results);
  }

  protected long intPow(int choices, int blanks) {
    long result = 1;
    for (int i = 0; i < blanks; i++) {
      result = result * choices;
    }
    return result;
  }

  protected long getRating(int choices, int blanks, boolean sorted, boolean unique) {
    long total = intPow(choices, blanks);
    if (!sorted && !unique) {
      return total;
    }

    if (!sorted && unique) {
      return chooseP(choices, blanks);
    }

    if (unique && sorted) {
      return chooseC(choices, blanks);
    }

    return chooseC(choices-1+blanks, blanks);
  }

  protected long factorial(int n) {
    long factorial = 1;
    for (int i = 1; i <= n; i++) {
      factorial = factorial * i;
    }
    return factorial;
  }

  protected long chooseP(int choices, int blanks) {
    if (blanks > choices) {
      return 0;
    }

    long result = 1;
    for (int i = choices - blanks + 1; i <= choices; i++) {
      result = result * i;
    }
    return result;
  }

  protected long chooseC(int choices, int blanks) {
    if (blanks > choices) {
      return 0;
    }
    long chooseP = chooseP(choices, blanks);
    return chooseP / factorial(blanks);
  }

  protected String[] sortNames(Map<String, Long> results) {

    class ValueComparator implements Comparator<String> {

      Map<String, Long> base;
      public ValueComparator(Map<String, Long> base) {
        this.base = base;
      }

      public int compare(String a, String b) {
        if (base.get(a).equals(base.get(b))) {
          return b.compareTo(a) < 0 ? 1 : -1; // if this returns 0, we merge keys, but there are no duplicate names
        } else if (base.get(a) > base.get(b)) {
          return 1;
        } else {
          return -1;
        }
      }
    }
    ValueComparator c = new ValueComparator(results);
    TreeMap<String, Long> sorted = new TreeMap<String, Long>(c);
    sorted.putAll(results);

    Set<String> keys = sorted.keySet();
    return keys.toArray(new String[0]);
  }
}
