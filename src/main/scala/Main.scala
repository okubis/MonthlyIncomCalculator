import java.io.{BufferedWriter, File, FileWriter}
import java.nio.charset.Charset

import scala.io.Source
import util._

import Texts._

object Main {
  val dataFileManager = DataFileManager(s"data.tsv")
  var iterator:Iterator[Array[String]] = _
  var estate: List[Property] = _


  def main(args: Array[String]): Unit = {
    dataFileManager.initData()
    estate = dataFileManager.loadEstate
    println()
    println()
    println(Separator)
    for(i <- 1 to 10000){
      mainMenu()
      estate = dataFileManager.loadEstate
    }
    println(randomText(farewells))
  }

  private def mainMenu():Unit = {
    multichoice(greetings)(List(
      computeIncomeIntro,
      addEstateIntro,
      removeEstateIntro,
      modifyEstateIntro,
      listEstateIntro,
      exitOp
    ))(
      readAnswer,
      mainMenuReactions
    )
  }




  def mainMenuReactions(choice:Int):Unit = {
    choice match{
      case 0 =>
        println("Kolik měsíců chcete vidět, Pane?")
        val months = readAnswer
        println(Separator)
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
          endSubdialog()
        }else
          randomText(wrongInput)
      case 1 =>
        println("ADD ESTATE")
        val newEstate = EstateCreator.loadNewEstate
        dataFileManager.append(newEstate.toString)
        println("Váš nově zapsaný majetek:")
        println(newEstate.toReadableString)
        endSubdialog()
      case 2 =>
        println("REMOVE ESTATE")
        println("yet 2B implemented")
        // todo: ask for attributes
        // todo: remove the estate
        endSubdialog()
      case 3 =>
        println("MODIFY ESTATE INFO")
        println("yet 2B implemented")
        // todo: ask for an estate to change
        // todo: ask for attributes to change
        // todo: modify the estate
        endSubdialog()
      case 4 =>
        println("Zde je celý soupis, Pane.")
        separate()
        estate.foreach( e =>
          println(e.toReadableString)
        )
        endSubdialog()
      case 5 =>
        println(randomText(farewells))
        programExit()
        System.exit(0)
      case _ =>
        println(randomText(wrongAnswers))
        mainMenuReactions(readAnswer)
    }
  }









}