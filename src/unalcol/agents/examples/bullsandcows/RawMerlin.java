package  unalcol.agents.examples.bullsandcows;

import java.util.ArrayList;
import java.util.Arrays;

public class RawMerlin {
  int digits;
  int positions;
  ArrayList<int[]> hat;
  
  public RawMerlin(int digits, int positions) {
    
    hat = new ArrayList();
    this.digits = digits;
    this.positions = positions;
    int[] ans = new int[positions];
    
    for (int m = 0; m < (1<<digits); ++m) {
      boolean add = true;
      int p = 0;
      for (int i = 0; i < digits; ++i) {
        if ((m&(1<<i)) != 0) {
          if (p+1 > positions) {
              add = false;
              break;
          }
          else {
              ans[p++] = i;
          }
        }
      }
      if (add) addPermutations(hat, ans, 0);
    }
  }
  
  public int[] next() {
    return hat.remove(hat.size()-1);
  }
  
  public boolean check(int guess[], int bc[]) {
    ArrayList<int[]> pruned = new ArrayList();
    for (int[] ans : hat) {
      int[] t = compare(ans, guess);
      if (Arrays.equals(t, bc)) pruned.add(ans);
    }
    hat = pruned;
    return true;
  }
  
  private int[] compare(int[] a, int[] b) {
    boolean[] inA = new boolean[digits];
    for (int d : a) {
      inA[d] = true;
    }
    int[] bc = new int[2];
    for (int i = 0; i < positions; ++i) {
      if (a[i] == b[i]) ++bc[0];
      else if (inA[b[i]]) ++bc[1];
    }
    return bc;
  }
  
  private void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }
  
  private void addPermutations(ArrayList arr, int[] perm, int k) {
    if (k == positions-1) {
      arr.add(perm.clone());
      return;
    }
    for (int i = k; i < positions; ++i) {
      swap(perm, i, k);
      addPermutations(arr, perm, k+1);
      swap(perm, i, k);
    }
  }
}
