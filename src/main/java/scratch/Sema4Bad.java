package scratch;

import java.util.concurrent.Semaphore;

/**
 * @author beevans
 */
public class Sema4Bad {

    private char[] buffer = new char[5];
    private int next = 0;

    private final Semaphore letters = new Semaphore(0);
    private final Semaphore spaces = new Semaphore(5);

    public void addLetter(char c) {
        try {
            spaces.acquire();
        } catch (InterruptedException ignore) {}

        synchronized (this) {
            buffer[next++] = c;
        }
        letters.release();
    }

    @Override
    public String toString() {
        return "Sema4Bad{" + "buffer=" + buffer + ", next=" + next + ", le=" + letters + ", sp=" + spaces + '}';
    }

    public static void main(String[] args) {
        final Sema4Bad s = new Sema4Bad();
        s.addLetter('a');
        s.addLetter('b');
        s.addLetter('c');
        s.addLetter('d');
        s.addLetter('e');
        System.out.println(s);

        Thread t1 = new Thread(() -> s.addLetter('f'));
        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {}
        t1.interrupt();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }
    }

}
