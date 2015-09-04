package com.fumanchu.simplebogglesolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fu Manchu
 */
class DictionaryLoader {
  private ArrayList<String> dic;

  public DictionaryLoader(String filename) {
    BufferedReader br = null;
    try {
      dic = new ArrayList<String>();
      br = new BufferedReader(new FileReader(filename));
      while (br.ready()) {
        dic.add(br.readLine().trim());
      }
    } catch (java.io.IOException ex) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, String.format("Could not open %s",filename));
    } finally {
      try {
        if (null != br) {
          br.close();
        }
      } catch (java.io.IOException x) {/* surpress */}
    }
  }

  public String[] dictionary() {
    String[] a = new String[dic.size()];
    return dic.toArray(a);
  }

  public int size() {
    return dic.size();
  }
  
}
