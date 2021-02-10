package ch6

case class Ray (origin: Vec3, direction: Vec3) {
  
  def point_at_parameter(t: Float): Vec3 = origin.add(direction.scale(t))
  
}
