package school.exia.server.model;

public class LogWriter {
    private static LogWriter ourInstance = new LogWriter();

    public static LogWriter getInstance() {
        return ourInstance;
    }

    private LogWriter() {
    }
}
