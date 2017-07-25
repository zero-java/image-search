package com.zero.scvzerng.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scvzerng on 2017/7/25.
 */
public class ImageCognition {
    public static final int SIM_ACCURATE_VERY = 0;
    public static final int SIM_ACCURATE = 31;
    public static final int SIM_BLUR = 61;
    public static final int SIM_BLUR_VERY = 81;

    public ImageCognition() {
    }

    public List<CoordBean> imageSearch(BufferedImage sourceImage, BufferedImage searchImage, int sim) {
        List<CoordBean> list = new ArrayList();
        RgbImageComparerBean pxSource = this.getPX(sourceImage);
        RgbImageComparerBean pxSearch = this.getPX(searchImage);
        int[][] px = pxSource.getColorArray();
        int[][] pxS = pxSearch.getColorArray();
        int pxSXMax = pxSearch.getImgWidth() - 1;
        int pxSYMax = pxSearch.getImgHeight() - 1;
        int xSearchEnd = pxSource.getImgWidth() - pxSearch.getImgWidth();
        int ySearchEnd = pxSource.getImgHeight() - pxSearch.getImgHeight();
        int contentSearchX = 1;
        int contentSearchY = 1;
        double pxPercent = 0.9900000095367432D;
        if(sim > 0) {
            pxPercent = (double)sim / 255.0D / 4.0D;
        }

        for(int x = 0; x < xSearchEnd; ++x) {
            for(int y = 0; y < ySearchEnd; ++y) {
                boolean contrast = false;
                int yes;
                int xS;
                if(sim < 32) {
                    if(this.colorCompare(px[x][y], pxS[0][0], sim)) {
                        yes = x + pxSearch.getImgWidth() - 1;
                        if(this.colorCompare(px[yes][y], pxS[pxSXMax][0], sim)) {
                            xS = y + pxSearch.getImgHeight() - 1;
                            if(this.colorCompare(px[x][xS], pxS[0][pxSYMax], sim) && this.colorCompare(px[yes][xS], pxS[pxSXMax][pxSYMax], sim)) {
                                if(pxSXMax > 2) {
                                    contentSearchX = (int)Math.ceil((double)(pxSXMax / 2));
                                }

                                if(pxSYMax > 2) {
                                    contentSearchY = (int)Math.ceil((double)(pxSYMax / 2));
                                }

                                if(this.colorCompare(px[x + contentSearchX][y + contentSearchY], pxS[contentSearchX][contentSearchY], sim)) {
                                    contrast = true;
                                }
                            }
                        }
                    }
                } else {
                    contrast = true;
                }

                int yS;
                if(sim >= 62) {
                    contrast = true;
                } else {
                    if(contrast) {
                        yes = 0;
                        xS = y + contentSearchY;

                        for(yS = 0; yS < pxSearch.getImgWidth(); ++yS) {
                            if(this.colorCompare(px[x + yS][xS], pxS[yS][contentSearchY], sim)) {
                                ++yes;
                            }
                        }

                        if((double)(yes / pxSearch.getImgWidth()) > pxPercent) {
                            contrast = true;
                        } else {
                            contrast = false;
                        }
                    }

                    if(contrast) {
                        yes = 0;
                        xS = x + contentSearchX;

                        for(yS = 0; yS < pxSearch.getImgHeight(); ++yS) {
                            if(this.colorCompare(px[xS][y + yS], pxS[contentSearchX][yS], sim)) {
                                ++yes;
                            }
                        }

                        if((double)(yes / pxSearch.getImgHeight()) > pxPercent) {
                            contrast = true;
                        } else {
                            contrast = false;
                        }
                    }
                }

                if(contrast) {
                    yes = 0;

                    for(xS = 0; xS < pxSearch.getImgWidth(); ++xS) {
                        for(yS = 0; yS < pxSearch.getImgHeight(); ++yS) {
                            if(this.colorCompare(px[x + xS][y + yS], pxS[xS][yS], sim)) {
                                ++yes;
                            }
                        }
                    }

                    if((double)(yes / pxSearch.getPxCount()) > pxPercent) {
                        CoordBean coord = new CoordBean();
                        coord.setX(x);
                        coord.setY(y);
                        list.add(coord);
                    }
                }
            }
        }

        return list;
    }

    public RgbImageComparerBean getPX(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        RgbImageComparerBean rgb = new RgbImageComparerBean();
        int[][] colorArray = new int[width][height];

        for(int i = minx; i < width; ++i) {
            for(int j = miny; j < height; ++j) {
                colorArray[i][j] = bufferedImage.getRGB(i, j);
            }
        }

        rgb.setColorArray(colorArray);
        return rgb;
    }

    public boolean colorCompare(int pxSource, int pxSearch, int sim) {
        if(sim == 0) {
            return pxSearch == pxSource;
        } else {
            Color sourceRgb = new Color(pxSource);
            Color searchRgb = new Color(pxSearch);
            return this.colorCompare(sourceRgb, searchRgb, sim);
        }
    }

    public boolean colorCompare(Color color1, Color color2, int sim) {
        return Math.abs(color1.getRed() - color2.getRed()) <= sim && Math.abs(color1.getGreen() - color2.getGreen()) <= sim && Math.abs(color1.getBlue() - color2.getBlue()) <= sim;
    }
}
