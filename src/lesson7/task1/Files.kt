@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.write("")
            writer.newLine()
        } else if (line[0] == '_') {
            continue
        } else {
            writer.write(line)
            writer.newLine()
        }
    }
    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    var count: Int
    for (string in substrings) {
        count = 0
        result += string to 0
        for (line in File(inputName).readLines()) {
            if (string.lowercase() !in line.lowercase()) continue
            for (index in 0..(line.length - string.length)) {
                if (line.substring(index, index + string.length).lowercase() == string.lowercase()) count++
            }
        }
        result[string] = count
    }
    return result
}


/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */

val CORRECTION = mapOf("ы" to "и", "я" to "а", "ю" to "у", "Ы" to "И", "Я" to "А", "Ю" to "У")
val CHECKLIST = listOf("ж", "ч", "ш", "щ")
fun sibilants(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var correctedLine: StringBuilder
    for (line in File(inputName).readLines()) {
        correctedLine = StringBuilder()
        correctedLine.append(line)
        for (index in 0..correctedLine.length - 2) {
            if (correctedLine[index].lowercase() in CHECKLIST && correctedLine[index + 1].toString() in CORRECTION) {
                correctedLine.replace(index + 1, index + 2, CORRECTION[correctedLine[index + 1].toString()])
            }
        }
        writer.write(correctedLine.toString())
        writer.newLine()
    }
    writer.close()
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var theLongestLine = 0
    val linesWithoutIndent = mutableListOf<String>()
    for (line in File(inputName).readLines()) {
        val clearLine = line.trimIndent()
        linesWithoutIndent.add(clearLine)
        if (clearLine.length > theLongestLine) theLongestLine = clearLine.length
    }
    var count: Int
    var centeredLine: StringBuilder
    var listOfSpaces: MutableList<String>
    for (line in linesWithoutIndent) {
        centeredLine = StringBuilder()
        listOfSpaces = emptyList<String>().toMutableList()
        count = (theLongestLine - line.length) / 2
        while (count != 0) {
            listOfSpaces.add(" ")
            count--
        }
        centeredLine.append(listOfSpaces.joinToString(""))
        centeredLine.append(line)
        writer.write(centeredLine.toString())
        writer.newLine()
    }
    writer.close()
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var theLargestLine = 0
    var memory: Char
    val listOfClearLines = mutableListOf<String>()
    for (line in File(inputName).readLines()) {
        val clearLine = StringBuilder()
        clearLine.append(line.trimIndent())
        var lineLength = clearLine.length
            memory = ' ' //просто для инициализации
            for ((index, char) in clearLine.withIndex()) { //избавляемся от лишних пробелов внутри
                if (char == memory && index != 0 && char == ' ') {
                    clearLine.delete(index, index + 1)
                    lineLength--
                }
                memory = char
            }
        if (lineLength > theLargestLine) theLargestLine = lineLength
        listOfClearLines.add(clearLine.toString())
    }
    var listOfWords: MutableList<String>
    val lines = StringBuilder()
    var numberOfSpaces: Int
    var spaces: Int
    var count: Int
    var addingSpaces: Int
    var size: Int
    var stringOfSpaces: StringBuilder
    for (line in listOfClearLines) {
        listOfWords = line.split(" ").toMutableList()
        size = listOfWords.size
        numberOfSpaces = theLargestLine - (line.length - (size - 1))
        addingSpaces = size - 1
        if (listOfWords.isEmpty() || size == 1) {
            count = 0
            spaces = 0
        } else {
            spaces = numberOfSpaces / (size - 1)
            count = numberOfSpaces % (size - 1)
        }
        stringOfSpaces = StringBuilder()
        while (spaces != 0) {
            stringOfSpaces.append(" ")
            spaces--
        }
        for (word in listOfWords) {
            lines.append(word)
            if (addingSpaces == 0) continue
            lines.append(stringOfSpaces)
            if (count > 0) lines.append(" ")
            count--
            addingSpaces--
        }
        lines.append("\n")
    }
    writer.write(lines.toString())
    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val intermediateResult = mutableListOf<String>()
    val set = mutableSetOf<Char>()
    var memory = 0
    for (line  in File(inputName).readLines()) {
        for (char in line.lowercase()) {
            set.add(char)
        }
        if (set.size == line.length && line.length >= memory) {
            intermediateResult.add(line)
            memory = line.length
            //находим самое длинное слово и избавляемся от слов с повторяющимися буквами
        }
        set.clear()
    }
    for (line in intermediateResult) {
        if (line.length != memory) intermediateResult.remove(line)
    }
    writer.write(intermediateResult.joinToString(", "))
    writer.close()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines: StringBuilder = StringBuilder()
    val howMuchHyphens = howMuchHyphens(lhv, rhv)
    val digitNumberRHV = digitNumber(rhv)
    var count = howMuchHyphens.length - "$lhv".length
    while (count != 0) {
        lines.append(" ")
        count--
    }
    lines.append("$lhv")
    lines.append("\n")
    count = howMuchHyphens.length - digitNumberRHV - 1
    lines.append("*")
    while (count != 0) {
        lines.append(" ")
        count--
    }
    lines.append("$rhv")
    lines.append("\n")
    lines.append(howMuchHyphens)
    lines.append("\n")
    count = howMuchHyphens.length - "${lhv * (rhv % 10)}".length
    while (count != 0) {
        lines.append(" ")
        count--
    }
    lines.append("${lhv * (rhv % 10)}")
    lines.append("\n")
    var plusCount = digitNumberRHV - 1
    var digit = 2
    var extraCount = 2
    while (plusCount != 0) {
        count = howMuchHyphens.length - digitNumber((lhv * digitUnderNumber(rhv, extraCount))) - extraCount
        lines.append("+")
        while (count != 0) {
            lines.append(" ")
            count--
        }
        lines.append("${lhv * digitUnderNumber(rhv, extraCount)}")
        lines.append("\n")
        plusCount--
        extraCount++
        digit++
    }
    lines.append(howMuchHyphens)
    lines.append("\n")
    lines.append(" ")
    lines.append("${lhv * rhv}")
    writer.write(lines.toString())
    writer.close()
}


fun howMuchHyphens(lhv: Int, rhv: Int): String {   // находит необходимое число дефисов в printMultiplicationProcess
    var count = digitNumber(lhv * rhv) + 1
    val hyphensLine: StringBuilder = StringBuilder()
    while (count != 0) {
        hyphensLine.append("-")
        count--
    }
    return hyphensLine.toString()
}

fun digitUnderNumber(rhv: Int, n: Int): Int { // находит n-ный символ в числе
    var number = rhv
    var result = 0
    var count = n
    while (count != 0) {
        result = number % 10
        number /= 10
        count--
    }
    return result
}

/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines: StringBuilder = StringBuilder()
    val listOfNumbers = lhv.toString().toList()
    val answer = lhv / rhv
    val digitsRemainder = digitNumber(lhv % rhv)
    val digitsLHV = digitNumber(lhv)
    val digitsRHV = digitNumber(rhv)
    var firstDividend = 0
    var number = 0
    for (int in listOfNumbers) {
        number = number * 10 + int.digitToInt()
        if (number / rhv > 0) {
            firstDividend = number
            break
        }
    }
    val digitsFirstDividend = digitNumber(firstDividend)
    val firstDifference = firstDividend / rhv * rhv
    val digitsFirstDifference = digitNumber(firstDifference)
    if (answer == 0 && digitsLHV == 1) {
        lines.append(" $lhv | $rhv")
    } else if (digitsFirstDifference != digitsFirstDividend
        || (answer == 0 && digitsLHV >= 2)) {
        lines.append("$lhv | $rhv")
    } else {
        lines.append(" $lhv | $rhv")
    }
    lines.append("\n")
    var count: Int
    var extraSpace = 0
    if (lhv < rhv) {
        count = digitsLHV - 2
        if (count < 0) {
            count = 0
            extraSpace = 0
        }
        while (count != 0) {
            lines.append(" ")
            count--
        }
        lines.append("-0   0")
    } else {
        lines.append("-${firstDifference}")
        count = digitsLHV - digitsFirstDifference + 3
        if (digitsFirstDifference != digitsFirstDividend)
            count = digitsLHV - digitsFirstDifference + 2
        extraSpace = 1 + digitsFirstDifference -
                digitNumber(firstDividend - firstDifference)
        while (count != 0) {
            lines.append(" ")
            count--
        }
        lines.append("$answer")
    }
    lines.append("\n")
    var countOfHyphens: Int
    countOfHyphens = digitsFirstDifference + 1
    if (answer == 0 && digitsLHV >= 2) countOfHyphens = digitsLHV
    while (countOfHyphens != 0) {
        lines.append("-")
        countOfHyphens--
    }
    countOfHyphens = digitsFirstDifference + 1
    if (answer == 0 && digitsLHV >= 2) countOfHyphens = digitsLHV
    lines.append("\n")
    var stages = digitNumber(answer) - 1 //максимальное количество сносов цифры из lhv
    var memoryOfSpaces = 0
    count = digitsFirstDifference
    var memoryNumber: Int
    memoryNumber = firstDividend - firstDifference
    var digitMemoryNumber: Int  //изменяемое число символов memoryNumber
    var digitMemoryNextNumber: Int  //число символов memoryNextNumber
    var memoryNextNumber : Int
        while (stages != 0) {
        digitMemoryNumber = 0
        memoryOfSpaces = extraSpace
        memoryNextNumber = (memoryNumber * 10 + listOfNumbers[count].digitToInt()) / rhv * rhv
        digitMemoryNextNumber = digitNumber(memoryNextNumber)
        while (memoryOfSpaces != 0) {
            lines.append(" ")
            memoryOfSpaces--
        }
        if (count + 1 <= listOfNumbers.size) {
            lines.append("$memoryNumber")
            lines.append("${listOfNumbers[count]}")
        }
        if (memoryNumber == 0) digitMemoryNumber++
        lines.append("\n")
        memoryNumber = (memoryNumber * 10 + listOfNumbers[count].digitToInt())
        digitMemoryNumber += digitNumber(memoryNumber)
        memoryOfSpaces = if (digitMemoryNumber != digitMemoryNextNumber)
            extraSpace + digitMemoryNumber - digitMemoryNextNumber - 1
        else extraSpace - 1
        while (memoryOfSpaces != 0) {
            lines.append(" ")
            memoryOfSpaces--
        }
        memoryOfSpaces = extraSpace
        if (digitMemoryNextNumber == digitMemoryNumber) memoryOfSpaces -= 1
        lines.append("-$memoryNextNumber")
        lines.append("\n")
        while (memoryOfSpaces != 0) {
            lines.append(" ")
            memoryOfSpaces--
        }
        memoryOfSpaces = extraSpace
        if (digitMemoryNextNumber == digitMemoryNumber) memoryOfSpaces -= 1
        countOfHyphens = digitMemoryNextNumber + 1
        if (countOfHyphens < digitsRemainder) countOfHyphens = digitsRemainder
        while (countOfHyphens != 0) {
            lines.append("-")
            countOfHyphens--
        }
        countOfHyphens = digitMemoryNextNumber + 1
        if (stages == 1 && countOfHyphens < digitsRemainder) countOfHyphens = digitsRemainder
        lines.append("\n")
        stages--
        count++
        if (digitMemoryNumber == digitsRHV) extraSpace--
        extraSpace = extraSpace + 1 + digitsRHV - digitNumber(memoryNumber - memoryNextNumber)
        memoryNumber %= rhv
    }
    memoryOfSpaces = memoryOfSpaces + countOfHyphens - digitsRemainder
    if (memoryOfSpaces >= 0) {
        while (memoryOfSpaces != 0) {
            lines.append(" ")
            memoryOfSpaces--
        }
    }
    lines.append("${lhv % rhv}")
    writer.write(lines.toString())
    writer.close()
}


/**fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
val writer = File(outputName).bufferedWriter()
val lines: StringBuilder = StringBuilder()
val theFirstSubtraction = rhv * digitUnderNumber((lhv / rhv), digitNumber(lhv / rhv)) //первое число второй строчки (вычитаемое)
val digitsOfFirstSubtraction = digitNumber(theFirstSubtraction)
val digitsOfRemainder = digitNumber(lhv % rhv)
val digitsOfLHV = digitNumber(lhv)
var lastSpaces = 0
val answer = lhv / rhv
//пробелы, добавляемые в конце. Если lhv сразу делится на rhv, число пробелов будет равно количеству символов theFirstSubtraction
if (digitsOfLHV != 1 && digitsOfLHV > digitNumber(answer * rhv))
lines.append("$lhv | $rhv") else {
lines.append(" $lhv | $rhv")
lastSpaces = 1
}
lastSpaces += digitsOfLHV - digitsOfRemainder
lines.append("\n")
var firstSpaces = 0
if (answer == 0) firstSpaces = digitsOfLHV - 2
while (firstSpaces != 0) {
lines.append(" ")
firstSpaces--
}
lines.append("-${theFirstSubtraction}")
var count =
if (answer == 0)
3
else if (digitsOfLHV != 1  && digitsOfLHV > digitNumber(answer * rhv))
digitsOfLHV - digitsOfFirstSubtraction + 2
else
digitsOfLHV - digitsOfFirstSubtraction + 3 //число пробелов во второй строчке
while (count != 0) {
lines.append(" ")
count--
}
lines.append("$answer")
lines.append("\n")
count = digitsOfFirstSubtraction + 1
if (digitsOfRemainder > count) count = digitsOfRemainder //число дефисов в третьей строчке, если остаток превышает предыдущее число
while (count != 0) {
lines.append("-")
count--
}
lines.append("\n")
var countOfSpaces: Int
var countOfRanks = digitNumber(theFirstSubtraction) + 1 //число разрядов. Далее используется для "сноса" очередной цифры из lhv
var nextStageNumber: Int //число будущей строки (остаток от разности)
var countOfHyphens: Int //число дефисов
var memory = theFirstSubtraction
var stages = digitNumber(answer) - 1 //максимальное количество сносов цифры из lhv
var extraSpace = 0 //дополнительный пробел, который появляется при каждом новом сносе цифры из lhv
var memoryOfSpaces: Int //память числа пробелов в зависимости от ситуации
var digitsOfNextStageNumber: Int
var digitsOfCurrentRemainder: Int
while (stages != 0) {\\h
nextStageNumber = someFirstDigits(lhv, countOfRanks) - memory * 10
digitsOfNextStageNumber = digitNumber(nextStageNumber)
digitsOfCurrentRemainder = digitNumber(nextStageNumber / rhv * rhv) //количество символов в текущем остатке
memoryOfSpaces = if (nextStageNumber < 10 || nextStageNumber % rhv == 0)
digitsOfFirstSubtraction + 1 - digitsOfNextStageNumber
else
digitsOfFirstSubtraction + 2 - digitsOfNextStageNumber
memory = memory * 10 + nextStageNumber / rhv * rhv
countOfSpaces = memoryOfSpaces + extraSpace
while (countOfSpaces != 0) {
lines.append(" ")
countOfSpaces--
}
if (nextStageNumber < 10) {
extraSpace++
lines.append("0$nextStageNumber")
} else if (nextStageNumber % rhv == 0) {
extraSpace++
lines.append(" $nextStageNumber")
} else lines.append("$nextStageNumber")
lines.append("\n")
memoryOfSpaces = if (nextStageNumber < 10 || nextStageNumber % rhv == 0) digitsOfFirstSubtraction - digitsOfCurrentRemainder
else digitsOfFirstSubtraction + 1 - digitsOfCurrentRemainder
countOfSpaces = memoryOfSpaces + extraSpace
while (countOfSpaces != 0) {
lines.append(" ")
countOfSpaces--
}
lines.append("-${nextStageNumber / rhv * rhv}")
lines.append("\n")
countOfSpaces = memoryOfSpaces + extraSpace
while (countOfSpaces != 0) {
lines.append(" ")
countOfSpaces--
}
countOfHyphens = digitsOfCurrentRemainder + 1
while (countOfHyphens != 0) {
lines.append("-")
countOfHyphens--
}
lines.append("\n")
countOfRanks++
stages--
extraSpace++
if (nextStageNumber % rhv == 0 || nextStageNumber < 10) extraSpace--
}
while (lastSpaces != 0) {
lines.append(" ")
lastSpaces--
}
lines.append("${lhv % rhv}")
writer.write(lines.toString())
writer.close()
}



fun someFirstDigits(number: Int, n: Int): Int { //первые n цифры числа number
val length = "$number".length
var firstDigits = number
var count = length - n
while (count != 0) {
firstDigits /= 10
count--
}
return firstDigits
}*/