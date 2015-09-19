
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.*;

/**
 * This class is used to take command line and manipulate text in a file.
 * When the file name is given, created new file, even if it's already existed.
 * Only updated contents of file when user exits the programe.
 * Sample input commands:
 
 Welcome to TextBuddy. myfile.text is ready for use
 Command: add test 1
 Added to myfile.txt: "test 1"
 Command: add test 2
 Added to myfile.txt: "test 2"
 Command: display
 1: test 1
 2: test 2
 Command: delete 1
 Deleted from myfile.txt: "test 1"
 Command: display
 1: test 2
 Command: add test 1
 Added to myfile.txt: "test 1"
 Command: display
 1: test 2
 2: test 1
 Command: sort
 All content was sorted
 Command: display
 1: test 1
 2: test 2
 Command: clear
 All content deleted from myfile.txt
 Command: display
 myfile.txt is empty!
 Command: exit
 */

public class TextBuddy {
    
    private static String COMMAND_ADD = "add";
    private static String COMMAND_DELETE = "delete";
    private static String COMMAND_DISPLAY = "display";
    private static String COMMAND_SORT = "sort";
    private static String COMMAND_SEARCH = "search";
    private static String COMMAND_CLEAR = "clear";
    private static String COMMAND_EXIT = "exit";
    
    private static String MESSAGE_EMPTY = "";
    private static String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use";
    private static String MESSAGE_COMMAND = "Command: ";
    private static String MESSAGE_INVALID_COMMAND = "Invalid command";
    private static String MESSAGE_TEXT_ADDED = "Added to %s: " + '"' + "%s" + '"';
    private static String MESSAGE_FILE_EMPTY = "%s is empty!";
    private static String MESSAGE_CLEARED = "All content deleted from %s";
    private static String MESSAGE_DELETED = "Deleted from %s: " + '"' + "%s" + '"';
    private static String MESSAGE_SORTED = "All content was sorted";
    private static String MESSAGE_NO_RESULT = "No line contains word %s";
    
    
    private static int FIRST_ELEMENT = 0;
    
    private static String myFileName;
    private static ArrayList<String> content = new ArrayList<String>();
    
    private static PrintWriter printWriter;
    private static Scanner scanner = new Scanner(System.in);
    
    
    public static void main(String[] argv) {
        
        myFileName = argv[FIRST_ELEMENT];
        openFile(myFileName);
        manipulateTextFile();
        
    }
    
    //check the command that user typed exist any words or not
    public static boolean checkCommandHasWord(String userCommand) {
        for(int i=0; i<userCommand.length(); i++) {
            if (userCommand.charAt(i) != ' ') {
                return true;
            }
        }
        return false;
    }
    
    //execute command that user typed
    public static String executeCommand(String userCommand) {
        
        if (!checkCommandHasWord(userCommand)) {
            return MESSAGE_INVALID_COMMAND;
        }
        
        String command = getCommandType(userCommand);
        if (command.equals(COMMAND_ADD)) {
            return addText(userCommand);
        } else if (command.equals(COMMAND_DISPLAY)) {
            return displayText(userCommand);
        } else if (command.equals(COMMAND_DELETE)) {
            return deleteText(userCommand);
        } else if (command.equals(COMMAND_CLEAR)) {
            return clearText(userCommand);
        } else if (command.equals(COMMAND_SORT)) {
            return sortText(userCommand);
        } else if (command.equals(COMMAND_SEARCH)) {
            return searchText(userCommand);
        } else if (command.equals(COMMAND_EXIT)) {
            exitText(userCommand);
            return "";
        } else {
            return MESSAGE_INVALID_COMMAND;
        }
    }
    
    // This function is to add text to file
    private static String addText(String userCommand) {
        String commandArgument = getCommandArgument(userCommand);
        content.add(commandArgument);
        return String.format(MESSAGE_TEXT_ADDED, myFileName, commandArgument);
    }
    
    // This function is to display text in file
    private static String displayText(String userCommand) {
        String displayMessage = "";
        for(int i=0; i<content.size(); i++) {
            displayMessage += (i+1) + ": " + content.get(i) + "\n";
        }
        if (content.isEmpty()) {
            return String.format(MESSAGE_FILE_EMPTY, myFileName);
        } else {
            return displayMessage;
        }
    }
    
    // This function is to delete a text in file
    private static String deleteText(String userCommand) {
        String commandArgument = getCommandArgument(userCommand);
        if (checkCommandHasWord(commandArgument)) {
            int lineToDelete = Integer.parseInt(commandArgument)-1;
            String contentDeleted = content.get(lineToDelete);
            content.remove(lineToDelete);
            return String.format(MESSAGE_DELETED, myFileName, contentDeleted);
        }
        return MESSAGE_INVALID_COMMAND;
    }
    
    // This function is to clear all content in file
    private static String clearText(String userCommand) {
        content.clear();
        return String.format(MESSAGE_CLEARED, myFileName);
    }
    
    // This function is to exit program, write content before exit
    private static void exitText(String userCommand) {
        for(int i=0; i<content.size(); i++) {
            printWriter.println(content.get(i));
        }
        printWriter.close();
        System.exit(0);
    }
    
    //this function is to sort file text
    private static String sortText(String userCommand) {
        Collections.sort(content);
        return MESSAGE_SORTED;
    }
    
    //this function is used to search a text in file
    private static String searchText(String userCommand) {
        String displayMessage = "";
        String commandArgument = getCommandArgument(userCommand);
        for(int i=0; i<content.size(); i++) {
            String textContent = content.get(i);
            if (textContent.contains(commandArgument)) {
                displayMessage += (i+1) + ": " + textContent + "\n";
            }
        }
        if (displayMessage.equals("")) {
            return String.format(MESSAGE_NO_RESULT, commandArgument);
        }
        return displayMessage;
    }
    
    //manipulate the command that user typed, display message for each command to user.
    private static void manipulateTextFile() {
        displayMessageToUserWithNewLine(String.format(MESSAGE_WELCOME, myFileName));
        while (true) {
            displayMessageToUser(MESSAGE_COMMAND);
            String userCommand = getUserCommand();
            String displayMessage = executeCommand(userCommand);
            displayMessageToUserWithNewLine(displayMessage);
        }
    }
    
    //get the command from user
    private static String getUserCommand() {
        return scanner.nextLine();
    }
    
    //display message for user, without new line
    private static void displayMessageToUser(String displayMessage) {
        System.out.print(displayMessage);
    }
    
    //display message for user with new line
    private static void displayMessageToUserWithNewLine(String displayMessage) {
        System.out.println(displayMessage);
    }
    
    //open file to manipulate
    private static void openFile(String fileName) {
        try {
            printWriter = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //get command type that user typed
    private static String getCommandType(String userCommand) {
        
        String command = "";
        for(int i=0; i<userCommand.length(); i++) {
            if (userCommand.charAt(i) == ' ') {
                break;
            } else {
                command += userCommand.charAt(i);
            }
        }
        return command;
        
    }
    
    //get argument for command type: add, delete, search,
    private static String getCommandArgument(String userCommand) {
        String commandArgument = "";
        String command = getCommandType(userCommand);
        for(int i=command.length()+1; i<userCommand.length(); i++) {
            commandArgument = commandArgument + userCommand.charAt(i);
        }
        return commandArgument;
    }

}
