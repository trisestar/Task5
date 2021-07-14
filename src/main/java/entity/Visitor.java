package entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Visitor implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    int id;
    int hookahId;
    State state;
    HookahBar room;

    public Visitor(int id) {
        room = HookahBar.getInstance();
        this.state = State.QUEUE;
        this.id = id;
    }


    @Override
    public void run() {
        logger.log(Level.INFO, id + " in queue ");
        int response;
        while (this.state == State.QUEUE) {
            response = room.push(id);
            if (response != -1) {
                hookahId = response;
                this.state = State.HOOKAH;
                room.reserveHookah(response);
            } else {
                try {

                    TimeUnit.MILLISECONDS.sleep(100);
                    room.unlock();
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR, e);
                    Thread.currentThread().interrupt();
                }
            }
        }
        smoke();
    }

    public void smoke() {
        try {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(3) + 2);
            logger.log(Level.INFO, id + " finished smoking hookah number " + hookahId);

            room.unreserve(hookahId);

        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            Thread.currentThread().interrupt();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHookahId() {
        return hookahId;
    }

    public void setHookahId(int hookahId) {
        this.hookahId = hookahId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public HookahBar getRoom() {
        return room;
    }

    public void setRoom(HookahBar room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return id == visitor.id && hookahId == visitor.hookahId && state == visitor.state && Objects.equals(room, visitor.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hookahId, state, room);
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", hookahId=" + hookahId +
                ", state=" + state +
                ", room=" + room +
                '}';
    }
}
