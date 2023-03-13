import cats.effect._
import com.sksamuel.scrimage._
import com.sksamuel.scrimage.color.AverageGrayscale
import com.sksamuel.scrimage.nio.PngWriter
import com.sksamuel.scrimage.pixels.Pixel

import java.awt.image.BufferedImage
import java.awt.{Color, Font, Graphics2D, Image}
import java.io.File



object Main extends IOApp.Simple {

  def calculatePixelTranche(pixel: Pixel): DieFace = {
    pixel.average() match {
      case x if x < 42 => SixFace
      case x if x < 85 => FiveFace
      case x if x < 128 => FourFace
      case x if x < 170 => ThreeFace
      case x if x < 213 => TwoFace
      case _ => OneFace
    }
  }

  private def diceifyImage(image: ImmutableImage, width: Int = 200): List[String] = {
    image.toGrayscale(new AverageGrayscale).pixels().grouped(image.width).map {
      _.map {calculatePixelTranche(_).face}.mkString
    }.toList
  }

  private def linesToImage(image: ImmutableImage, lines: List[String]): ImmutableImage = {
    val font: Font = new Font(Font.MONOSPACED, Font.PLAIN, 24) // TODO: Option here?
    val imageWidth: Int = image.width * font.getSize
    val imageHeight: Int = image.height * font.getSize
    val textImage: BufferedImage = ImmutableImage.filled(imageWidth, imageHeight, Color.WHITE).awt()
    val graphics: Graphics2D = textImage.createGraphics()
    graphics.setFont(font)
    graphics.setColor(Color.BLACK)

    def inner(line: String, imageWidth: Int, y: Int): Unit = {
      val lineWidth = graphics.getFontMetrics.stringWidth(line)
      val x = (imageWidth - lineWidth) / 2
      graphics.drawString(line, x, y)
    }

    val y = font.getSize
    lines.zipWithIndex.foreach { case (line, ix) =>
      val lineWidth = graphics.getFontMetrics.stringWidth(line)
      val x = (imageWidth - lineWidth) / 2
      graphics.drawString(line, x, y * (ix+1))
    }
    graphics.dispose()

    // ImmutableImage.fromAwt(textImage.getDeviceConfiguration(), textImage.getDevice(), textImage.getRenderingHints(), textImage.getComposite(), textImage.getBackground(), textImage.getStroke(), textImage.getTransform(), textImage.getClip(), textImage.getClipBounds(), textImage.getFont(), textImage.getFontRenderContext(), textImage.getColor(), textImage.getPaint(), textImage.getRendering(), textImage.getComposite(), textImage.getBackground(), textImage.getForeground(), textImage.getStroke(), textImage.getTransform(), textImage.getClip(), textImage.getClipBounds(), textImage.getRenderingHints()).toImmutableImage()
    ImmutableImage.fromAwt(textImage)
  }

  val run: IO[Unit] =
    for {
      _ <- IO.println("Provide the path to an image file")
      path <- IO.readLine
      image = ImmutableImage.loader().fromFile(path)
      resized = image.scaleToWidth(200)
      dicedString = diceifyImage(resized)
      _ <- IO.println(dicedString.mkString("\n"))
      newImage = linesToImage(resized, dicedString)
      _ = newImage.output(new PngWriter(), new File("./diced.png"))
    } yield ()
}

