package com.caffeine.swingit.Graphics;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by olivierpicard on 02/04/2018.
 */

public class GInterval {
    public float min, max;

    public GInterval(int min, int max) {
        this.min = min;
        this.max = max;
    }


    public GInterval(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public int randomInt() { return ThreadLocalRandom.current().nextInt((int)min, (int)max); }
    public float random() {return (float)ThreadLocalRandom.current().nextDouble(min, max); }


    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }





}
