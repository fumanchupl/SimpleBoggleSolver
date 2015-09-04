package com.fumanchu.simplebogglesolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ***********************************************************************
 * Compilation: javac BoggleBoard.java Execution: java BoggleBoard Dependencies:
 * StdRandom.java
 *
 * A data type for Boggle boards.
 *
 ************************************************************************
 */
public class BoggleBoard {

  private final int M;        // number of rows
  private final int N;        // number of columns
  private char[][] board;     // the M-by-N array of characters
  private static final String alphabet = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPQRSŚTUVWXYZŹŻ";
  private static final double[] frequencies = { //Polish letters freq. table
    /*A*/0.10503, /*Ą*/0.00698, /*B*/0.01739, /*C*/0.03894, /*Ć*/0.00742,
    /*D*/0.03724, /*E*/0.07352, /*Ę*/0.01036, /*F*/0.00142, /*G*/0.01730,
    /*H*/0.01014, /*I*/0.08328, /*J*/0.01835, /*K*/0.02752, /*L*/0.02563,
    /*Ł*/0.02108, /*M*/0.02514, /*N*/0.06237, /*Ń*/0.00361, /*O*/0.06667,
    /*Ó*/0.01140, /*P*/0.02444, /*Q*/0.00000, /*R*/0.05242, /*S*/0.05223,
    /*Ś*/0.00813, /*T*/0.02474, /*U*/0.02061, /*V*/0.00011, /*W*/0.05812,
    /*X*/0.00003, /*Y*/0.03205, /*Z*/0.04851, /*Ź*/0.00077, /*Ż*/0.00705
  };

  /**
   * Initializes a board from the given filename.
   *
   * @param filename the name of the file containing the Boggle board
   */
  public BoggleBoard(String filename) {
    BufferedReader br = null;
    int m = 4,n = 4;
    try {
      br = new BufferedReader(new FileReader(filename));
      String[] line = br.readLine().split("\\s");
      m = Integer.parseInt(line[0].replaceAll("\\D", ""));
      n = Integer.parseInt(line[1].replaceAll("\\D", ""));
      board = new char[m][n];
      for (int i = 0; i < m; ++i) {
        line = br.readLine().toUpperCase().split("\\s");
        for (int j = 0; j < n; ++j) {
          String letter = line[j];
          if (letter.length() != 1) {
            throw new IllegalArgumentException("invalid character: " + letter);
          } else if (alphabet.indexOf(letter) == -1) {
            throw new IllegalArgumentException("invalid character: " + letter);
          } else {
            board[i][j] = letter.charAt(0);
          }
        }
      }
    } catch (NumberFormatException x) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, x.getMessage());
    } catch (java.io.IOException e) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, String.format("Can't read %s", filename));
    } finally {
      if (null != br) {
        try {
          br.close();
        } catch (java.io.IOException e) {}
      }
    }
    M = m;
    N = n;
  }

  /**
   * Initializes a random M-by-N board, according to the frequency of letters in
   * the Polish language.
   *
   * @param M the number of rows
   * @param N the number of columns
   */
  public BoggleBoard(int M, int N) {
    this.M = M;
    this.N = N;
    board = new char[M][N];
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        int r = edu.princeton.cs.introcs.StdRandom.discrete(frequencies);
        board[i][j] = alphabet.charAt(r);
      }
    }
  }

  /**
   * Initializes a board from the given 2d character array.
   *
   * @param a the 2d character array
   */
  public BoggleBoard(char[][] a) {
    this.M = a.length;
    this.N = a[0].length;
    board = new char[M][N];
    for (int i = 0; i < M; i++) {
      if (a[i].length != N) {
        throw new IllegalArgumentException("char[][] array is ragged");
      }
      for (int j = 0; j < N; j++) {
        if (alphabet.indexOf(a[i][j]) == -1) {
          throw new IllegalArgumentException("invalid character: " + a[i][j]);
        }
        board[i][j] = a[i][j];
      }
    }
  }

  /**
   * Returns the number of rows.
   *
   * @return number of rows
   */
  public int rows() {
    return M;
  }

  /**
   * Returns the number of columns.
   *
   * @return number of columns
   */
  public int cols() {
    return N;
  }

  /**
   * Returns the letter in row i and column j.
   *
   * @param i the row
   * @param j the column
   * @return the letter in row i and column j.
   */
  public char getLetter(int i, int j) {
    return board[i][j];
  }

  /**
   * Returns a string representation of the board
   *
   * @return a string representation of the board
   */
  public String toString() {
    StringBuilder sb = new StringBuilder(M + " " + N + "\n");
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        sb.append(board[i][j]).append("  ");        
      }
      sb.append("\n");
    }
    return sb.toString().trim();
  }
}
