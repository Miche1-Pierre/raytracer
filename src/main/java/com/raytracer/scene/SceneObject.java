package com.raytracer.scene;

import com.raytracer.core.Ray;

public interface SceneObject {
    /** Retourne l’intersection la plus proche, ou null si pas d’impact */
    Intersection intersect(Ray ray);
}