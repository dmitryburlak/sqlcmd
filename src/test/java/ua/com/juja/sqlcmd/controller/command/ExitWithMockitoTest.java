package ua.com.juja.sqlcmd.controller.command;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ExitWithMockitoTest {

    private View view = Mockito.mock(View.class);

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
    public void testProcessExitCommand_throwsExitException(){
        //given
        Command command = new Exit(view);

        //when
        try {
            command.process("exit");
            Assert.fail("Expected ExitException");
        } catch (ExitException e) {
            //donothing
        }
        //then
        Mockito.verify(view).write("до встречи!");

    }
}
