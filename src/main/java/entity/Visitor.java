package entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Visitor implements Runnable{
    //private static final Logger logger = LogManager.getLogger();
    int id;
    int hookahId;

    Room room = Room.getInstance();
    final Random random = new Random();

    public Visitor(int id, int hookahId) {
        this.id = id;
        this.hookahId = hookahId;
    }


    @Override
    public void run() {
        try {
            System.out.println(id + " начал курить кальян номер "+ hookahId);
            TimeUnit.SECONDS.sleep(random.nextInt(3)+2);
            System.out.println(id + " закончил курить кальян номер "+ hookahId);
            room.unreserve(hookahId);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
};
