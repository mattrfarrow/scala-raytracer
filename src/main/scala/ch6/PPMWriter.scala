package ch6

import java.io.{File, FileWriter}

class PPMWriter(file: File, width: Int, height: Int) {

  private val pixels = Array.ofDim[Colour](width, height)
  
  def setPixel(x: Int, y: Int, colour: Colour): Unit = {
    pixels(x)(y) = colour
  }
  
  def writeToFile(): Unit = {
    val writer = new FileWriter(file)
    writer.write("P3\n" + width + " " + height + "\n255\n")
    for(x <- 0 to width-1) {
      for(y <- 0 to height-1) {
        val colour = pixels(x)(y)
        val ir = (255.99f*colour.r).toInt;
        val ig = (255.99f*colour.g).toInt;
        val ib = (255.99f*colour.b).toInt;
        writer.write(ir + " " + ig + " " + ib + "\n");
      }
    }
    writer.close()
    
  }
  
}
