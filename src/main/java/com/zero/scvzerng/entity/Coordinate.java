package com.zero.scvzerng.entity;

/**
 * 查找到的坐标
 * Created by scvzerng on 2017/7/24.
 */
public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

}
