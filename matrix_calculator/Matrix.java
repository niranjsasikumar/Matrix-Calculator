public class Matrix {
  private String name;
  private int numberOfRows;
  private int numberOfColumns;
  private double[][] matrix;

  public Matrix(String name, int numberOfRows, int numberOfColumns) {
    this.name = name;
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.matrix = new double[numberOfRows][numberOfColumns];
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumberOfRows() {
    return numberOfRows;
  }

  public int getNumberOfColumns() {
    return numberOfColumns;
  }

  // Sets the row of the Matrix object at the index specified by rowNumber to the array row
  public void setRow(int rowNumber, double[] row) {
    this.matrix[rowNumber] = row;
  }

  // Returns a String that depicts the Matrix object
  public String toString() {
    String result = "";

    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        if(this.matrix[row][column] >= 0)
          result += String.format(" %.3f   ", matrix[row][column]);
        else
          result += String.format("%.3f   ", matrix[row][column]);
      }

      if (row != numberOfRows - 1)
        result += "\n";
    }

    return result;
  }

  // Returns the matrix resulting from the matrix addition of matrix1 and matrix2
  public static Matrix addition(Matrix matrix1, Matrix matrix2) {
    Matrix result = new Matrix("", matrix1.numberOfRows, matrix1.numberOfColumns);

    for (int row = 0; row < matrix1.numberOfRows; row++) {
      for (int column = 0; column < matrix1.numberOfColumns; column++) {
        result.matrix[row][column] = matrix1.matrix[row][column] + matrix2.matrix[row][column];
      }
    }

    return result;
  }

  // Returns the matrix resulting from the matrix subtraction of matrix1 and matrix2
  public static Matrix subtraction(Matrix matrix1, Matrix matrix2) {
    Matrix result = new Matrix("", matrix1.numberOfRows, matrix1.numberOfColumns);

    for (int row = 0; row < matrix1.numberOfRows; row++) {
      for (int column = 0; column < matrix1.numberOfColumns; column++) {
        result.matrix[row][column] = matrix1.matrix[row][column] - matrix2.matrix[row][column];
      }
    }

    return result;
  }
  
  // Returns the matrix resulting from the scalar multiplication of the Matrix object and scalar
  public Matrix scalarMultiplication(double scalar) {
    Matrix result = new Matrix("", this.numberOfRows, this.numberOfColumns);
    
    for(int row = 0; row < this.numberOfRows; row++) {
      for(int column = 0; column < this.numberOfColumns; column++)
        result.matrix[row][column] = scalar * this.matrix[row][column];
    }
    
    return result;
  }
  
  // Returns the matrix resulting from the matrix multiplication of matrix1 and matrix2
  public static Matrix matrixMultiplication(Matrix matrix1, Matrix matrix2) {
    Matrix result = new Matrix("", matrix1.numberOfRows, matrix2.numberOfColumns);
    
    for(int row = 0; row < result.numberOfRows; row++) {
      for(int column = 0; column < result.numberOfColumns; column++) {
        double value = 0;
        for(int i = 0; i < result.numberOfRows; i ++) {
          value += matrix1.matrix[row][i] * matrix2.matrix[i][column];
        }
        result.matrix[row][column] = value;
      }
    }
    
    return result;
  }
  
  // Returns the matrix resulting from transposing the Matrix object
  public Matrix transpose() {
    Matrix result = new Matrix("", this.numberOfColumns, this.numberOfRows);
    
    for(int row = 0; row < this.numberOfRows; row++) {
      for(int column = 0; column < this.numberOfColumns; column++)
        result.matrix[column][row] = this.matrix[row][column];
    }
    
    return result;
  }
  
  // Returns the determinant of the Matrix object
  public double determinant() {
    if(this.numberOfRows == 1)
      return this.matrix[0][0];
    
    double result = 0;
    
    for(int column = 0; column < this.numberOfColumns; column++) {
      if(column % 2 == 0)
        result += this.matrix[0][column] * this.cofactor(0, column).determinant();
      else
        result -= this.matrix[0][column] * this.cofactor(0, column).determinant();
    }
    
    return result;
  }
  
  // Returns the cofactor matrix of the Matrix object
  private Matrix cofactor(int row, int column) {
    Matrix result = new Matrix("", this.numberOfRows - 1, this.numberOfColumns - 1);
    
    for(int r = 0; r < this.numberOfRows; r++) {
      for(int c = 0; c < this.numberOfColumns; c++) {
        if(r < row && c < column)
          result.matrix[r][c] = this.matrix[r][c];
        else if(r < row && c > column)
          result.matrix[r][c - 1] = this.matrix[r][c];
        else if(r > row && c < column)
          result.matrix[r - 1][c] = this.matrix[r][c];
        else if(r > row && c > column)
          result.matrix[r - 1][c - 1] = this.matrix[r][c];
      }
    }
    
    return result;
  }
  
  // Returns the inverse of the Matrix object
  public Matrix inverse() {
    Matrix adjugate = new Matrix("", this.numberOfRows, this.numberOfColumns);
    
    for(int i = 0; i < this.numberOfRows; i++) {
      for(int j = 0; j < this.numberOfColumns; j++) {
        adjugate.matrix[i][j] = Math.pow(-1, i + j) * this.cofactor(j, i).determinant();
      }
    }
    
    return adjugate.scalarMultiplication(1 / this.determinant());
  }
  
  // Returns the trace of the Matrix object
  public double trace() {
    double result = 0;
    
    for(int i = 0; i < this.numberOfRows; i++)
      result += this.matrix[i][i];
    
    return result;
  }
}
