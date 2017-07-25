package com.zero.scvzerng;

import java.awt.image.BufferedImage;

/**
 * Created by scvzerng on 2017/7/25.
 */
public class ImageUtil {
    public static int[][] getRGB(BufferedImage image){
        int width = image.getWidth(),
                height = image.getHeight(),
                minWidth = image.getMinX(),
                minHeigth = image.getMinY();

        int[][] rgbs = new int[width][height];
        for(int x=minWidth;x<width;x++){
            for(int y=minHeigth;y<height;y++){
                rgbs[x][y] = image.getRGB(x,y);
            }
        }

        return rgbs;
    }
}
