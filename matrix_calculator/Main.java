import java.util.HashMap;
import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Storage storage = new Storage();
    Operation operation = new Operation(storage);
    Scanner userInput = new Scanner(System.in);
    int command = -1;
    
    HashMap<Integer, String> commands = new HashMap<>();
    initializeCommands(commands);

    while (command != 0) {
      System.out.println("\n[0] Exit program\n[1] Create matrix\n[2] Rename matrix\n[3] Delete matrix\n[4] Print matrix\n[5] List saved matrices");
      System.out.println("---");
      System.out.println("[6] Addition\n[7] Subtraction\n[8] Scalar multiplication\n[9] Matrix multiplication\n[10] Transpose\n[11] Determinant\n[12] Inverse\n[13] Trace");
      System.out.println("---");
      System.out.print("Select a command: ");
      
      try {
        command = userInput.nextInt();
        if(command >= 1 && command <= 5)
          storage.getClass().getMethod(commands.get(command)).invoke(storage);
        else if(command >= 6 && command <= 13)
          operation.getClass().getMethod(commands.get(command)).invoke(operation);
        else if(command < 0 || command > 13)
          throw new Exception();
      } catch(Exception e) {
        System.out.println("\nPlease enter a valid command.");
        userInput.nextLine();
        command = -1;
      }
    }
    
    userInput.close();
    System.exit(0);
  }
  
  // Fills the commands HashMap with the names of the supported commands.
  private static void initializeCommands(HashMap<Integer, String> commands) {
    commands.put(1, "createMatrix");
    commands.put(2, "renameMatrix");
    commands.put(3, "deleteMatrix");
    commands.put(4, "printMatrix");
    commands.put(5, "listSavedMatrices");
    commands.put(6, "addition");
    commands.put(7, "subtraction");
    commands.put(8, "scalarMultiplication");
    commands.put(9, "matrixMultiplication");
    commands.put(10, "transpose");
    commands.put(11, "determinant");
    commands.put(12, "inverse");
    commands.put(13, "trace");
  }
}