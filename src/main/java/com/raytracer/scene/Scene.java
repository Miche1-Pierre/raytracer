package com.raytracer.scene;

import com.raytracer.core.ColorRGB;
import com.raytracer.core.Ray;

import java.util.List;

public class Scene {
    private final List<SceneObject> objects;
    private final List<Light> lights;
    private final ColorRGB backgroundColor;

    public Scene(List<SceneObject> objects,
                 List<Light> lights,
                 ColorRGB backgroundColor) {
        this.objects = objects;
        this.lights = lights;
        this.backgroundColor = backgroundColor;
    }

    /** Parcourt tous les objets et renvoie la plus proche intersection */
    public Intersection intersect(Ray ray) {
        Intersection closest = null;
        for (SceneObject obj : objects) {
            Intersection hit = obj.intersect(ray);
            if (hit != null && (closest == null || hit.t < closest.t)) {
                closest = hit;
            }
        }
        return closest;
    }

    // Accesseurs…
    public List<Light> getLights() { return lights; }
    public ColorRGB getBackground() { return backgroundColor; }
}