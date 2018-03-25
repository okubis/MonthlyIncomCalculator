import java.io.{BufferedWriter, File, FileWriter}

import scala.io.Source
import util._

import scala.io.StdIn.{readLine => readInput}
import Texts._

object Main {
  val Separator = "----------------------------------------------------------------------"
  var path:String = s"data.tsv"
  var iterator:Iterator[Array[String]] = _
  var estate: List[Property] = Nil
  def main(args: Array[String]): Unit = {
    initData()
    iterator =                          // parse tsv into lines slit by tab
      Source.fromFile(new File(path)).getLines().drop(1).map(_.split("\t"))
    estate = transform(iterator)

    println()
    println()
    println(Separator)
    for(i <- 1 to 10000){
      mainMenu()
    }

    println(randomText(farewells))
  }

  private def mainMenu():Unit = {
    multichoice(greetings)(List(
      computeIncome,
      addEstateTxts,
      removeEstate,
      exitTxts
    ))(
      mainMenuReactions
    )
  }

  def loadAnswer():Int = try{readInput().toInt}catch{case _ => Int.MaxValue}
  def mainMenuReactions(choice:Int):Unit = {
    choice match{
      case 0 =>
        println("Kolik měsíců chcete vidět, Pane?")
        val months = loadAnswer()
        if(months < Int.MaxValue) {
          var sum = 0d
          for(m <- 1 to months){
            println(s"Záznam za měsíc: $m")
            estate.foreach(p => {
              val (output,income) = p.getMonthlyIncomeInfo
              println(output)
              sum = sum + income
            })
            println(Separator)
          }
          println(s"Celkem jste tedy získal ze svých statků: $sum zlatých, (tedy ${(sum/40).toInt} měšců a ${(sum%40).toInt} fufňů.)")
          println(Separator)
        }else
          randomText(wrongInput)
      case 1 =>
        println("ADD ESTATE")
        // todo: ask for attributes
        // todo: log the estate
      case 2 =>
        println("REMOVE ESTATE")
        // todo: ask for attributes
        // todo: remove the estate
      case 3 =>
        println(randomText(farewells))
        System.exit(0)
      case _ =>
        println(randomText(wrongAnswerTxts))
        mainMenuReactions(loadAnswer())
    }
  }

  private def multichoice[R](intro:Array[String])(choices:List[Array[String]])(reactions:Int => R): R = {
    val sb = new StringBuilder()
    println(randomText(intro) +
      s"\n$Separator\n"+
      s"možnosti: \n"
    )
    var i =0
    choices.zipWithIndex.foreach(t =>
      sb.append(s"${t._2} ~ ${randomText(t._1)} \n")
    )
    sb.append(s"_ ~ ${randomText(prolongations)} \n")
    println(sb)
    val input = loadAnswer()
    reactions(input)
  }


  private def addEstate():Unit = {
    val fw = new FileWriter(path,true)
    //val newPorperty = loadProperty

    try {
      //fw.write( /* your stuff */)
    }
    finally fw.close()
  }

  private def transform(it:Iterator[Array[String]]):List[Property] = {
    import DataParser.DataLine
    it.map{
      d: Array[String] => Property(d.what,d.where,d.amount,d.baseIncomePerUnitPerMonth,d.dice,d.numberOfRolls,d.const,d.incomeIsPercentual,d.admin,d.note)
    }.toList
  }

  private def initData():Unit = {
    import java.nio.file.{Files, Paths}
    val exists = Files.exists(Paths.get(path))
    if(!exists) {
      val p = Paths.get(path)
      Files.createFile(p)

      val lines = "what\twhere\tamount\tbaseIncomePerUnitPerMonth\tdice\tn\tconst\tincomeIsInPercents\tadministrator\tnote\n" +
        "A haunted dwarfmine\tsomewhere in the woods\t1\t0\t20\t1\t1\tfalse\tGruusBazaddan\tan example of what should a property log look like"

      val file = new File(path)
      val bw = new BufferedWriter(new FileWriter(file))
      bw.write(lines)
      bw.close()
    }
  }
}

case class Property(
  what:String,
  where:String,
  amount:Int,
  baseIncomePerUnitPerMonth:Double,
  dice:Int,
  n:Int,
  const:Double,
  incomeIsPercentual:Boolean,
  admin:String,
  note:String) {
  private def correction:Double = getSignum * const * nroll(n)(dice)

  private def getMonthlyProfit:Double  =
    if(incomeIsPercentual)
      (for(_ <- 1 to amount) yield baseIncomePerUnitPerMonth * nroll(n)(dice)/100d).sum
    else
      (for(_ <- 1 to amount) yield baseIncomePerUnitPerMonth + correction).sum

  def toReadableString: String = s"what:$what\twhere:$where\tamount:$amount\tbaseIncomePerUnitPerMonth:$baseIncomePerUnitPerMonth\t" +
    s"dice:$dice\tn:$n\tconst:$const\tincomeIsInPercents:$incomeIsPercentual\tadministrator:$admin\tnote:$note"

  override def toString: String = s"\n$what\t$where\t$amount\t$baseIncomePerUnitPerMonth\t$dice\t$n\t$const\t$incomeIsPercentual\t$admin\t$note"

  def getMonthlyIncomeInfo:(String,Double) = {
    val sb = new StringBuilder()
    sb.append("Majetek: ")
    sb.append(what)
    sb.append(" Místo: ")
    sb.append(where)
    sb.append("\n")
    sb.append("Zisk: ")
    val income = getMonthlyProfit
    sb.append(income)
    sb.append(" průměrný zisk: ")
    sb.append(income / amount)
    sb.append("\n")
    (sb.toString(),income)
  }
}

object DataParser{
  implicit class DataLine(line: Array[String]){
    def what: String = line(0)
    def where: String = line(1)
    def amount: Int = line(2).toInt
    def baseIncomePerUnitPerMonth: Double = line(3).toDouble
    def dice: Int = line(4).toInt
    def numberOfRolls: Int = line(5).toInt
    def const:Double = line(6).toInt
    def incomeIsPercentual:Boolean = line(7).toBoolean
    def admin:String = line(8)
    def note:String = line(9)
  }
}
