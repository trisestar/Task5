package entity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Visitor implements Runnable{
    //private static final Logger logger = LogManager.getLogger();
    int id;
    int hookahId;
    State state;

    HookahBar room;
    final Random random = new Random();

    public Visitor(int id) {
        room = HookahBar.getInstance();
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
                    TimeUnit.MILLISECONDS.sleep(100);
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
}
