sealed trait DieFace {
  val face: Char
}

case object OneFace extends DieFace {
  override val face: Char = '\u2680'
}

case object TwoFace extends DieFace {
  override val face: Char = '\u2681'
}

case object ThreeFace extends DieFace {
  override val face: Char = '\u2682'
}

case object FourFace extends DieFace {
  override val face: Char = '\u2683'
}

case object FiveFace extends DieFace {
  override val face: Char = '\u2684'
}

case object SixFace extends DieFace {
  override val face: Char = '\u2685'
}