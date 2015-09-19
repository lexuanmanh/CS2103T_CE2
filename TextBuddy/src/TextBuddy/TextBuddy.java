package TextBuddy;

import java.util.ArrayList;
import java.util.Scanner;
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
    
    private final String COMMAND_ADD = "add";
    private final String COMMAND_DELETE = "delete";
    private final String COMMAND_DISPLAY = "display";
    private final String COMMAND_SORT = "sort";
    private final String COMMAND_SEARCH = "search";
    private final String COMMAND_CLEAR = "clear";
    private final String COMMAND_EXIT = "exit";
    
    private final String MESSAGE_EMPTY = "";
    private final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use";
    private final String MESSAGE_COMMAND = "Command";
    private final String MESSAGE_INVALID_COMMAND = "Invalid command";
    private final String MESSAGE_TEXT_ADDED = "Added to %s: " + '"' + "%s" + '"';
    
    
    private final FIRST_ELEMENT = 0;
    
    private static String myFileName;
    private static ArrayList<String> content = new ArrayList<String>();
    
    private final PrinterWrite writer;
    private final Scanner scanner = new Scanner(System.in);
    
    
    public void main(String[] argv) {
        
        myFileName = argv[FIRST_ELEMENT];
        openFile(myFileName);
        manipulateTextFile();
        
    }
    
    boolean checkCommandHasWord(String userCommand) {
        for(int i=0; i<userCommand.size(); i++) {
            if (userCommand.charAt(i) != ' ') {
                return true;
            }
        }
        return false;
    }
    
    public String executeCommand(String userCommand) {
        
        if (!checkCommandHasWord) {
            return MESSAGE_INVALID_COMMAND;
        }
        
        String command = getCommand(userCommand);
        if (command == COMMAND_ADD) {
            return addText(userCommand);
        } else if (command == COMMAND_DISPLAY) {
            return displayText(userCommand);
        } else if (command == COMMAND_DELETE) {
            return deleteText(userCommand);
        } else if (command == COMMAND_CLEAR) {
            return clearText(userCommand);
        } else if (command == COMMAND_SORT) {
            return sortText(userCommand);
        } else if (command == COMMAND_SEARCH) {
            return searchText(userCommand);
        } else if (command == COMMAND_EXIT) {
            return exitText(userCommand);
        } else {
            return MESSAGE_INVALID_COMMAND;
        }
        
    }
    
    private void manipulateTextFile() {
        displayMessageToUserWithNewLine(MESSAGE_WELCOME, myFileName);
        while (true) {
            displayMessageToUser(MESSAGE_COMMAND);
            String userCommand = getUserCommand();
            String displayMessage = executeCommand(userCommand);
            displayMessageToUserWithNewLine(userCommand);
        }
    }
    
    private String getUserCommand() {
        return scanner.nextLine();
    }
    
    private void displayMessageToUser(String displayMessage) {
        System.out.print(displayMessage, firstArgument, secondArgument);
    }
                                 
    private void displayMessageToUserWithNewLine(String displayMessage) {
        System.out.println(displayMessage, firstArgument, secondArgument);
    }
    
    private void openFile(String fileName) {
        try {
            writer = new PrinterWrite(fileName);
        } catch (FileNotFoundExeption e) {
            System.out.printfln(e.getMessage());
        }
    }
    
    private String getCommand(String userCommand) {
        
        String command = userCommand.split(" ")[FIRST_ELEMENT];
        return command;
        
    }
    
    private String getCommandArgument(String userCommand) {
        String commandArgument = "";
        String command = getCommand(userCommand);
        for(int i=command.size()+1; i<commandArgument.size(); i++) {
            commandArgument = commandArgument + command.charAt(i);
        }
        return commandArgument;
    }

}
