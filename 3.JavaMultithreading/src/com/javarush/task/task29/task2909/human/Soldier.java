package com.javarush.task.task29.task2909.human;


import java.util.List;

public class Soldier extends Human {

    public Soldier() {
        super();
    }

    public void fight() {

    }

    @Override
    public List<Human> getChildren() {
        return super.getChildren();
    }

    @Override
    public void addChild(Human human) {
        super.addChild(human);
    }

    @Override
    public void removeChild(Human human) {
        super.removeChild(human);
    }

    @Override
    public void setBloodGroup(int code) {
        super.setBloodGroup(code);
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void printSize() {
        super.printSize();
    }

    @Override
    public void live() {
        fight();
    }

    public Soldier(String name, int age) {
        super(name, age);
    }
}
