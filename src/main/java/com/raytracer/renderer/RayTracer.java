package com.raytracer.renderer;

import com.raytracer.core.ColorRGB;
import com.raytracer.core.Ray;
import com.raytracer.scene.Camera;
import com.raytracer.scene.Intersection;
import com.raytracer.scene.Light;
import com.raytracer.scene.Scene;

public class RayTracer {
    private final Scene scene;
    private final Camera camera;
    private final int maxDepth = 5;

    public RayTracer(Scene scene, Camera camera) {
        this.scene = scene;
        this.camera = camera;
    }

    /**
     * Parcourt tous les pixels,
     * pour chaque (i,j), génère un rayon, trace et stocke la couleur.
     */
    public ColorRGB[][] render(int width, int height) {
        ColorRGB[][] image = new ColorRGB[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                // coordonnées normalisées [0,1]
                double u = (i + 0.5) / width;
                double v = (j + 0.5) / height;
                Ray ray = camera.getRay(u, v);
                image[i][j] = traceRay(ray, 0);
            }
        }
        return image;
    }

    /**
     * Tracer un rayon et retourner la couleur vue par ce rayon.
     * - intersection la plus proche
     * - shading lambertien + Phong
     * - réflexions jusqu’à maxDepth
     */
    private ColorRGB traceRay(Ray ray, int depth) {
        if (depth > maxDepth) {
            return ColorRGB.black();
        }

        Intersection hit = scene.intersect(ray);
        if (hit == null) {
            return scene.getBackground();
        }

        // 1. Shading local
        ColorRGB localColor = shade(hit, ray);

        // 2. Réflexion
        double refl = hit.material.reflectivity;
        if (refl > 0) {
            // direction réfléchie : R = D - 2(D·N)N
            var dir = ray.direction;
            var n   = hit.normal;
            var reflectDir = dir.sub(n.scale(2 * dir.dot(n))).normalize();
            Ray reflectRay = new Ray(hit.point.add(n.scale(1e-4)), reflectDir);
            ColorRGB reflectedColor = traceRay(reflectRay, depth + 1);
            // mélange local vs reflet
            localColor = localColor.scale(1 - refl)
                    .add(reflectedColor.scale(refl));
        }

        return localColor;
    }

    /**
     * Calcul du shading à un point d’impact :
     * somme des contributions de chaque lumière.
     */
    private ColorRGB shade(Intersection hit, Ray ray) {
        double ambientStrength = 0.1;
        ColorRGB result = hit.material.diffuseColor.scale(ambientStrength);
        for (Light light : scene.getLights()) {
            // vecteur vers la lumière
            var toLight = light.position.sub(hit.point).normalize();

            // Lambert (diffuse)
            double lambert = Math.max(0, hit.normal.dot(toLight));
            ColorRGB diff = hit.material.diffuseColor.scale(lambert);

            // Phong (speculaire simple)
            var viewDir = ray.direction.scale(-1).normalize();
            var halfVec = toLight.add(viewDir).normalize();
            double specAngle = Math.max(0, hit.normal.dot(halfVec));
            double specular = Math.pow(specAngle, 16);  // shininess fixe
            ColorRGB spec = light.intensity.scale(specular);

            // atténuation selon distance (optionnel)
            double distance = light.position.sub(hit.point).length();
            double attenuation = 1.0 / (distance * distance);

            // accumulation
            result = result
                    .add(diff.scale(attenuation))
                    .add(spec.scale(attenuation));
        }
        return result;
    }
}