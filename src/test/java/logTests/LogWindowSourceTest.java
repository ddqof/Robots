package logTests;

import org.junit.*;
import robots.log.LogEntry;
import robots.log.LogLevel;
import robots.log.LogWindowSource;

import java.util.Iterator;


public class LogWindowSourceTest {
    private LogWindowSource logWindowSource;
    private LogLevel testLogLevel = LogLevel.Debug;

    @Before
    public void setUp() {
        logWindowSource = new LogWindowSource(2);
    }

    @Test
    public void messagesHandlesOverflowCorrectly() {
        String firstMsg = "First";
        String secondMsg = "Second";
        String thirdMsg = "Third";
        logWindowSource.append(testLogLevel, firstMsg);
        logWindowSource.append(testLogLevel, secondMsg);
        logWindowSource.append(testLogLevel, thirdMsg);
        Iterator<LogEntry> entryIterator = logWindowSource.all().iterator();
        LogEntry firstLogEntry = entryIterator.next();
        LogEntry secondLogEntry = entryIterator.next();
        Assert.assertEquals(secondMsg, firstLogEntry.getMessage());
        Assert.assertEquals(testLogLevel, firstLogEntry.getLevel());
        Assert.assertEquals(thirdMsg, secondLogEntry.getMessage());
        Assert.assertEquals(testLogLevel, secondLogEntry.getLevel());
        Assert.assertFalse(entryIterator.hasNext());
    }

    @Test
    public void messagesStoresAsListWhenNoOverflow() {
        String firstLogMsg = "Log1";
        String secondLogMsg = "Log2";
        logWindowSource.append(testLogLevel, firstLogMsg);
        logWindowSource.append(testLogLevel, secondLogMsg);
        Iterator<LogEntry> entryIterator = logWindowSource.all().iterator();
        LogEntry firstLogEntry = entryIterator.next();
        LogEntry secondLogEntry = entryIterator.next();
        Assert.assertEquals(firstLogMsg, firstLogEntry.getMessage());
        Assert.assertEquals(testLogLevel, firstLogEntry.getLevel());
        Assert.assertEquals(secondLogMsg, secondLogEntry.getMessage());
        Assert.assertEquals(testLogLevel, secondLogEntry.getLevel());
    }
}
