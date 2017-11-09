package school.exia.server.model;

import java.io.*;
import java.util.concurrent.Semaphore;

public class LogWriter {
    private static LogWriter ourInstance = new LogWriter();
    private static Semaphore available = new Semaphore(1,true);
    private static Writer writer;

    public static LogWriter getInstance() {
        return ourInstance;
    }

    private LogWriter() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                     new FileOutputStream("log.txt"), "UTF-8"));
        } catch (Exception e) {
        }
    }

    public void log(String s) {

        available.tryAcquire();

        try {
            writer.write(s + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        available.release();
    }

}
