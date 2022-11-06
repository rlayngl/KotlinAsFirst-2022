package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
            "Хлеб" to 50.0,
            "Молоко" to 100.0
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко"),
                itemCosts
            )
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                itemCosts
            )
        )
        assertEquals(
            0.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                mapOf()
            )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
            "Quagmire" to "+1-800-555-0143",
            "Adam's Ribs" to "+82-000-555-2960",
            "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю Котлин".split(" "),
                "как-то"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю таки Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я люблю Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
            mutableSetOf("Я", "люблю", "Котлин"),
            buildWordSet("Я люблю Котлин".split(" "))
        )
        assertEquals(
            mutableSetOf("Котлин", "люблю", "Я"),
            buildWordSet("Я люблю люблю Котлин".split(" "))
        )
        assertEquals(
            mutableSetOf<String>(),
            buildWordSet(listOf())
        )
    }

    @Test
    @Tag("2")
    fun buildGrades() {
        assertEquals(
            mapOf<Int, List<String>>(),
            buildGrades(mapOf())
        )
        assertEquals(
            mapOf(5 to listOf("Михаил", "Семён"), 3 to listOf("Марат")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
                .mapValues { (_, v) -> v.sorted() }
        )
        assertEquals(
            mapOf(3 to listOf("Марат", "Михаил", "Семён")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
                .mapValues { (_, v) -> v.sorted() }
        )
    }

    @Test
    @Tag("2")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
        assertTrue(containsIn(mapOf(), mapOf("" to "")))
        assertFalse(containsIn(mapOf("" to "", "xtB5u" to "#q#d#cytLv"), mapOf("" to "")))
    }

    @Test
    @Tag("2")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(mapOf("a" to "z", "b" to "c"), from)

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(mapOf("a" to "z", "b" to "c"), from)

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(mapOf("b" to "c"), from)
    }

    @Test
    @Tag("2")
    fun whoAreInBoth() {
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
            listOf("Marat"),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
        assertEquals(
            listOf(""),
            whoAreInBoth(listOf(""), listOf("", ""))
        )
        assertEquals(
            listOf("a"),
            whoAreInBoth(listOf("", "a"), listOf("a"))
        )
    }

    @Test
    @Tag("3")
    fun mergePhoneBooks() {
        assertEquals(
            mapOf("Emergency" to "112"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112", "Fire department" to "01"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf(
                "Emergency" to "112, 911",
                "Police" to "02, 102",
                "Ambulance" to "03, 103",
                "Fire department" to "01"
            ),
            mergePhoneBooks(
                mapOf("Emergency" to "112", "Fire department" to "01", "Ambulance" to "03", "Police" to "02"),
                mapOf("Emergency" to "911", "Police" to "102", "Ambulance" to "103")
            )
        )
    }

    @Test
    @Tag("4")
    fun averageStockPrice() {
        assertEquals(
            mapOf<String, Double>(),
            averageStockPrice(listOf())
        )
        assertEquals(
            mapOf("MSFT" to 100.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 45.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
        assertEquals(
            mapOf("MSFT" to 200.0, "NFLX" to 45.0),
            averageStockPrice(
                listOf(
                    "MSFT" to 100.0,
                    "MSFT" to 200.0,
                    "MSFT" to 300.0,
                    "NFLX" to 40.0,
                    "NFLX" to 50.0,
                    "MSFT" to 200.0
                )
            )
        )
    }

    @Test
    @Tag("4")
    fun findCheapestStuff() {
        assertNull(
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "торт"
            )
        )
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "печенье"
            )
        )
    }

    @Test
    @Tag("3")
    fun canBuildFrom() {
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(emptyList(), ""))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'M', 'r'), "Marat"))
        assertFalse(canBuildFrom(listOf('a', 'q', 'r', 'm'), "Marat"))
        assertTrue(canBuildFrom(listOf('a'), ""))
        assertTrue(canBuildFrom(listOf('A'), "a"))
        assertTrue(canBuildFrom(listOf('a'), "A"))
        assertFalse(canBuildFrom(listOf('a', 'a'), "aw"))
    }

    @Test
    @Tag("4")
    fun extractRepeats() {
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(emptyList())
        )
        assertEquals(
            mapOf("a" to 2),
            extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(listOf("a", "b", "c"))
        )
        assertEquals(
            mapOf(("a" to 4), ("b" to 2), ("c" to 3)),
            extractRepeats(listOf("a", "b", "a", "a", "c", "c", "b", "l", "c", "a"))
        )
    }

    @Test
    @Tag("3")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(listOf("рот")))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
        assertFalse(hasAnagrams(listOf("поле", "полено")))
        assertTrue(hasAnagrams(listOf("лунь", "нуль")))
        assertFalse(hasAnagrams(listOf("рот")))
        assertFalse(hasAnagrams(listOf("")))
        assertTrue(hasAnagrams(listOf("", "")))
        assertFalse(hasAnagrams(listOf("a", "")))
    }

    @Test
    @Tag("5")
    fun propagateHandshakes() {
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail"),
                "Mikhail" to setOf("Sveta", "Marat"),
                "Friend" to setOf("GoodGnome"),
                "EvilGnome" to setOf(),
                "GoodGnome" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta"),
                    "Friend" to setOf("GoodGnome"),
                    "EvilGnome" to setOf()
                )
            )
        )
    }

    @Test
    @Tag("6")
    fun findSumOfTwo() {
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
            Pair(0, 2),
            findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 2, 3), 6)
        )
        assertEquals(
            Pair(3, 5),
            findSumOfTwo(listOf(1, 2, 3, 9, 10, 27, 4), 36)
        )
    }

    @Test
    @Tag("8")
    fun bagPacking() {
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                850
            )
        )
        assertEquals(
            emptySet<String>(),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                450
            )
        )

        assertEquals(
            setOf("1", "0"),
            bagPacking(
                mapOf("0" to (1 to 1), "1" to (1 to 1), "2" to (1 to 1), "3" to (2 to 1)),
                2
            )
        )
        assertEquals(
            setOf("1", "0"),
            bagPacking(
                mapOf("0" to (1 to 1), "1" to (1 to 1)),
                2
            )
        )
        assertEquals(
            setOf("Кольцо", "Цепь", "Слиток"),
            bagPacking(
                mapOf(
                    "Кубок" to (500 to 2000),
                    "Слиток" to (1000 to 5000),
                    "Цепь" to (200 to 4000),
                    "Кольцо" to (100 to 1000)
                ),
                1500
            )
        )
        assertEquals(
            setOf("Кольцо", "Цепь"),
            bagPacking(
                mapOf(
                    "Кубок" to (500 to 2000),
                    "Слиток" to (1000 to 5000),
                    "Цепь" to (200 to 4000),
                    "Кольцо" to (100 to 1000)
                ),
                600
            )
        )
        assertEquals(
            setOf("0"),
            bagPacking(
                mapOf(
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (2 to 1)
                ),
                1
            )
        )
        assertEquals(
            setOf("17", "16", "15", "14", "13", "12", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"),
            bagPacking(
                mapOf(
                    "0" to (1 to 1),
                    "1" to (1 to 1),
                    "2" to (1 to 1),
                    "3" to (1 to 1),
                    "4" to (1 to 1),
                    "5" to (152 to 1),
                    "6" to (497 to 1),
                    "7" to (457 to 1),
                    "8" to (1 to 1),
                    "9" to (1 to 1),
                    "10" to (1 to 1),
                    "11" to (368 to 1),
                    "12" to (1 to 1),
                    "13" to (149 to 484),
                    "14" to (480 to 331),
                    "15" to (1 to 324),
                    "16" to (149 to 324),
                    "17" to (140 to 299)
                ),
                2351
            )
        )
    }


    @Test
    @Tag("0")
    fun revertedMap() {
        assertEquals(
            mapOf("3" to (5 to 8), "2" to (9 to 1), "1" to (0 to 11), "0" to (2 to 3)),
            revertedMap(
                mapOf("0" to (2 to 3), "1" to (0 to 11), "2" to (9 to 1), "3" to (5 to 8))
            )
        )
    }


    @Test
    @Tag("0")
    fun revertedSet() {
        assertEquals(
            setOf("4", "3", "2", "1"),
            revertedSet(
                listOf("1", "2", "3", "4")
            )
        )
    }

    @Test
    @Tag("0")
    fun differenceOfMaps() {
        assertEquals(
            mapOf("1" to (1 to 1)),
            differenceOfMaps(
                mapOf("0" to (1 to 1), "1" to (1 to 1), "2" to (1 to 1)),
                mapOf("0" to (1 to 1), "2" to (1 to 1))
            )
        )
        assertEquals(
            emptyMap<String, Pair<Int, Int>>(),
            differenceOfMaps(
                mapOf("0" to (1 to 1), "1" to (1 to 1), "2" to (1 to 1)),
                mapOf("0" to (1 to 1), "1" to (1 to 1), "2" to (1 to 1), "3" to (1 to 1))
            )
        )
    }
}
