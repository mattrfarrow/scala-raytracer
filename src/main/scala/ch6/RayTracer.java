package ch6;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RayTracer {

    public static void main(String[] args) throws Exception {
        new RayTracer(400,400, "Chapter1.ppm");
    }
    
    public RayTracer (int width, int height, String title) {
        ProgressBar progressBar = new ProgressBar( width*height, 20, '=');
        PPMWriter writer = new PPMWriter(new File(title), width, height);

        List<Hitable> objList = new ArrayList<>();
        objList.add(new Sphere(new Vec3(0.0f,0.0f,-1.0f), 0.5f));
        objList.add(new Sphere(new Vec3(0.3f,0.0f,-1.0f), 0.3f));
        objList.add(new Sphere(new Vec3(0.0f,-100.5f,-1.0f), 100f));
        Hitable world = new HitableList(objList);
        Camera camera = new Camera();
        int index = 0;
        int ns = 100; 
        for(int j = height - 1; j >= 0; j--){
            for(int i = 0; i < width; i++){
                Colour colour = new Colour(0,0,0);   
                for(int s = 0; s < ns; s++){
                    float u = (float)(i + Math.random())/(float)width; //添加随机数 消锯齿
                    float v = (float)(j + Math.random())/(float)height;
                    Ray r = camera.GetRay(u, v);        //根据uv得出光线向量
                    colour = colour.add(color(r,world));      //根据每个像素点（光线）上色 累加
                }
                colour = colour.scale(1.0f/(float)ns);        //除以采样次数 平均化
                index += 1;
                writer.setPixel(j, i, colour);
                if(index % 100 == 0){
                    progressBar.update(index);
                }
            }
        }
        progressBar.complete();
        writer.writeToFile();
    }

  

    public float hitSphere(final Vec3 center, float radius, final Ray r) {
        Vec3 oc = r.origin().subtract(center);      //oc = A-C
        float a = r.direction().dot(r.direction()); //a = B·B
        float b = 2.0f * oc.dot(r.direction());     //b = 2B·oc
        float c = oc.dot(oc) - radius * radius;     //c = oc^2 - R^2
        float discriminant = b*b - 4*a*c;
        if (discriminant < 0)
        {
            return -1.0f;
        }
        else
        {
            return (-b - (float)Math.sqrt(discriminant)) / (2.0f * a);
        }
    }

    public Colour color(Ray r, Hitable world)
    {
        HitRecord rec = new HitRecord();
        if(world.hit(r, 0.0f, Float.MAX_VALUE, rec)){
            //有撞击点，按撞击点法向量代表的颜色绘制
            return new Colour(rec.normal.x()+1,rec.normal.y()+1,rec.normal.z()+1).scale(0.5f);
        }
        else{
            //没有撞击点，绘制背景
            Vec3 unit_dir = r.direction().normalize();  //单位方向向量
            float t = 0.5f * (unit_dir.y() + 1.0f);     //原本范围为[-1,1]调整为[0,1]
            return new Colour(1.0f, 1.0f, 1.0f).scale(1.0f - t).add(new Colour(0.5f, 0.7f, 1.0f).scale(t));
            //返回背景(1.0-t)*vec3(1.0, 1.0, 1.0) + t*vec3(0.5, 0.7, 1.0); 沿着y轴线性插值，返回的颜色介于白色与天蓝色之间
        }
    }
}