/* Boggle Board Comparator
 * 
 * String Comparator for Boggle game
 * String o1 is considered less than o2 if o1 is shorter than o2
 * String o1 is considered greater than o2 if o1 is longer than o2
 * If o1 and o2 is of the same length,
 * o1 is less than o2 when o1 lexicographically precedes o2.
 * o1 is greather than o2 when o1 lexicographically follows o2.
 * o1 equals o2 when strings o1 and o2 equals as well.
 */

package com.fumanchu.simplebogglesolver;

import java.util.Comparator;

/**
 *
 * @author Fu Manchu
 */
class BoggleComparator implements Comparator<String> {

  @Override
  public int compare(String o1, String o2) {
    if (o1.length() < o2.length()) {
      return 1;
    }
    if (o1.length() > o2.length()) {
      return -1;
    }
    return o1.compareTo(o2);
  }
  
}
