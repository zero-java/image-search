package com.zero.scvzerng;

import com.zero.scvzerng.entity.Coordinate;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * Created by scvzerng on 2017/7/24.
 */
public interface ImageFinder {
    /**
     * 查询到匹配的图片
     * @param image 需要查找的图片
     * @return
     */
   List<Coordinate> match(BufferedImage image,double percent);
}
