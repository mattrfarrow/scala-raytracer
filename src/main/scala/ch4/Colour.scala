package ch4

case class Colour(r: Float, g: Float, b: Float) {

  def scale(a: Vec3, c: Float) = Colour(r * c, g * c, b * c)

  def scale(t: Float) = new Colour(r * t, g * t, b * t)

  def add(v: Colour) = Colour(r + v.r, g + v.g, b + v.b)

}
