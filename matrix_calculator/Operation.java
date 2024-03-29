import java.util.Scanner;

public class Operation {
  private Storage storage;

  public Operation(Storage storage) {
    this.storage = storage;
  }

  // Asks the user to create a new matrix or use an existing matrix and returns the matrix the user creates or selects
  private Matrix promptForMatrix() {
    Scanner userInput = new Scanner(System.in);
    String answer = null;
    Matrix matrix = null;

    while (answer == null) {
      System.out.println("Would you like to create a new matrix or use an existing matrix?");
      System.out.println("[N] New\n[E] Existing\n[C] Cancel");

      try {
        answer = userInput.nextLine();
        if (answer.equals("N") || answer.equals("n"))
          matrix = storage.createMatrix();
        else if (answer.equals("E") || answer.equals("e"))
          matrix = getMatrixFromName();
        else if (answer.equals("C") || answer.equals("c"))
          return null;
        else {
          answer = null;
          throw new Exception();
        }
      } catch (Exception e) {
        answer = null;
        System.out.println("Invalid answer. Please enter a valid answer.");
      }
    }

    return matrix;
  }

  // Asks the user for the name of a matrix and returns the matrix with that name or null if the user decides to cancel
  private Matrix getMatrixFromName() {
    String name = null;
    Matrix matrix = null;
    Scanner userInput = new Scanner(System.in);

    while (name == null) {
      System.out.print("\nPlease enter the name of the matrix or enter | to cancel: ");

      try {
        name = userInput.nextLine();
        if (name.equals("|"))
          return null;
        else if (storage.getMatrix(name) == null) {
          name = null;
          System.out.println("No such matrix exists.");
        } else
          matrix = storage.getMatrix(name);
      } catch (Exception e) {
        name = null;
        System.out.println("Invalid name. Please enter a valid name.");
      }
    }

    return matrix;
  }

  // Returns whether matrix1 and matrix2 are the same size or not
  private boolean isSameSize(Matrix matrix1, Matrix matrix2) {
    if (matrix1.getNumberOfRows() == matrix2.getNumberOfRows()
        && matrix1.getNumberOfColumns() == matrix2.getNumberOfColumns())
      return true;

    return false;
  }

  // Prints the matrix that results from an operation
  private void outputResult(Matrix matrix) {
    System.out.println("\nThe resulting matrix:");
    System.out.println(matrix);
    storage.promptToSave(matrix);
  }

  // Asks the user to create or select two matrices and prints the result of performing matrix addition on the two matrices
  public void addition() {
    System.out.println("\nFirst matrix:");
    Matrix matrix1 = promptForMatrix();
    if (matrix1 == null)
      return;

    System.out.println("\nSecond matrix:");
    Matrix matrix2 = promptForMatrix();
    if (matrix2 == null)
      return;

    if (!isSameSize(matrix1, matrix2)) {
      System.out.println("\nThe two matrices are not of the same size.");
      return;
    }

    Matrix result = Matrix.addition(matrix1, matrix2);
    outputResult(result);
  }

  // Asks the user to create or select two matrices and prints the result of performing matrix subtraction on the two matrices
  public void subtraction() {
    System.out.println("\nFirst matrix:");
    Matrix matrix1 = promptForMatrix();
    if (matrix1 == null)
      return;

    System.out.println("\nSecond matrix:");
    Matrix matrix2 = promptForMatrix();
    if (matrix2 == null)
      return;

    if (!isSameSize(matrix1, matrix2)) {
      System.out.println("\nThe two matrices are not of the same size.");
      return;
    }

    Matrix result = Matrix.subtraction(matrix1, matrix2);
    outputResult(result);
  }

  // Asks the user to create or select a matrix and enter a scalar value, then prints the result of performing scalar multiplication using the matrix and the scalar value
  public void scalarMultiplication() {
    System.out.println("\nMatrix:");
    Matrix matrix = promptForMatrix();
    if (matrix == null)
      return;

    Double scalar = null;
    Scanner userInput = new Scanner(System.in);

    while (scalar == null) {
      System.out.print("\nEnter a scalar value: ");

      try {
        scalar = userInput.nextDouble();
      } catch (Exception e) {
        scalar = null;
        System.out.println("Please enter a valid real number.");
      }
    }

    Matrix result = matrix.scalarMultiplication(scalar);
    outputResult(result);
  }

  // Asks the user to create or select two matrices and prints the result of performing matrix multiplication on the two matrices
  public void matrixMultiplication() {
    System.out.println("\nFirst matrix:");
    Matrix matrix1 = promptForMatrix();
    if (matrix1 == null)
      return;

    System.out.println("\nSecond matrix:");
    Matrix matrix2 = promptForMatrix();
    if (matrix2 == null)
      return;

    if (matrix1.getNumberOfColumns() != matrix2.getNumberOfRows()) {
      System.out.println("\nThe number of columns in the first matrix must equal the number of rows"
          + " in the second matrix");
      return;
    }

    Matrix result = Matrix.matrixMultiplication(matrix1, matrix2);
    outputResult(result);
  }

  // Asks the user to create or select a matrix and prints the result of transposing the matrix
  public void transpose() {
    System.out.println("\nMatrix:");
    Matrix matrix = promptForMatrix();
    if (matrix == null)
      return;

    Matrix result = matrix.transpose();
    outputResult(result);
  }

  // Asks the user to create or select a matrix and prints the determinant of the matrix
  public void determinant() {
    System.out.println("\nMatrix:");
    Matrix matrix = promptForMatrix();
    if (matrix == null)
      return;

    if (matrix.getNumberOfRows() != matrix.getNumberOfColumns()) {
      System.out.println("\nThe matrix must be a square matrix.");
      return;
    }

    double result = matrix.determinant();
    System.out.println("\nDeterminant: " + result);
  }
  
  // Asks the user to create or select a matrix and prints the inverse of the matrix
  public void inverse() {
    System.out.println("\nMatrix:");
    Matrix matrix = promptForMatrix();
    if (matrix == null)
      return;

    if (matrix.getNumberOfRows() != matrix.getNumberOfColumns()) {
      System.out.println("\nThe matrix must be a square matrix.");
      return;
    }
    
    if(matrix.determinant() == 0) {
      System.out.println("\nCannot invert a singular matrix.");
      return;
    }

    Matrix result = matrix.inverse();
    outputResult(result);
  }
  
  // Asks the user to create or select a matrix and prints the trace of the matrix
  public void trace() {
    System.out.println("\nMatrix:");
    Matrix matrix = promptForMatrix();
    if (matrix == null)
      return;

    if (matrix.getNumberOfRows() != matrix.getNumberOfColumns()) {
      System.out.println("\nThe matrix must be a square matrix.");
      return;
    }

    double result = matrix.trace();
    System.out.println("\nTrace: " + result);
  }
}
