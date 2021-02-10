package ch3;

import java.io.FileWriter;
import java.io.IOException;

public class RayTracer {

    public static void main(String[] args) throws Exception {
        new RayTracer(100,100, "Chapter1.ppm");
    }
    
    public RayTracer(int width, int height, String title) throws Exception {
        ProgressBar cpb = new ProgressBar( width*height, 20, '=');

        FileWriter writer = new FileWriter(title);
        writer.write("P3\n" + width + " " + height + "\n255\n");
        int index = 0;
        for(int j = height - 1; j >= 0; j--){
            for(int i = 0; i < width; i++){
                float r = (float)i/(float)width;
                float g = (float)j/(float)height;
                float b = 0.2f;
                index += 1;
                writeColour(writer, new Colour(r, g, b));
                if(index % 100 == 0){
                    cpb.update(index);
                }
            }
        }
        cpb.complete();
        writer.close();
    }

    private void writeColour(FileWriter fw, Colour colour) throws IOException {
        int ir = (int)(255.99f*colour.r());
        int ig = (int)(255.99f*colour.g());
        int ib = (int)(255.99f*colour.b());
        fw.write(ir + " " + ig + " " + ib + "\n");
    }
}