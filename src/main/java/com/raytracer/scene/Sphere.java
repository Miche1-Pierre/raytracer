package com.raytracer.scene;

import com.raytracer.core.Ray;
import com.raytracer.core.Vector3;

public class Sphere implements SceneObject {
    private final Vector3 center;
    private final double radius;
    private final Material material;

    public Sphere(Vector3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public Intersection intersect(Ray ray) {
        // Résoudre ||(O + tD) - C||^2 = R^2
        Vector3 oc = ray.origin.sub(center);
        double a = ray.direction.dot(ray.direction);
        double b = 2 * oc.dot(ray.direction);
        double c = oc.dot(oc) - radius * radius;
        double disc = b*b - 4*a*c;
        if (disc < 0) return null;
        double sqrtD = Math.sqrt(disc);
        double t1 = (-b - sqrtD) / (2*a);
        double t2 = (-b + sqrtD) / (2*a);
        double t = (t1 > 1e-4) ? t1 : (t2 > 1e-4 ? t2 : -1);
        if (t < 0) return null;
        Vector3 p = ray.at(t);
        Vector3 n = p.sub(center).normalize();
        return new Intersection(t, p, n, material);
    }
}