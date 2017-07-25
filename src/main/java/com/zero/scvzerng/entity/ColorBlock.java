package com.zero.scvzerng.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *
 * Created by scvzerng on 2017/7/24.
 */
public class ColorBlock {
    int[][] rgbs;
    private int width;
    private int height;
    private Point leftTop;
    private Point rightTop;
    private Point leftBottom;
    private Point rightBottom;
    private Point center;
    private int count = 0;
    public ColorBlock(BufferedImage image){
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.rgbs = new int[this.width][this.height];

        for(int x=0;x<this.width;x++){
            for(int y=0; y<this.height;y++){
                this.rgbs[x][y] = image.getRGB(x,y);
                count = count+1;
            }
        }
        this.leftTop = Point.ZERO;
        this.leftTop.setRgb(rgbs);
        this.rightTop = new Point(this.width-1,0);
        this.rightTop.setRgb(rgbs);
        this.leftBottom = new Point(0,this.height-1);
        this.leftBottom.setRgb(rgbs);
        this.rightBottom = new Point(this.width-1,this.height-1);
        this.rightBottom.setRgb(rgbs);
        this.center = new Point(this.width/2,this.height/2);
        this.center.setRgb(rgbs);
    }

    public static class Point{
        public static final Point ZERO = new Point(0,0);
        private int x;
        private int y;
        private int rgb;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(int x, int y, int rgb) {
            this(x,y);
            this.rgb = rgb;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getRgb() {
            return rgb;
        }

        public void setRgb(int rgb) {
            this.rgb = rgb;
        }
        public void setRgb(int[][] block){
            this.rgb = block[this.getX()][this.getY()];
        }
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Point leftTop) {
        this.leftTop = leftTop;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(Point rightTop) {
        this.rightTop = rightTop;
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Point leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Point getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(Point rightBottom) {
        this.rightBottom = rightBottom;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean allMatch(Coordinate coordinate,int[][] screen,double percent){
       // 检查x中心线
        int centerX = this.getWidth()/2;
        int centerY = this.getHeight()/2;
        for(int x=0;x<this.getWidth();x++){
            if(this.rgbs[x][centerY]!=screen[x+coordinate.getX()][centerY+coordinate.getY()]) return false;
        }
        for(int y=0;y<this.getHeight();y++){
            if(this.rgbs[centerX][y]!=screen[coordinate.getX()+centerX][y+coordinate.getY()]) return false;
        }
       //检查y 中心线
        int temp=0;
        for(int x = 0;x<this.getWidth();x++){
            for(int y=0;y<this.getHeight();y++){
                if(this.rgbs[x][y]==screen[x+coordinate.getX()][y+coordinate.getY()]) temp++;
            }
        }
        return ((double)temp/(double) count)>percent;
    }
}
