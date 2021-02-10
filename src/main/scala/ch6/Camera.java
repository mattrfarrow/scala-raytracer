package ch6;

public class Camera
{
    private final Vec3 lower_left;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 origin;

    public Camera() {
        lower_left = new Vec3(-2.0f, -1.0f, -1.0f);
        horizontal = new Vec3(4.0f, 0.0f, 0.0f);
        vertical = new Vec3(0.0f, 2.0f, 0.0f);
        origin = new Vec3(0.0f, 0.0f, 0.0f);
    }

    public Ray GetRay(float u, float v) {
        return new Ray(origin, lower_left.add(horizontal.scale(u)).add(vertical.scale(v)));
    }
}