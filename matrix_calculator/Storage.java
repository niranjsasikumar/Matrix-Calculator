import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
  private ArrayList<Matrix> matrices; // Stores the matrices saved by the user

  public Storage() {
    this.matrices = new ArrayList<>();
  }

  // Returns the matrix with the name that matches the String name
  public Matrix getMatrix(String name) {
    for (Matrix matrix : matrices) {
      if (matrix.getName().equals(name))
        return matrix;
    }

    return null;
  }

  // Asks the user if they want to save the Matrix object matrix
  public void promptToSave(Matrix matrix) {
    Scanner userInput = new Scanner(System.in);
    String answer = null;

    while (answer == null) {
      System.out.println("\nWould you like to save this matrix?\n[Y] Yes\n[N] No");

      try {
        answer = userInput.nextLine();
        if (answer.equals("Y") || answer.equals("y")) {
          setName(matrix, "Please enter a name for the matrix (alphanumeric characters only): ", "Matrix saved successfully.");
          return;
        } else if (answer.equals("N") || answer.equals("n"))
          return;
        else {
          throw new Exception();
        }
      } catch (Exception e) {
        System.out.println("Invalid answer. Please enter a valid answer.");
        answer = null;
      }
    }
  }

  // Asks the user to enter a name for the matrix that they want to save and adds it to the list of saved matrices
  private void setName(Matrix matrix, String prompt, String success) {
    Scanner userInput = new Scanner(System.in);
    String name = null;

    while (name == null) {
      System.out.print("\n" + prompt);

      try {
        name = userInput.nextLine();

        if (getMatrix(name) != null) {
          overwriteMatrix(getMatrix(name), matrix, name, success);
          return;
        }

        if (isValidName(name)) {
          matrix.setName(name);
          matrices.add(matrix);
          System.out.println("\n" + success);
          return;
        } else {
          System.out.println("Invalid name. Please enter an alphanumeric name.");
          name = null;
        }
      } catch (Exception e) {
        System.out.println("Invalid name. Please enter an alphanumeric name.");
        name = null;
      }
    }
  }

  // Asks the user if they want to overwrite an existing matrix with the matrix they want to save having the same name
  private void overwriteMatrix(Matrix oldMatrix, Matrix newMatrix, String name, String success) {
    String answer = null;
    Scanner userInput = new Scanner(System.in);

    System.out.println("\nA matrix with this name already exists. Would you like to overwrite it?\n[Y] Yes\n[N] No");

    try {
        answer = userInput.nextLine();
        if (answer.equals("Y") || answer.equals("y")) {
          matrices.remove(oldMatrix);
          newMatrix.setName(name);
          matrices.add(newMatrix);
          System.out.println("\n" + success);
          return;
        } else if (answer.equals("N") || answer.equals("n"))
          return;
        else {
          throw new Exception();
        }
      } catch (Exception e) {
        System.out.println("Invalid answer. Please enter a valid answer.");
        answer = null;
      }
  }

  // Prompts the user for the name of a matrix and returns the Matrix object having that name or returns null if there is no matrix with that name
  private Matrix getMatrixFromName(String prompt) {
    String name = null;
    Matrix matrix = null;
    Scanner userInput = new Scanner(System.in);

    while (name == null) {
      System.out.print("\n" + prompt);

      try {
        name = userInput.nextLine();
        if (name.equals("|"))
          return null;
        else if (getMatrix(name) == null) {
          name = null;
          System.out.println("No such matrix exists.");
        } else
          matrix = getMatrix(name);
      } catch (Exception e) {
        name = null;
        System.out.println("Invalid name. Please enter a valid name.");
      }
    }

    return matrix;
  }

  // Returns whether the String name is a valid name containing only alphanumeric characters
  private boolean isValidName(String name) {
    for (int i = 0; i < name.length(); i++) {
      if (!Character.isLetterOrDigit(name.charAt(i)))
        return false;
    }

    return true;
  }

  // Prompts the user to create a new matrix and returns the resulting Matrix object
  public Matrix createMatrix() {
    int numberOfRows = getValidNumber("\nEnter the number of rows: ");
    int numberOfColumns = getValidNumber("Enter the number of columns: ");

    Matrix result = new Matrix("", numberOfRows, numberOfColumns);
    fillMatrix(result);

    System.out.println("\nYour new matrix:");
    System.out.println(result);
    promptToSave(result);

    return result;
  }

  // Prompts the user to enter a valid integer between 1 and 15 and returns the value
  private int getValidNumber(String prompt) {
    int num = -1;
    Scanner userInput = new Scanner(System.in);

    while (num == -1) {
      System.out.print(prompt);

      try {
        num = userInput.nextInt();
        if (num < 1 || num > 15) {
          System.out.println("Please enter a number between 1 (inclusive) and 15 (inclusive).");
          num = -1;
        }
      } catch (Exception e) {
        System.out.println("Please enter a valid number.");
        userInput.next();
        num = -1;
      }
    }

    return num;
  }

  // Prompts the user to enter the values in each row of the matrix and sets each row of the matrix to the entered values
  private void fillMatrix(Matrix matrix) {
    Scanner userInput = new Scanner(System.in);

    for (int rowNumber = 0; rowNumber < matrix.getNumberOfRows(); rowNumber++) {
      double[] row = null;

      while (row == null) {
        System.out.println("Enter the values in row " + (rowNumber + 1) + " of the matrix"
            + " separated by spaces:");
        String[] input = userInput.nextLine().split("\\s+");

        if (input.length != matrix.getNumberOfColumns()) {
          System.out.println("Please enter the correct number of values.");
          continue;
        }

        double[] newRow = new double[matrix.getNumberOfColumns()];
        try {
          for (int column = 0; column < matrix.getNumberOfColumns(); column++)
            newRow[column] = Double.parseDouble(input[column]);
        } catch (Exception e) {
          System.out.println("Please enter valid integer or decimal values.");
          newRow = null;
        }

        row = newRow;
      }

      matrix.setRow(rowNumber, row);
    }
  }

  // Prompts the user for the matrix they want to rename and the new name, then renames the matrix to the new name
  public void renameMatrix() {
    Matrix matrix =
        getMatrixFromName("Please enter the name of the matrix to rename or enter | to cancel: ");

    if (matrix == null)
      return;
    
    setName(matrix, "Please enter the new name for the matrix (alphanumeric characters only): ",
        "Matrix renamed successfully.");
  }

  // Prompts the user for the name of the matrix they want to delete and deletes the corresponding matrix
  public void deleteMatrix() {
    Matrix matrix =
        getMatrixFromName("Please enter the name of the matrix to delete or enter | to cancel: ");

    if (matrix == null)
      return;
    
    matrices.remove(matrix);
    matrix = null;
    System.out.println("\nMatrix deleted successfully.");
  }

  // Prompts the user for the name of the matrix they want to print and prints the corresponding matrix
  public void printMatrix() {
    Matrix matrix =
        getMatrixFromName("Please enter the name of the matrix to print or enter | to cancel: ");
    
    if (matrix == null)
      return;
    
    System.out.println("\n" + matrix);
  }

  // Prints the names of the matrices that the user has saved so far
  public void listSavedMatrices() {
    System.out.println("\nSaved matrices:");
    
    for(Matrix matrix : matrices)
      System.out.println(matrix.getName());
  }
}
