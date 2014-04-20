package  unalcol.agents.examples.bullsandcows;

import java.util.ArrayList;
import java.util.Arrays;

public class Merlin {
  ArrayList<int[]> hat;
  
  public Merlin(NumberIndex ni) {
    
    hat = new ArrayList();
    int sz = ni.size();
    for (int i = 0; i < sz; ++i) {
      hat.add(ni.getOption(i));
    }
  }
  
  public int[] next() {
    return hat.remove(hat.size()-1);
  }
  
  public boolean check(int guess[], int bc[]) {
    ArrayList<int[]> pruned = new ArrayList();
    for (int[] ans : hat) {
      int[] t = NumberIndex.compare(ans, guess);
      if (Arrays.equals(t, bc)) pruned.add(ans);
    }
    hat = pruned;
    return true;
  }
}
