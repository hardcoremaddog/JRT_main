package com.javarush.task.task34.task3410.model;

import java.awt.*;

public abstract class CollisionObject extends GameObject {

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public CollisionObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        switch (direction) {
            case LEFT: {
                return getX() - Model.FIELD_CELL_SIZE == gameObject.getX() && gameObject.getY() == this.getY();
            }
            case RIGHT: {
                return getX() + Model.FIELD_CELL_SIZE == gameObject.getX() && gameObject.getY() == this.getY();
            }
            case DOWN: {
                return gameObject.getX() == this.getX() && getY() + Model.FIELD_CELL_SIZE == gameObject.getY();
            }
            case UP: {
                return gameObject.getX() == this.getX() && getY() - Model.FIELD_CELL_SIZE == gameObject.getY();
            }
            default:
                return false;
        }
    }
}
