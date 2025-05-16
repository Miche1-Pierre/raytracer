package com.raytracer.core;

public class Ray {

    public final Vector3 origin;
    public final Vector3 direction;

    public Ray(final Vector3 origin, final Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 at(double t) {
        return origin.add(direction.scale(t));
    }
}
