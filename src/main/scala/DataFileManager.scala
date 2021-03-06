import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.{Files, Paths}

import scala.io.Source

case class DataFileManager(path: String) {


  def append(data: String):Unit = {
    val fw = new FileWriter(path,true)
    try {
      fw.write(data)
    }catch {
      case e:Exception => Console.err.print(e.getMessage)
    }
    finally fw.close()
  }

  def rewriteDataWith(newData:String):Unit = {
    reinitDataFile()
    append(newData)
  }

  def loadEstate:List[Property] = {
    var iterator:Iterator[Array[String]]  =                          // parse tsv into lines slit by tab
      Source.fromFile(new File(path)).getLines().drop(2).map(_.split("\t"))
    transform(iterator)
  }

  private def transform(it:Iterator[Array[String]]):List[Property] = {
    import DataParser.DataLine
    it.filter(_.nonEmpty).filter(_.length >= 9).map{
      d: Array[String] =>
        Property(d.what,d.where,d.amount,d.baseIncomePerUnitPerMonth,d.dice,d.numberOfRolls,d.const,d.incomeIsPercentual,d.admin,d.note)
    }.toList
  }

  def initData():Unit = {
    import java.nio.file.{Files, Paths}
    val exists = Files.exists(Paths.get(path))
    if(!exists) {
      reinitDataFile()
    }
  }

  private def reinitDataFile():Unit = {
    val p = Paths.get(path)
    Files.createFile(p)

    val lines = "what\twhere\tamount\tbaseIncomePerUnitPerMonth\tdice\tn\tconst\tincomeIsInPercents\tadministrator\tnote\n" +
      "A haunted dwarfmine\tsomewhere in the woods\t1\t0\t20\t1\t1\tfalse\tGruusBazaddan\tan example of what should a property log look like"

    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file,false))
    bw.write(lines)
    bw.close()
  }
}
