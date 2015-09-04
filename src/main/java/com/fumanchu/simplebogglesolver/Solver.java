/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fumanchu.simplebogglesolver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import edu.princeton.cs.algorithms.TST;

/**
 *
 * @author Fu Manchu
 */
public class Solver {
  private TST<Integer> dictionary;

  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public Solver(String[] dictionary) {
    this.dictionary = new TST<Integer>();
    for (String word : dictionary) {
      this.dictionary.put(word, getPointValue(word));
    }
  }

  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public SortedMap<String, List<Integer>> getAllValidWords(BoggleBoard board) {
    SortedMap<String, List<Integer>> wordSet =
            new TreeMap<String, List<Integer>>(new BoggleComparator());
    boolean[] marked = new boolean[board.cols() * board.rows()];
    for (int r = 0; r < board.rows(); ++r) {
      for (int c = 0; c < board.cols(); ++c) {
        //System.out.println("r:" + r + ", c:" + c + "=" + asID(r, c, board.cols()));
        marked[asID(r, c, board.cols())] = true;
        dfsBoard(r, c, board, new StringBuilder(), marked, wordSet, new Stack<Integer>());
      }
    }
    return wordSet;
  }

  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word) {
    if (!dictionary.contains(word)) {
      return 0;
    }
    return dictionary.get(word);
  }

  private int getPointValue(String word) {
    int base = word.length() - 2;
    return base * base;
  }

  private int asID(int i, int j, int length) {
    return j * length + i;
  }

  private void dfsBoard(int r, int c,
          BoggleBoard board,
          StringBuilder prefix,
          boolean[] marked,
          SortedMap<String, List<Integer>> words,
          Stack<Integer> steps) {
    int cols = board.cols();
    int rows = board.rows();
    push(prefix, board.getLetter(r, c));
    steps.push(asID(c, r, rows));
    if (prefix.length() > 2 && !dictionary.prefixMatch(prefix.toString()).iterator().hasNext()) {
      marked[asID(r, c, cols)] = false;
      steps.pop();
      pop(prefix);
      return;
    }
    marked[asID(r, c, cols)] = true;
    //up
    if (r - 1 >= 0 && !marked[asID(r - 1, c, cols)]) {
      dfsBoard(r - 1, c, board, prefix, marked, words, steps);
    }
    //up-right
    if (r - 1 >= 0 && c + 1 < cols && !marked[asID(r - 1, c + 1, cols)]) {
      dfsBoard(r - 1, c + 1, board, prefix, marked, words, steps);
    }
    //right
    if (c + 1 < cols && !marked[asID(r, c + 1, cols)]) {
      dfsBoard(r, c + 1, board, prefix, marked, words, steps);
    }
    //down-right
    if (c + 1 < cols && r + 1 < rows && !marked[asID(r + 1, c + 1, cols)]) {
      dfsBoard(r + 1, c + 1, board, prefix, marked, words, steps);
    }
    //down
    if (r + 1 < rows && !marked[asID(r + 1, c, cols)]) {
      dfsBoard(r + 1, c, board, prefix, marked, words, steps);
    }
    //down-left
    if (r + 1 < rows && c - 1 >= 0 && !marked[asID(r + 1, c - 1, cols)]) {
      dfsBoard(r + 1, c - 1, board, prefix, marked, words, steps);
    }
    //left
    if (c - 1 >= 0 && !marked[asID(r, c - 1, cols)]) {
      dfsBoard(r, c - 1, board, prefix, marked, words, steps);
    }
    //up-left
    if (r - 1 >= 0 && c - 1 >= 0 && !marked[asID(r - 1, c - 1, cols)]) {
      dfsBoard(r - 1, c - 1, board, prefix, marked, words, steps);
    }
    if (prefix.length() > 2 && dictionary.contains(prefix.toString())) {
      ArrayList<Integer> path = new ArrayList<Integer>();
      Enumeration e = steps.elements();
      while (e.hasMoreElements()) {
        path.add((Integer)e.nextElement());
      }
      words.put(prefix.toString(), path);
      //words.add(prefix.toString());
    }
    marked[asID(r, c, cols)] = false;
    steps.pop();
    pop(prefix);
  }

  public static void main(String[] args) throws FileNotFoundException {
    DictionaryLoader l = new DictionaryLoader(args[0]);
    String[] dictionary = l.dictionary();
    Solver solver = new Solver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board).keySet()) {
      System.out.println(word);
      score += solver.scoreOf(word);
    }
    System.out.println("Score = " + score);
  }

  private void push(StringBuilder stack, char letter) {
    stack.append(letter);
  }

  private void pop(StringBuilder stack) {
    stack.deleteCharAt(stack.length() - 1);
  }
}




