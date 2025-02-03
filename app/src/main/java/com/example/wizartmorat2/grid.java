package com.example.wizartmorat2;

import static java.lang.Math.abs;

public class grid {

    private int[][] array;
    private int rows;
    private int columns;

@SuppressWarnings("unchecked")
public grid(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    array = new int[rows][columns];}
public void set(int row, int column, int value) {
    if (isValidIndex(row, column)) {
        array[row][column] = value;
    } else {
        throw new IndexOutOfBoundsException("Invalid index");
    }
}
public String[][] toStringArray() {
    String[][] stringArray = new String[rows][columns];
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            stringArray[i][j] = String.valueOf(array[i][j]);
        }
    }
    return stringArray;
}
public void updateScore(int row, int column,int oldscore, int wins,int predictions) {
    if (wins == predictions) {
        array[row][column] = oldscore+ 20 + wins * 10;
    } else {
        int delta = abs(predictions - wins);
        array[row][column] = oldscore - delta * 10;
    }
}

public int get(int row, int column) {
    if (isValidIndex(row, column)) {
        return array[row][column];
    } else {
        throw new IndexOutOfBoundsException("Invalid index");
    }
}

public int getRows() {
    return rows;
}

public int getColumns() {
    return columns;
}

private boolean isValidIndex(int row, int column) {
    return row >= 0 && row < rows && column >= 0 && column < columns;
    }
}

