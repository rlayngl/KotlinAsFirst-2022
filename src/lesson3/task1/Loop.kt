@file:Suppress("UNUSED_PARAMETER", "KotlinConstantConditions", "UnusedEquals", "NAME_SHADOWING")

package lesson3.task1

import lesson1.task1.sqr
import kotlin.math.sqrt
import kotlin.math.min
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.max

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var count = 1
    var number = abs(n)
    while (number / 10 > 0) {
        count++
        number /= 10
    }
    return count
}


/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var count = 2
    var a = 1
    var b = 1
    var c = 0
    if (n == 1 || n == 2) {
        return 1
    } else while (count != n) {
        c = a + b
        count++
        a = b
        b = c
    }
    return c
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (m in 2..sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return m
    }
    return n
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    if (minDivisor(n) != n)
    for (m in n / 2 downTo sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return m
        }
    return 1
}

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var count = 0
    var x = x
    while (x != 1) {
        if (x % 2 != 0) {
            x = 3 * x + 1
        } else {
            x /= 2
        }
        count++
    }
    return count
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    val b = min(m, n)
    if (m == n) {
        return m
    } else for (a in b downTo 2) {
        if ((m % a == 0) && (n % a == 0)) return m * n / a
    }
    return m * n
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val max = max(m, n)
    val min = min(m, n)
    for (a in 2..sqrt(min.toDouble()).toInt()) {
        return !(((m % a == 0) && (n % a == 0)) || (max % min == 0))
    }
    return true
}

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var result = 0
    var number = n
    while (number != 0) {
        result = result * 10 + number % 10
        number /= 10
    }
    return result
}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var result = 0
    var number = n
    while (number != 0) {
        result = result * 10 + number % 10
        number /= 10
        if (n / 10 == 0) {
            return true
        } else if (result == n) {
            return true
        }
    }
    return false
}

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var digit: Int
    val lastDigit = n % 10
    var number = n
    while (number != 0) {
        digit = number % 10
        number /= 10
        if (digit != lastDigit) return true
    }
    return false
}


/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double = TODO() //пока не могу до конца понять условие задачи для ее решения

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больши х значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double = TODO() //пока не могу до конца понять условие задачи для ее решения

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var count = 0
    var result = 1
    for (i in 1..n) {
        var number = sqr(i)
        var b = sqr(i)
        var degree = -1
        while ((b >= 1) && (count != n)) {
            while (number > 0) {
                degree++
                number /= 10
            }
            result = ((sqr(i) / 10.0.pow(degree)).toInt()) % 10
            count++
            degree--
            b /= 10
        }
    }
    return result
}
/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var count = 0
    var result = 1
    for (i in 1..n) {
        var number = fib(i)
        var b = fib(i)
        var degree = -1
        while ((b >= 1) && (count != n)) {
            while (number > 0) {
                degree++
                number /= 10
            }
            result = ((fib(i) / 10.0.pow(degree)).toInt()) % 10
            count++
            degree--
            b /= 10
        }
    }
    return result
}



