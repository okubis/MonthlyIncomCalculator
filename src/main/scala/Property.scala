import util.{fixLength, getSignum, nroll,Separator}

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

  def toReadableString: String ={
    val sb = new StringBuilder()
    sb.append(fixLength("Majetek:"))
    sb.append(what)
    sb.append("\n")
    sb.append(fixLength("Místo:"))
    sb.append(where)
    sb.append("\n")
    sb.append(fixLength("Počet:"))
    sb.append(amount)
    sb.append("\n")
    sb.append(fixLength("Spravuje:"))
    sb.append(admin)
    sb.append("\n")
    sb.append(fixLength("Příjem:"))
    if(incomeIsPercentual){
      sb.append(s"$baseIncomePerUnitPerMonth * (1k100)")
    }else{
      sb.append(s"$baseIncomePerUnitPerMonth +/- (${n}k${dice} * $const)")
    }
    sb.append("\n")
    sb.append(fixLength("Poznámka:"))
    sb.append(note)
    sb.append("\n")
    sb.append(Separator)
    sb.toString()
  }

  override def toString: String = s"\n$what\t$where\t$amount\t$baseIncomePerUnitPerMonth\t$dice\t$n\t$const\t$incomeIsPercentual\t$admin\t$note"

  def getMonthlyIncomeInfo:(String,Double) = {
    val sb = new StringBuilder()
    sb.append(fixLength("Majetek:"))
    sb.append(what)
    sb.append("\n")
    sb.append(fixLength("Místo:"))
    sb.append(where)
    sb.append("\n")
    sb.append(fixLength("Spravuje:"))
    sb.append(admin)
    sb.append("\n")
    sb.append(fixLength("Zisk:"))
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
    def const:Double = line(6).toDouble
    def incomeIsPercentual:Boolean = line(7).toBoolean
    def admin:String = line(8)
    def note:String = line(9)
  }
}