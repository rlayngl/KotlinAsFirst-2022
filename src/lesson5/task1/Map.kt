@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1



// Урок 5: ассоциативные массивы и множества
// Максимальное количество баллов = 14
// Рекомендуемое количество баллов = 9
// Вместе с предыдущими уроками = 33/47

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая (2 балла)
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val result = mutableMapOf<Int, MutableList<String>>()
    for ((name,grade) in grades) {
        val intermediateResult = result[grade]
        if (intermediateResult != null) intermediateResult.add(name)
        else result[grade] = mutableListOf(name)
    }
    return result
}


/**
 * Простая (2 балла)
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    if (a.isEmpty()) return true
    var count = 0
    for ((first,_) in a) {
        if ((first in b) && a[first] == b[first]) count++
        return (count == a.size)
    }
    return false
}



/**
 * Простая (2 балла)
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for ((key) in b) {
        if (b[key] == a[key]) a.remove(key)
    }
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяющихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val resultList = mutableListOf<String>()
    for (element in b.indices) {
        if ((b[element] in a) && (b[element] !in resultList)) resultList.add(b[element])
    }
    return resultList
}

/**
 * Средняя (3 балла)
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val result = mutableMapOf<String, String>()
    for ((service, phone) in mapB) {
        val phones: String = if ((service in mapA) && (mapA[service] != phone)) {
            "$phone, ${mapA[service]}"
        } else phone
        result += (service to phones)
    }
    for ((service, phone) in mapA) {
        val phones: String = if ((service in mapB) && (mapB[service] != phone)) {
            "$phone, ${mapB[service]}"
        } else phone
        result += (service to phones)
    }
    return result
}



/**
 * Средняя (4 балла)
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val result = mutableMapOf<String, Double>()
    val counter = mutableMapOf<String, Int>()
    for ((name, price) in stockPrices) {
        if (name in result) {
            counter[name] = counter[name]!! + 1
            result[name] = result[name]!! + price
        } else {
            counter += name to 1
            result += name to price
        }
    }
    for ((name, count) in counter) {
        result[name] = result[name]!! / count
    }
    return result
}

/**
 * Средняя (4 балла)
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var result: String? = null
    var firstPrice = 0.0
    val intermediateResult = mutableMapOf<String, Pair<String, Double>>()
    for ((name, characteristic) in stuff) {
        if (characteristic.first == kind) {
            intermediateResult += (name to characteristic)
            firstPrice = characteristic.second
        }
    }
    for ((name, characteristic) in intermediateResult) {
        if (characteristic.second <= firstPrice) {
            firstPrice = characteristic.second
            result = name
        }
    }
    return result
}

/**
 * Средняя (3 балла)
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    if (word == "") return true
    if (chars.isEmpty()) return false
    val charsWithLowerCase = mutableSetOf<Char>()
    for (char in chars) {
        charsWithLowerCase += char.lowercaseChar()
    }
    val wordByLettersWithLowerCase = mutableSetOf<Char>()
    for (letter in word) {
        wordByLettersWithLowerCase += letter.lowercaseChar()
    }
    wordByLettersWithLowerCase -= charsWithLowerCase
    return if (wordByLettersWithLowerCase.isEmpty()) true else false
}

/**
 * Средняя (4 балла)
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val setOfLetters = emptySet<String>().toMutableSet()
    setOfLetters += list
    for (letter in list) {
        when (letter) {
            in setOfLetters -> { setOfLetters.remove(letter) }
            !in result -> { result += letter to 2 }
            else -> result[letter] = result[letter]!! + 1
        }
    }
    return result
}

/**
 * Средняя (3 балла)
 *
 * Для заданного списка слов определить, содержит ли он анаграммы.
 * Два слова здесь считаются анаграммами, если они имеют одинаковую длину
 * и одно можно составить из второго перестановкой его букв.
 * Скажем, тор и рот или роза и азор это анаграммы,
 * а поле и полено -- нет.
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    if (words.size == 1) return false
    var wordByLetters: List<Char>
    val memory = mutableListOf<String>()
    for ((index, word) in words.withIndex()) {
        if (word in memory) return true
        if (word == "") memory += ""
        if (index == 0) continue
        wordByLetters = word.toList()
        if (word == words[0]) return true
        if (wordByLetters.sorted() == (words[0]).toList().sorted()) return true
    }
    return false
}

/**
 * Сложная (5 баллов)
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 *
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Оставлять пустой список знакомых для людей, которые их не имеют (см. EvilGnome ниже),
 * в том числе для случая, когда данного человека нет в ключах, но он есть в значениях
 * (см. GoodGnome ниже).
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta"),
 *       "Friend" to setOf("GoodGnome"),
 *       "EvilGnome" to setOf()
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat"),
 *          "Friend" to setOf("GoodGnome"),
 *          "EvilGnome" to setOf(),
 *          "GoodGnome" to setOf()
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> = TODO()

/**
 * Сложная (6 баллов)
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    var result = -1 to -1
    if (list.size == 1) return result
    var memory = 0
    for ((count, element) in list.withIndex()) {
        if (((number - element) in list) && (number - element != element)) {
            result = list.indexOf(number - element) to list.indexOf(element)
        }
        if (element == memory) result = list.indexOf(element) to count
        if (number / 2 == element) {
            memory = element
        }
    }
    return result
}

/**
 * Очень сложная (8 баллов)
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> =TODO()
/**    val possibleVariants = emptyMap<String, Pair<Int, Int>>().toMutableMap()
    var weight = 0
    var price = 0
    for ((name, characteristic) in treasures) {
        if (weight + characteristic.first > capacity) continue
        possibleVariants += (name to characteristic)
        weight += characteristic.first
        price += characteristic.second
    }
    if (possibleVariants.isEmpty()) return emptySet()
    val difference = differenceOfMaps(treasures, possibleVariants)
    for ((name, characteristic) in difference) {
        for ((name1, characteristic1) in revertedMap(possibleVariants)) {
            if ((characteristic.first <= characteristic1.first) && (characteristic.second > characteristic1.second)) {
                possibleVariants += name to characteristic
                possibleVariants.remove(name1)
                weight = weight + characteristic.first - characteristic1.first
                price = price + characteristic.second - characteristic1.second
            }
            if ((characteristic.first + weight <= capacity)) possibleVariants += name to characteristic
        }
    }
    for ((name, characteristic) in difference) {
        var setOfNames: Set<String>
        for ((name1, characteristic1) in revertedMap(possibleVariants)) {
            var someTreasuresWeight = characteristic1.first
            var someTreasuresPrice = characteristic1.second
            setOfNames = emptySet()
            setOfNames + name1
            if ((someTreasuresWeight < characteristic.first) || (someTreasuresPrice > characteristic.second)) continue
            for ((name2, characteristic2) in revertedMap(possibleVariants)) {
                setOfNames + name2
                someTreasuresWeight += characteristic2.first
                someTreasuresPrice += characteristic2.second
                if ((someTreasuresWeight < characteristic.first)
                    || (someTreasuresPrice > characteristic.second)
                ) continue
                if ((capacity >= weight - someTreasuresWeight + characteristic.first)
                    && (price <= price - someTreasuresPrice + characteristic.second)
                ) {
                    for (name3 in setOfNames) {
                        possibleVariants.remove(name3)
                    }
                    possibleVariants += name to characteristic
                }
            }
        }
    }
    val result = emptyList<String>().toMutableList()
    for ((name, _) in possibleVariants) {
        result += name
    }
    return revertedSet(result)
}




fun revertedMap(map: Map<String, Pair<Int, Int>>): Map<String, Pair<Int, Int>> {    //создана, чтобы создать массив, обратный входному
    val listOfNames = mutableListOf<String>()
    val listOfWeights = mutableListOf<Int>()
    val listOfPrices = mutableListOf<Int>()
    for ((name, characteristic) in map) {
        listOfNames.add(0, name)
        listOfWeights.add(0, characteristic.first)
        listOfPrices.add(0, characteristic.second)
    }
    val result = mutableMapOf<String, Pair<Int,Int>>()
    for (name in listOfNames) {
        result += name to (listOfWeights[listOfNames.indexOf(name)] to (listOfPrices[listOfNames.indexOf(name)]))
    }
    return result
}

fun revertedSet(set: List<String>): Set<String> {  //создана, чтобы создавать из списка множество с элементами расположенными в обратном порядке
    val result = mutableSetOf<String>()
    var count = set.size - 1
    for (element in set) {
        result += set[count]
        count--
    }
    return result
}

fun differenceOfMaps(map1: Map<String, Pair<Int, Int>>, map2: Map<String, Pair<Int, Int>>): Map<String, Pair<Int, Int>> {
    if (map1.size <= map2.size) return emptyMap()
    val result = emptyMap<String, Pair<Int, Int>>().toMutableMap()
    for ((name, characteristic) in map1) {
        if (name !in map2) result += name to characteristic
    }
    return result
}







 * fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
val possibleVariants = mutableMapOf<String, Pair<Int, Int>>()
var result = mutableListOf<String>() //то же, что и result, только его элементы расположены в обратном порядке
val preResult = mutableSetOf<String>()
for ((name, characteristic) in treasures) {
if (characteristic.first <= capacity) {
preResult += name
possibleVariants += name to characteristic  //сразу удаляет все слишкмо "тяжелые" для рюкзака сокровища
}
}
if (preResult.size == 1) return preResult
var weightOfProducts: Int
var intermediateResult: Int
var resultWeight = 0
var intermediateMapOfProducts = emptyMap<String, Pair<Int, Int>>()
for ((name1, characteristic1) in possibleVariants) {  //оставляет первый элемент массива, после чего перебирает его еще раз
val listOfNames = emptyList<String>().toMutableList()
val preIntermediateMapOfProducts = emptyMap<String, Pair<Int, Int>>().toMutableMap()
preIntermediateMapOfProducts += name1 to characteristic1
listOfNames += name1
weightOfProducts = characteristic1.first
intermediateResult = characteristic1.second
for ((name2, characteristic2) in possibleVariants) {
if ((name1 != name2)
&& (weightOfProducts + characteristic2.first <= capacity))
{
preIntermediateMapOfProducts += name2 to characteristic2
weightOfProducts += characteristic2.first
intermediateResult += characteristic2.second
listOfNames += name2
}
}
result = listOfNames
resultWeight = weightOfProducts
intermediateMapOfProducts = preIntermediateMapOfProducts
break
}
if (intermediateMapOfProducts.size < treasures.size) { //поиск тяжелых, но не таких ценных сокровищ, которые можно заменить
val otherPreResult = intermediateMapOfProducts.toMutableMap()
for ((name2, characteristic2)
in differenceOfMaps(possibleVariants, intermediateMapOfProducts)){
for ((name1, characteristic1) in revertedMap(intermediateMapOfProducts)) {
if ((characteristic2.first + resultWeight - characteristic1.first <= capacity)) {
if ((characteristic2.first + resultWeight > capacity)
&& (characteristic2.second > characteristic1.second)) {
resultWeight = resultWeight - characteristic1.first + characteristic2.first
result.remove(name1)
result.add(name2)
break
}
if (characteristic2.first + resultWeight <= capacity) {
result.add(name2)
resultWeight += characteristic2.first
break
}
}
var changingPrice = characteristic1.second
var changingWeight = characteristic1.first
otherPreResult += name1 to characteristic1
for ((name3, characteristic3) in revertedMap(intermediateMapOfProducts)) {
if (name3 == name1) break
changingWeight += characteristic3.first
changingPrice += characteristic3.second
otherPreResult += name3 to characteristic3
if ((characteristic2.second > changingPrice) && (characteristic2.first <= changingWeight)) {
for ((name, _) in otherPreResult) {
result += name2
result -= name
}
}
}
}
}
}
return revertedSet(result)
}


fun revertedMap(map: Map<String, Pair<Int, Int>>): Map<String, Pair<Int, Int>> {    //создана, чтобы создать массив, обратный входному
val listOfNames = mutableListOf<String>()
val listOfWeights = mutableListOf<Int>()
val listOfPrices = mutableListOf<Int>()
for ((name, characteristic) in map) {
listOfNames.add(0, name)
listOfWeights.add(0, characteristic.first)
listOfPrices.add(0, characteristic.second)
}
val result = mutableMapOf<String, Pair<Int,Int>>()
for (name in listOfNames) {
result += name to (listOfWeights[listOfNames.indexOf(name)] to (listOfPrices[listOfNames.indexOf(name)]))
}
return result
}

fun revertedSet(set: List<String>): Set<String> {  //создана, чтобы создавать из списка множество с элементами расположенными в обратном порядке
val result = mutableSetOf<String>()
var count = set.size - 1
for (element in set) {
result += set[count]
count--
}
return result
}

fun differenceOfMaps(map1: Map<String, Pair<Int, Int>>, map2: Map<String, Pair<Int, Int>>): Map<String, Pair<Int, Int>> {
if (map1.size <= map2.size) return emptyMap()
val result = emptyMap<String, Pair<Int, Int>>().toMutableMap()
for ((name, characteristic) in map1) {
if (name !in map2) result += name to characteristic
}
return result
}
 */