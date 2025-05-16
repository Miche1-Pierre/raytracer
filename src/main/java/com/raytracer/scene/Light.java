package com.raytracer.scene;

import com.raytracer.core.ColorRGB;
import com.raytracer.core.Vector3;

public class Light {
    public final Vector3 position;
    public final ColorRGB intensity;

    public Light(Vector3 position, ColorRGB intensity) {
        this.position = position;
        this.intensity = intensity;
    }
}