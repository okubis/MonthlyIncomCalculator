import java.nio.charset.Charset

object Texts {
  val encoding = Charset.defaultCharset()
  def modifyCharset(array: Array[String]):Array[String] =
    array.map(s => scala.io.Source.fromBytes(s.getBytes(),encoding.toString).mkString)

  val greetings:Array[String] = modifyCharset(
    Array(
      "Vítejte zpět Pane. Čím Vám mohu posloužit?",
      "Doufám, že se Vám stále daří excelence, mohu Vám nějak pomoci?",
      "Ctihodný pán by se jistě rád ráčil zaobírat správou svých statků, smím nějak posloužit?",
      "Smím vám doněst číši rudého Andegavenského, než opět zasednete k papírování Pane?",
      "Vždy mi bylo ctí spravovat Váš majetek dle svého nejlepšího svědomí, mohu posloužit i nyní?"
    )
  )

  val wasteOp:Array[String] = modifyCharset(
    Array(
      "Nevyužité a proto nepoužitelné",
      "Nikam nevede",
      "Zdržíš pouze sám sebe a svého pomocníka",
      "Pouze zbytečně ohřeješ planetu výpočtem k ničemu"
    )
  )

  val computeIncomeIntro:Array[String] = modifyCharset(
    Array(
      "Chtěl bych vidět své zisky.",
      "Rád bych viděl jak se dařilo mému panství.",
      "Předlož mi své účty správče, chci vidět jak jsi rozmnožil mé jmění.",
      "Přines mi měšec s mým ziskem."
    )
  )

  val addEstateIntro:Array[String] = modifyCharset(
    Array(
      "Podařilo se rozmnožit své jmění, přines brk a kalamář a zapiš to do účetních knih.",
      "Mám pro tebe novou práci, podařilo se mi rozmnožit své statky.",
      "Připij si se mnou na oslavu, než zapíšeš můj nový majetek do knih."
    )
  )

  val modifyEstateIntro:Array[String] = modifyCharset(
    Array(
      "Informace o některé z mých nemovitostí se změninily, přines účtní knihy a přepiš to.",
      "Musím upravit nesrovnalost v účetních knihách, některé údaje jsou špatně zaznamenány"
    )
  )

  val removeEstateIntro:Array[String] = modifyCharset(
    Array(
      "Žel musím vyškrtnou část svého majetku z evidence.",
      "Tvá práce se ti ulehčí, žel budu muset odepsat část svého majetku, který spravuješ."
    )
  )

  val exitOp:Array[String] = modifyCharset(
    Array(
      "Dnes nic nepotřebuji, chvátám na výpravu.",
      "Musím už jít, zatím se dál dobře starej o mé statky.",
    )
  )

  val wrongAnswers:Array[String] = modifyCharset(
    Array(
      "Obávám se Pane, že vaše odpověd není správná.",
      "Asi jsem Vás přeslechl,mohl byste to prosím zopakovat?",
      "Ano, Excelence, to je jistě zajímavé, byť to tak zcela nesouvisí s tématem naší konverzace.",
      "Myslím, že tímto bych se vzhledem ke své pozici neměl zaneprazdňovat"
    )
  )

  val farewells:Array[String] = modifyCharset(
    Array(
      "Budu netrpělivě očekávat Váš návrat.",
      "Přeji mnoho zdaru a pěkný den, Excelence.",
      "Jdu se vrátit ke svým povinnostem, doufám, že mi brzi přinesete další práci."
    )
  )

  val wrongInput:Array[String] = modifyCharset(
    Array(
      "Jste mistrem vtipu, pane.",
      "Vidím, že dnes máte žertovnou náladu sire, ale raději bych záleřitosti vyřídil rychle.",
      "Ano, Excelence, to je jistě zajímavé, byť to tak zcela nesouvisí s tématem naší konverzace.",
      "Myslím, že tímto bych se vzhledem ke své pozici neměl zaneprazdňovat"
    )
  )

  val adminChoiceIntro:Array[String] = modifyCharset(
    Array(
      "Komu ze svých správců tyto statky svěříte? Či snad máte na mysli někoho nového?",
      "Přivedete mi nějakého pomocníka, který bude o tento majetek pečovat, nebo jej svěříte někomu, kdo se již osvědčil?"
    )
  )

  val adminChoiceNewGuy:Array[String] = modifyCharset(
    Array(
      "Představím ti nového pomocníka.",
      "Mám na mysli někoho jiného.",
      "Tvůj nový pomocník se o to postará.",
      "Zde ti představým svého nového poddaného, který bude tento majetek spravovat."
    ).map(_ + " (zadám jméno místo čísla volby)")
  )
}
