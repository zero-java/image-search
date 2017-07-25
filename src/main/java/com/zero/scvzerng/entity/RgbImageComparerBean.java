package com.zero.scvzerng.entity;

/**
 * Created by scvzerng on 2017/7/25.
 */
public class RgbImageComparerBean {
    private int[][] colorArray;
    private boolean[][] ignorePx;
    private int imgWidth;
    private int imgHeight;
    private int pxCount;

    public RgbImageComparerBean() {
    }

    public int[][] getColorArray() {
        return this.colorArray;
    }

    public void setPxCount(int pxCount) {
        this.pxCount = pxCount;
    }

    public void setColorArray(int[][] colorArray) {
        this.colorArray = colorArray;
        this.imgWidth = this.colorArray.length;
        this.imgHeight = this.colorArray[0].length;
        this.pxCount = this.imgWidth * this.imgHeight;
    }

    public boolean[][] getIgnorePx() {
        return this.ignorePx;
    }

    public void setIgnorePx(boolean[][] ignorePx) {
        this.ignorePx = ignorePx;
    }

    public int getImgWidth() {
        return this.imgWidth;
    }

    public int getImgHeight() {
        return this.imgHeight;
    }

    public int getPxCount() {
        return this.pxCount;
    }
}
