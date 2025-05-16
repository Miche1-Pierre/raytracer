package com.raytracer.core;

public class ColorRGB {

    public final double r, g, b;

    public ColorRGB(double r, double g, double b) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
    }

    private double clamp(double v) {
        return Math.max(0, Math.min(1, v));
    }

    public ColorRGB add(ColorRGB c) {
        return new ColorRGB(r + c.r, g + c.g, b + c.b);
    }

    public ColorRGB scale(double s) {
        return new ColorRGB(r * s, g * s, b * s);
    }

    public int toInt() {
        int ir = (int)(clamp(r) * 255);
        int ig = (int)(clamp(g) * 255);
        int ib = (int)(clamp(b) * 255);
        return (ir << 16) | (ig << 8) | ib;
    }

    public static ColorRGB black() {
        return new ColorRGB(0, 0, 0);
    }
}
