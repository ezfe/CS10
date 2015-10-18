/**
 * Wrapper class for an int that may be changed.
 * Does not support autoboxing or unboxing.
 * 
 * @author Tom Cormen
 */

public class MutableInteger {
  private int value;
  
  public MutableInteger(int v) {
    value = v;
  }
  
  public void set(int v) {
    value = v;
  }
  
  public int get() {
    return value;
  }
  
  public String toString() {
    return Integer.toString(value);
  }
  
  public boolean equals(Object other) {
    if (other instanceof MutableInteger)
      return value == ((MutableInteger) other).value;
    else if (other instanceof Integer)
      return value == (Integer) other;
    else
      return false;
  }
}