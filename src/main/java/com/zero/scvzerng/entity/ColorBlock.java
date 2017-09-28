package com.zero.scvzerng.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.util.stream.Stream;

/**
 *
 * Created by scvzerng on 2017/7/24.
 */
public class ColorBlock {
    final int[][] rgbs;
    final private int width;
    final private int height;
    final private Point leftTop;
    final private Point rightTop;
    final private Point leftBottom;
    final private Point rightBottom;
    final private Point center;
    private final int count;
    public ColorBlock(BufferedImage image){
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.rgbs = new int[this.width][this.height];
        this.count = width*height;
        for(int x=0;x<this.width;x++){
            for(int y=0; y<this.height;y++){
                this.rgbs[x][y] = image.getRGB(x,y);
            }
        }
        this.leftTop = new Point(Point.ZERO.getX(),Point.ZERO.getY(),rgbs);
        this.rightTop = new Point(this.width-1,Point.ZERO.getY(),rgbs);
        this.leftBottom = new Point(Point.ZERO.getX(),this.height-1,rgbs);
        this.rightBottom = new Point(this.width-1,this.height-1,rgbs);
        this.center = new Point(this.width/2,this.height/2,rgbs);
    }

    public static class Point{
        public static final Point ZERO = new Point(0,0);
        private final int x;
        private final int y;
        private  int rgb;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(int x, int y, int rgb) {
            this(x,y);
            this.rgb = rgb;
        }
        public Point(int x, int y, int[][] block) {
            this(x,y);
            this.rgb = block[x][y];
        }

        public int getX() {
            return x;
        }


        public int getY() {
            return y;
        }


        public int getRgb() {
            return rgb;
        }

    }

    public Point getLeftTop() {
        return leftTop;
    }


    public Point getRightTop() {
        return rightTop;
    }


    public Point getLeftBottom() {
        return leftBottom;
    }


    public Point getRightBottom() {
        return rightBottom;
    }


    public Point getCenter() {
        return center;
    }


    public boolean isPointMatch(int leftTop,int rightTop,int leftBottom,int rightBottom,int center){
        return  this.leftTop.getRgb()==leftTop&&
                this.rightTop.getRgb()==rightTop&&
                this.leftBottom.getRgb()==leftBottom&&
                this.rightBottom.getRgb()==rightBottom&&
                this.center.getRgb()==center;

    }

    public int getWidth() {
        return width;
    }



    public int getHeight() {
        return height;
    }


    public boolean allMatch(Coordinate coordinate,int[][] screen,double percent){
       // 检查x中心线
        int centerX = this.getWidth()/2;
        int centerY = this.getHeight()/2;
        for(int x=0;x<this.getWidth();x++){
            if(!this.matchColor(this.rgbs[x][centerY],screen[x+coordinate.getX()][centerY+coordinate.getY()])) return false;
        }
        for(int y=0;y<this.getHeight();y++){
            if(!this.matchColor(this.rgbs[centerX][y],screen[coordinate.getX()+centerX][y+coordinate.getY()])) return false;
        }
       //检查y 中心线
        int temp=0;
        for(int x = 0;x<this.getWidth();x++){
            for(int y=0;y<this.getHeight();y++){
                if(this.matchColor(this.rgbs[x][y],screen[x+coordinate.getX()][y+coordinate.getY()])) temp++;
            }
        }
        return ((double)temp/(double) count)>percent;
    }

    boolean matchColor(int source,int dest){
        DirectColorModel colorModel = (DirectColorModel) DirectColorModel.getRGBdefault();
       return  colorModel.getBlue(source)==colorModel.getBlue(dest)
               && colorModel.getRed(source)==colorModel.getRed(dest)
               &&colorModel.getGreen(source)==colorModel.getGreen(dest);
    }
}
