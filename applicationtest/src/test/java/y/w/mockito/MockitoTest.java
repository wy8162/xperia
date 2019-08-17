package y.w.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {
    class AClass {
        private int number;

        public AClass(int number) {
            this.number = number;
        }

        public AClass() {
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void runSomething(String message) {
            System.out.println(message);
        }
    }

    /**
     * Mock vs Spy
     * If you want to be safe and avoid calling external services and just want to test the logic inside of the unit,
     * then use mock.
     *
     * If you want to call external service and perform calling of real dependency, or simply say, you want to run
     * the program as it is and just stub specific methods, then use spy
     */
    @Mock
    private ArrayList<String> mockList;

    @Spy
    private ArrayList<String> spyList;

    @Test
    @SuppressWarnings("unchecked")
    public void testMockVsSpy()
    {
        String message = "by default, calling method of a mock object does nothing";
        mockList.add(message);

        Mockito.verify(mockList).add(message);
        assertEquals(0, mockList.size());
        assertNull(mockList.get(0));

        String spyMessage = "Instead, calling method of a spied object calls the real method";
        spyList.add(spyMessage);

        Mockito.verify(spyList).add(spyMessage);
        assertEquals(1, spyList.size());
        assertEquals(spyMessage, spyList.get(0));
    }

    @Test
    public void testMockWithStub() {
        //try stubbing a method
        String expected = "Mock 100";
        when(mockList.get(100)).thenReturn(expected);

        assertEquals(expected, mockList.get(100));
    }

    @Test
    public void testSpyWithStub() {
        //stubbing a spy method will result the same as the mock object
        String expected = "Spy 100";
        //take note of using doReturn instead of when
        doReturn(expected).when(spyList).get(100);

        assertEquals(expected, spyList.get(100));
    }
}
