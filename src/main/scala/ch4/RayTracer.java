package ch4;

import java.io.File;

public class RayTracer {

    public static void main(String[] args) throws Exception {
        new RayTracer(100,100, "Chapter1.ppm");
    }

    private Vec3 lower_left = new Vec3(-2.0f, -1.0f, -1.0f);
    private Vec3 horizontal = new Vec3(4.0f, 0.0f, 0.0f);
    private Vec3 vertical = new Vec3(0.0f, 2.0f, 0.0f);
    private Vec3 origin = new Vec3(0.0f, 0.0f, 0.0f);
    
    public RayTracer (int width, int height, String title) {
        ProgressBar progressBar = new ProgressBar( width*height, 20, '=');
        PPMWriter writer = new PPMWriter(new File(title), width, height);
        
        int index = 0;
        for(int j = height - 1; j >= 0; j--){
            for(int i = 0; i < width; i++){
                float u = (float)i / (float)width;
                float v = (float)j / (float)height;
                Ray r = new Ray(origin, lower_left.add(horizontal.scale(u)).add(vertical.scale(v))); 
                
                Colour col = calculateColour(r);
                writer.setPixel(i, j, col);

                index += 1;
                if(index % 100 == 0){
                    progressBar.update(index);
                }
            }
        }
        progressBar.complete();
        writer.writeToFile();
    }

    public Colour calculateColour(Ray ray) {
        if(hitSphere(new Vec3(0,0,-1), 0.5f, ray)){
            return new Colour(0,0,1);
        } else {
            Vec3 unit_dir = ray.direction().normalize();
            float t = 0.5f * (unit_dir.y() + 1.0f);
            return new Colour(1.0f, 1.0f, 1.0f).scale(1.0f - t).add(new Colour(0.5f, 0.7f, 1.0f).scale(t));
        }
    }

    public boolean hitSphere(Vec3 sphereCentre, float radius, final Ray ray) {
        Vec3 oc = ray.origin().subtract(sphereCentre);      //oc = A-C
        float a = ray.direction().dot(ray.direction()); //a = B·B
        float b = 2.0f * oc.dot(ray.direction());     //b = 2B·oc
        float c = oc.dot(oc) - radius * radius;     //c = oc^2 - R^2
        
        // a negative discriminant means there are zero solutions
        float discriminant = b*b - 4*a*c;
        return !(discriminant < 0);
    }
}