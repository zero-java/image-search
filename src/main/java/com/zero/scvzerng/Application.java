package com.zero.scvzerng;

import com.zero.scvzerng.entity.CoordBean;
import com.zero.scvzerng.entity.Coordinate;
import com.zero.scvzerng.entity.ImageCognition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Created by scvzerng on 2017/7/24.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        ImageFinder finder = new ScreenImageFinder();
        BufferedImage search = ImageIO.read(Application.class.getClassLoader().getResourceAsStream("flow.png"));
        long start = System.currentTimeMillis();
        List<Coordinate> coordinateList = finder.match(search,0.99);
        System.out.println(System.currentTimeMillis()-start);
        start = System.currentTimeMillis();
        List<CoordBean> coordBeans = new ImageCognition().imageSearch(((ScreenImageFinder)finder).getScreen(),search,ImageCognition.SIM_ACCURATE_VERY);
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(coordinateList.size());
        System.out.println(coordBeans.size());
    }
}
