package com.raytracer.scene;

import com.raytracer.core.Ray;
import com.raytracer.core.Vector3;

public class Camera {
    private final Vector3 origin;
    private final Vector3 lowerLeftCorner;
    private final Vector3 horizontal;
    private final Vector3 vertical;

    public Camera(Vector3 lookFrom, Vector3 lookAt, Vector3 vUp,
                  double vfov, double aspect) {
        double theta = Math.toRadians(vfov);
        double halfHeight = Math.tan(theta/2);
        double halfWidth = aspect * halfHeight;
        Vector3 w = lookFrom.sub(lookAt).normalize();
        Vector3 u = vUp.cross(w).normalize();
        Vector3 v = w.cross(u);
        origin = lookFrom;
        lowerLeftCorner = origin
                .sub(u.scale(halfWidth))
                .sub(v.scale(halfHeight))
                .sub(w);
        horizontal = u.scale(2 * halfWidth);
        vertical   = v.scale(2 * halfHeight);
    }

    public Ray getRay(double u, double v) {
        Vector3 dir = lowerLeftCorner
                .add(horizontal.scale(u))
                .add(vertical.scale(v))
                .sub(origin);
        return new Ray(origin, dir);
    }
}