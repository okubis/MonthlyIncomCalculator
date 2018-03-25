import sun.plugin2.message.Message

import scala.util.Random

package object util {
  class UndefinedDiceType(message: String) extends UnsupportedOperationException
  private val Four = 4
  private val Six = 6
  private val Eight = 8
  private val Ten = 10
  private val Twelve = 12
  private val Twenty = 20
  private val Hundred = 100

  private val dice = Random

  private def rollNdice(n:Int) = dice.nextInt(n)+1

  def getSignum = if (dice.nextBoolean()) 1 else -1

  def roll(dice:Int):Int = {
    val n =
    dice match {
      case Four | Six | Eight | Ten | Twelve | Twenty|Hundred => dice
      case _ => throw new UndefinedDiceType(s"Undefined dice type, hase to be one from: $Four,$Six,$Eight,$Ten,$Twelve,$Twenty,$Hundred")
    }
    rollNdice(n)
  }

  def nroll(n:Int)(dice:Int): Int =
    (for(_ <- 1 to n) yield roll(dice)).sum

  def randomText(textGroup: Array[String]):String = {
    textGroup(dice.nextInt(textGroup.length))
  }

}
