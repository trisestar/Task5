package entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class HookahBar {
    private static final Logger logger = LogManager.getLogger();
    private static final int NUM_OF_HOOKAHS = 3;
    private static final int SIZE_OF_QUEUE = 3;
    Map<Integer, Boolean> hookahs = new HashMap<>();
    Map<Integer, Integer> queue = new HashMap<>();
    Lock lock;
    Condition cond;
    ExecutorService executorService;

    public HookahBar() {
        for (int i = 1; i <= NUM_OF_HOOKAHS; i++) {
            hookahs.put(i, true);
        }
        for (int i = 1; i <= SIZE_OF_QUEUE; i++) {
            queue.put(i, -1);
        }

        lock = new ReentrantLock();
        cond = lock.newCondition();
        executorService = Executors.newFixedThreadPool(SIZE_OF_QUEUE + NUM_OF_HOOKAHS);
    }

    public static HookahBar getInstance() {
        return SingletonHolder.instance;
    }

    public int check() {
        for (int i = 1; i <= NUM_OF_HOOKAHS; i++) {
            if (hookahs.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public int reserve(int id) throws InterruptedException {
        HookahBar room = HookahBar.getInstance();
        lock.lock();
        try {
            while (room.check() == -1) {
                cond.await();
            }
            int number = room.check();
            hookahs.put(number, false);
            executorService.submit(new Visitor(id));
            return number;
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return -1;
    }

    public void put(int id) {
        lock.lock();
        try {
            while (!isQueueNotFull()) {
                cond.await();
            }
            queue.put(queue.size(), id);
            executorService.submit(new Visitor(id));

        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

    }

    public boolean isQueueNotFull() {
        return queue.get(SIZE_OF_QUEUE) == -1;
    }

    public int unreserve(int number) {
        lock.lock();

        hookahs.put(number, true);

        cond.signalAll();
        lock.unlock();
        return number;
    }

    public void unlock() {
        lock.lock();
        cond.signalAll();
        lock.unlock();

    }

    public int push(int id) {
        for (int i = 1; i <= SIZE_OF_QUEUE; i++) {
            if (queue.get(i) == id) {

                if (i == 1) {
                    int response = check();
                    if (response != -1) {
                        queue.put(1, -1);
                    }
                    return response;
                }

                if (isFree(i - 1)) {
                    queue.put(i, -1);
                    queue.put(i - 1, id);

                    return -1;
                }
            }
        }
        return -1;
    }

    public void reserveHookah(int number) {

        hookahs.put(number, false);
    }

    public boolean isFree(int number) {
        return queue.get(number) == -1;
    }

    public void end() {
        executorService.shutdown();
    }

    @Override
    public String toString() {
        return "HookahBar{" +
                "hookahs=" + hookahs +
                ", queue=" + queue +
                '}';
    }

    private static class SingletonHolder {
        private static final HookahBar instance = new HookahBar();
    }
}