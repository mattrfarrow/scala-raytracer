package ch2;

import java.io.FileWriter;

public class RayTracer {

    public static void main(String[] args) throws Exception {
        new RayTracer(25,25, "Chapter1.ppm");
    }
    
    public RayTracer(int width, int height, String title) throws Exception {
        ProgressBar cpb = new ProgressBar(width*height, 20, '=');

        FileWriter fw = new FileWriter(title);
        fw.write("P3\n" + width + " " + height + "\n255\n");
        int index = 0;
        for(int j = height - 1; j >= 0; j--){
            for(int i = 0; i < width; i++){
                float r = (float)i/(float)width;
                float g = (float)j/(float)height;
                float b = 0.2f;
                index += 1;
                int ir = (int)(255.59f*r);
                int ig = (int)(255.59f*g);
                int ib = (int)(255.59f*b);
                fw.write(ir + " " + ig + " " + ib + "\n");
                if(index % 100 == 0){
                    cpb.update(index);
                }
            }
        }
        cpb.complete();
        fw.close();

    }
}