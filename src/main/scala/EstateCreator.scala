import Main.estate
import Texts.{adminChoiceIntro, adminChoiceNewGuy}
import util._
import Texts._

object EstateCreator {

  def loadNewEstate:Property = {
    loadEstateInput()
  }

  private def loadEstateInput():Property = {
    val listOfAdmins = estate.map(_.admin).distinct
    val reactions = dictionaryChoice(listOfAdmins) _
    val admin = multichoice(
      adminChoiceIntro)(
      listOfAdmins.map(s => Array(s)),s"_ ~ ${randomText(adminChoiceNewGuy)}\n")(
      readString,reactions)
    println()
    println("DOBRA ZAPISUJI. DIKTUJTE PANE")
    println(Separator)
    println("Jméno Vašeho nového majetku? ")
    val what:String = getInput
    separate()
    println("Kde se Vás majetek nachází? ")
    val where:String = getInput
    separate()
    println("Kolik takových věcí vlastníte? ")
    val amount:Int = getIntInput()
    separate()
    println("Kolik je měsíční příjem za jednotku ve zlatých? (samozřejmě že umím i desetinná čísla Pane, jen je zapisuji s tečkou místo čárky)")
    val baseIncomePerUnitPerMonth:Double = getDoubleInput(0)
    separate()
    println("Počítá se měsíční příjem jako náhodná procenta této částky?  \n(ano ~  částka * 1k100/100, \n ne ~ opravov8no o nahazovanou konstantu)")
    val incomeIsPercentual:Boolean = readBoolean
    val (dice,n,const) =
    if(incomeIsPercentual){
      (100,1,0d)
    }else{
      separate()
      println("Jaká je míra nejistoty zisku, Sire? (kolika stěnnou kostkou si hážete na opravu? )")
      val dice1:Int = getDice
      separate()
      println("(kolikrát si hážete touto kostkou?) ~ default = 1")
      val n1:Int = getIntInput(1)
      separate()
      println("a jakou konstantou se vysledek opravných hodů přenásobuje? ~ default = desetina příjmu")
      val const1:Double = getDoubleInput(baseIncomePerUnitPerMonth/10)
      (dice1,n1,const1)
    }
    separate()
    println("Chcete si k tomuto majetku připsat poznámku, Pane? (pokud ne, pouze odklepněte enter)")
    val note:String = getInput +";"
    separate()
    Property(what,where,amount,baseIncomePerUnitPerMonth,dice,n,const,incomeIsPercentual,admin,note)
  }

  private def getInput:String = readString.replace("\t"," ")
  private def getDice:Int = readDice
  private def getIntInput():Int = {
    val in = readAnswer
    if (in == Int.MaxValue) {println(randomText(wrongInput)); getIntInput()}//default
    else in
  }
  private def getIntInput(default: Int):Int = {
    val in = readAnswer
    if (in == Int.MaxValue) default
    else in
  }
  private def getDoubleInput(default: Double):Double = {
    val in = readDouble
    if (in == Double.MaxValue) default
    else in
  }


}
