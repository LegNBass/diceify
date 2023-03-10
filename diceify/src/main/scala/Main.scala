import scala.concurrent.duration._

import cats.effect._


  object Main extends IOApp.Simple {
    val run =
      for {
        _ <- IO.println("What is your name?")
        name <- IO.readLine
        _ <- IO.println(s"Hello, $name")
      } yield ()
  }

