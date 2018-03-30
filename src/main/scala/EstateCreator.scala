import Main.estate
import Texts.{adminChoiceIntro, adminChoiceNewGuy}
import util.{dictionaryChoice, multichoice, randomText, readString}

object EstateCreator {

  def loadNewEstate:Property = {
    loadEstateInput()
  }

  private def loadEstateInput():Property = {
    println(randomText(adminChoiceIntro))
    val listOfAdmins = estate.map(_.admin).distinct
    val reactions = dictionaryChoice(listOfAdmins) _
    val admin = multichoice(
      adminChoiceIntro)(
      listOfAdmins.map(s => Array(s)),s"_ ~ ${randomText(adminChoiceNewGuy)}\n")(
      readString,reactions)
    println()
    println()

    System.exit(1)
    null
  }

}
