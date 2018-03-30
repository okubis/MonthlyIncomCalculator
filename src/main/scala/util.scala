import Texts.{wasteOp, wrongInput}
import sun.plugin2.message.Message

import scala.util.Random
import scala.io.StdIn.{readLine => readInput}

package object util {

  // ###################################################################################################################
  // ########### DICE ROLLS ############################################################################################
  // ###################################################################################################################
  class UndefinedDiceType(message: String) extends UnsupportedOperationException
  private val Four = 4
  private val Six = 6
  private val Eight = 8
  private val Ten = 10
  private val Twelve = 12
  private val Twenty = 20
  private val Hundred = 100
  private val textLength = 15

  private val dice = Random

  private def rollNdice(n:Int) = dice.nextInt(n)+1

  def getSignum = if (dice.nextBoolean()) 1 else -1

  def roll(dice:Int):Int = {
    val n =
    dice match {
      case Four | Six | Eight | Ten | Twelve | Twenty |Hundred => dice
      case _ => throw new UndefinedDiceType(s"Undefined dice type, hase to be one from: $Four,$Six,$Eight,$Ten,$Twelve,$Twenty,$Hundred")
    }
    rollNdice(n)
  }

  def nroll(n:Int)(dice:Int): Int =
    (for(_ <- 1 to n) yield roll(dice)).sum

  // ###################################################################################################################
  // ########### TEXT MANIPULATION #####################################################################################
  // ###################################################################################################################

  val ProgramEnd= "######################## PROGRAM UKONCEN #############################"

  def randomText(textGroup: Array[String]):String = {
    textGroup(dice.nextInt(textGroup.length))
  }

  def fixLength(s:String):String = {
    val l = s.length
    val ss = Array.fill((textLength -l) max 0)(" ").mkString("")
    s + ss
  }

  def separate():Unit = {println();println(Separator)}

  def endSubdialog():Unit = {
    println(DoubleSeparator)
    println()
    println(DoubleSeparator)
  }

  def programExit():Unit = {
    println()
    println(DoubleSeparator)
    println(DoubleSeparator)
    println(ProgramEnd)
    println(DoubleSeparator)
  }

  // ###################################################################################################################
  // ########### CONSOLE IO OPERATIONS #################################################################################
  // ###################################################################################################################

  val Separator = "----------------------------------------------------------------------"
  val DoubleSeparator = "======================================================================"


  def readAnswer: Int = try{readInput().toInt}catch{case _ => Int.MaxValue}
  def readString: String =  readInput()
  def readDouble: Double = try{readInput().toDouble}catch{case _ => Double.MaxValue}
  def readBoolean:Boolean = {println(" zadej [ ano / ne ]"); val r = readString.toLowerCase; if (r == "ano") true else if (r == "ne") false else readBoolean}
  def readDice: Int = {
    println(s" zadej [ $Four / $Six / $Eight / $Ten / $Twelve / $Twenty / $Hundred ]")
    val r = readAnswer
    r match {case Four | Six | Eight | Ten | Twelve | Twenty |Hundred => r; case _ => readDice }}

  def dictionaryChoice(dictionary: List[String])(choice:String):String = {
    def isName(s:String) = s forall Character.isLetter
    def isAnExistingAdmin(x: String) = (x forall Character.isDigit) && x.toInt >= 0 && x.toInt < dictionary.length
    choice match {
      case s if s.length == 0 || (!isAnExistingAdmin(s) && !isName(s)) => dictionaryChoice(dictionary)({println(randomText(wrongInput));readInput()})
      case s if isAnExistingAdmin(s) => dictionary(choice.toInt)
      case _ => choice
    }
  }

  def multichoice[I,R]
  (intro:Array[String])
  (choices:List[Array[String]],defaultChoice:String = s"_ ~ ${randomText(wasteOp)} \n")
  (loadMethod:  => I ,reactions:I => R): R = {
      val sb = new StringBuilder()
      println(randomText(intro) +
        s"\n$Separator\n"+
        s"možnosti: \n"
      )
      var i =0
      choices.zipWithIndex.foreach(t =>
        sb.append(s"${t._2} ~ ${randomText(t._1)} \n")
      )
      sb.append(defaultChoice)
      sb.append(DoubleSeparator)
      println(sb)

      val input = loadMethod
      reactions(input)
  }

}
