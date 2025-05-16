package com.raytracer.scene;

import com.raytracer.core.ColorRGB;

public class Material {
    public final ColorRGB diffuseColor;
    public final double reflectivity;

    public Material(ColorRGB diffuseColor, double reflectivity) {
        this.diffuseColor = diffuseColor;
        this.reflectivity = reflectivity;
    }
}