package com.raytracer;

import com.raytracer.core.ColorRGB;
import com.raytracer.core.Vector3;
import com.raytracer.renderer.RayTracer;
import com.raytracer.scene.*;
import com.raytracer.utils.ImageWriter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Définition des matériaux
        Material redMat   = new Material(new ColorRGB(1.0, 0.2, 0.2), 0.0);
        Material greenMat = new Material(new ColorRGB(0.2, 1.0, 0.2), 0.4);
        Material blueMat  = new Material(new ColorRGB(0.2, 0.2, 1.0), 0.8);
        Material grayMat  = new Material(new ColorRGB(1.0, 1.0, 1.0), 0.4);

        // 2. Création des objets de la scène
        List<SceneObject> objects = List.of(
                new Sphere(new Vector3( 0.0,  0.0, -1.0), 0.5, redMat),
                new Sphere(new Vector3( 1.0,  0.0, -2.0), 0.5, greenMat),
                new Sphere(new Vector3(-1.0,  0.0, -2.0), 0.5, blueMat),
                new Plane(new Vector3( 0.0, -0.6,  0.0), new Vector3(0,1,0), grayMat)
        );

        // 3. Création des lumières
        List<Light> lights = List.of(
                new Light(new Vector3( 5, 5, -5), new ColorRGB(1.0, 1.0, 1.0)),
                new Light(new Vector3(-3, 5, -2), new ColorRGB(1.0, 1.0, 1.0))
        );

        // 4. Construction de la scène et de la caméra
        Scene scene = new Scene(objects, lights, new ColorRGB(1.0, 1.0, 1.0));
        Camera camera = new Camera(
                new Vector3(0, 0, 1),    // position caméra
                new Vector3(0, 0, -1),   // point visé
                new Vector3(0, 1, 0),    // up vector
                60,                      // FOV vertical en degrés
                800.0 / 600.0            // aspect ratio
        );

        // 5. Lancement du rendu
        int width  = 800;
        int height = 600;
        RayTracer tracer = new RayTracer(scene, camera);
        ColorRGB[][] image = tracer.render(width, height);

        // 6. Sauvegarde du résultat
        String filename = "output.png";
        ImageWriter.saveImage(image, filename);
        System.out.println("Rendu terminé ! Image enregistrée sous « " + filename + " »");
    }
}