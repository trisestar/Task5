package entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Visitor2 implements Runnable{
    //private static final Logger logger = LogManager.getLogger();
    int id;
    int hookahId;
    State state;

    Room2 room;
    final Random random = new Random();

    public Visitor2(int id) {
        room = Room2.getInstance();
        this.state = State.QUEUE;
        this.id = id;
    }


    @Override
    public void run() {
        int response;
        while (this.state == State.QUEUE){
            response = room.push(id);
            if (response != -1){
                hookahId = response;
                this.state = State.HOOKAH;
                room.reserveHookah(response);
            } else {
                try {
                    //System.out.println(id + " ожидает");
                    TimeUnit.MILLISECONDS.sleep(1);
                    room.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        smoke();
    }

    public void smoke(){
        try {
            //System.out.println(id + " начал курить кальян номер "+ hookahId);
            TimeUnit.SECONDS.sleep(random.nextInt(3)+2);
            System.out.println(id + " закончил курить кальян номер "+ hookahId);
            room.unreserve(hookahId);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
};
