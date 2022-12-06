fun main() {
    fun firstValidWindow(input: String, windowSize: Int): Int {
        var windowStart = 0
        val windowChars = mutableSetOf<Char>()

        run inputLoop@ {
            input.forEach {
                while (it in windowChars) {
                    windowChars.remove(input[windowStart++])
                }
                windowChars.add(it)
                if (windowChars.size == windowSize) return@inputLoop
            }
        }

        return windowStart + windowSize
    }

    fun part1(input: String): Int {
        return firstValidWindow(input, 4)
    }

    fun part2(input: String): Int {
        return firstValidWindow(input, 14)
    }

    // Test
    val testInput = readInput("Day06_test")
    check(part1(testInput[0]) == 7)
    check(part2(testInput[0]) == 19)

    // Final
    val input = readInput("Day06")
    println(part1(input[0]))
    println(part2(input[0]))
}