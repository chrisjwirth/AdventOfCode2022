fun main() {
    fun sharedItem(firstCompartment: CharSequence, secondComponent: CharSequence): Char {
        return firstCompartment.first { it in secondComponent }
    }

    fun sharedItem(first: CharSequence, second: CharSequence, third: CharSequence): Char {
        return first.first { it in second && it in third }
    }

    fun priorityOfSharedItem(sharedItem: Char): Int {
        return if (sharedItem.isLowerCase()) {
            sharedItem.code - 'a'.code + 1
        } else {
            sharedItem.code - 'A'.code + 27
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val firstCompartment = it.subSequence(0, it.length.floorDiv(2))
            val secondCompartment = it.subSequence(it.length.floorDiv(2), it.length)
            sum += priorityOfSharedItem(sharedItem(firstCompartment, secondCompartment))
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        var index = 0
        while (index < input.size) {
            val first = input[index++]
            val second = input[index++]
            val third = input[index++]
            sum += priorityOfSharedItem(sharedItem(first, second, third))
        }
        return sum
    }

    // Test
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    // Final
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}