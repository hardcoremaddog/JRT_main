package com.javarush.task.task21.task2113;

import java.util.*;

public class Hippodrome {
    private List<Horse> horses;
    public static Hippodrome game;

    public Hippodrome(List horses) {
        this.horses = horses;
    }

    public Hippodrome() {

    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                move();
                print();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move() {
        for (Horse horse : horses) {
            horse.move();
        }
    }

    public void print() {
        for (Horse horse : horses) {
            horse.print();
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }

    public Horse getWinner() {
        Map<Horse, Double> map = new HashMap<>();

        for (Horse horse : horses) {
            map.put(horse, horse.getDistance());
        }

        Object maxEntry = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();

        return (Horse) maxEntry;
    }

    public void printWinner() {
        System.out.println("Winner is " + getWinner().getName() + "!");
    }

    public static void main(String[] args) throws InterruptedException {
        game = new Hippodrome();
        game.horses = new ArrayList<>();

        Horse horse1 = new Horse("mr.Muscle", 3, 0);
        Horse horse2 = new Horse("WhitePower", 3, 0);
        Horse horse3 = new Horse("Rocket", 3, 0);

        game.horses.add(horse1);
        game.horses.add(horse2);
        game.horses.add(horse3);

        game.run();
        game.printWinner();
    }
}