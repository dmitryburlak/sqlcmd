package ua.com.juja.sqlcmd.controller.command;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ExitTest {

    private FakeView view = new FakeView();

    @Test
    public void testCanProcessExitString(){
        //given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("exit");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testNotCanProcessExitString(){
        //given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("qwe");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_throwsExitException() {
        //given
        Command command = new Exit(view);

        //when
        try {
            command.process("exit");
            fail("Expected ExitExeption");
        } catch (ExitException e) {
            //donothing
        }
        //then
        assertEquals("до встречи!\n", view.getContent());
        //throws ExitException
    }
}
