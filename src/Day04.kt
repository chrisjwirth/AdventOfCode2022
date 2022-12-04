fun main() {
    fun rangeAsLowHighInts(range: String): Pair<Int, Int> {
        val (rangeLow, rangeHigh) = range.split("-")
        return rangeLow.toInt() to rangeHigh.toInt()
    }

    fun rangeContainsOther(range1: String, range2: String): Boolean {
        val (range1Low, range1High) = rangeAsLowHighInts(range1)
        val (range2Low, range2High) = rangeAsLowHighInts(range2)
        return (
                (range1Low >= range2Low && range1High <= range2High)
                        ||
                        (range2Low >= range1Low && range2High <= range1High))
    }

    fun rangesOverlap(range1: String, range2: String): Boolean {
        val (range1Low, range1High) = rangeAsLowHighInts(range1)
        val (range2Low, range2High) = rangeAsLowHighInts(range2)
        return (
                (range1Low in range2Low..range2High)
                        ||
                        (range2Low in range1Low..range1High))
    }

    fun part1(input: List<String>): Int {
        var containedAssignments = 0
        input.forEach {
            val (range1, range2) = it.split(",")
            if (rangeContainsOther(range1, range2)) containedAssignments++
        }
        return containedAssignments
    }

    fun part2(input: List<String>): Int {
        var overlappingAssignments = 0
        input.forEach {
            val (range1, range2) = it.split(",")
            if (rangesOverlap(range1, range2)) overlappingAssignments++
        }
        return overlappingAssignments
    }

    // Test
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Final
    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}