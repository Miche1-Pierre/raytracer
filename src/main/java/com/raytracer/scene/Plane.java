package com.raytracer.scene;

import com.raytracer.core.Ray;
import com.raytracer.core.Vector3;

public class Plane implements SceneObject {
    private final Vector3 point;
    private final Vector3 normal;
    private final Material material;

    public Plane(Vector3 point, Vector3 normal, Material material) {
        this.point = point;
        this.normal = normal.normalize();
        this.material = material;
    }

    @Override
    public Intersection intersect(Ray ray) {
        double denom = normal.dot(ray.direction);
        if (Math.abs(denom) < 1e-6) return null;
        double t = (point.sub(ray.origin).dot(normal)) / denom;
        if (t < 1e-4) return null;
        Vector3 p = ray.at(t);
        return new Intersection(t, p, normal, material);
    }
}