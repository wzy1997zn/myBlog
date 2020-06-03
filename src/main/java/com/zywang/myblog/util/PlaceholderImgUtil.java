package com.zywang.myblog.util;

import java.util.Random;

public class PlaceholderImgUtil {
    public static String getRandomImg(int x, int y) {
        int seed = new Random().nextInt();
        return String.format("https://picsum.photos/%d/laowang/%d/%d",seed,x,y);
    }
}
