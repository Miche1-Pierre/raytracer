package com.raytracer.scene;

import com.raytracer.core.Vector3;
import com.raytracer.core.ColorRGB;

public class Intersection {
    public final double t;
    public final Vector3 point;
    public final Vector3 normal;
    public final Material material;

    public Intersection(double t, Vector3 point, Vector3 normal, Material material) {
        this.t = t;
        this.point = point;
        this.normal = normal;
        this.material = material;
    }
}
