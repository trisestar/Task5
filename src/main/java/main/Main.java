package main;

import entity.HookahBar;
import generator.IdGenerator;


public class Main {
    public static void main(String[] args) {

        HookahBar hookahBar = HookahBar.getInstance();
        for (int i = 0; i < 10; i++) {
            hookahBar.put(IdGenerator.generate());
        }

        hookahBar.end();
    }
}

