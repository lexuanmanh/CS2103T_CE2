package TextBuddy;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextBuddyTests {
    
    public void testTextBuddy() {
        //added test for sortText() method.
        TextBuddy.executeCommand("add test 1");
        TextBuddy.executeCommand("add test 3");
        TextBuddy.executeCommand("add test 2");
        assertEquals("All content was sorted", TextBuddy.executeCommand("sort"));
        assertEquals("1: test 1\n2: test 2\n3: test 3\n", TextBuddy.executeCommand("display"));
        TextBuddy.executeCommand("add test 0");
        TextBuddy.executeCommand("delete 1");
        assertEquals("All content was sorted", TextBuddy.executeCommand("sort"));
        assertEquals("1: test 0\n2: test 2\n3: test 3\n", TextBuddy.executeCommand("display"));
    }

}
