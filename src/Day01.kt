fun main() {
    fun part1(input: List<String>): Int {
        var maxWeight = 0
        var currentWeight = 0

        input.forEach {
            if (it.isNotBlank()) {
                currentWeight += it.toInt()
            } else {
                maxWeight = maxOf(maxWeight, currentWeight)
                currentWeight = 0
            }
        }

        return maxOf(maxWeight, currentWeight)
    }

    fun part2(input: List<String>): Int {
        val allWeights = mutableListOf<Int>()
        var currentWeight = 0

        input.forEach {
            if (it.isNotBlank()) {
                currentWeight += it.toInt()
            } else {
                allWeights.add(currentWeight)
                currentWeight = 0
            }
        }
        if (currentWeight != 0) allWeights.add(currentWeight)

        return allWeights.sortedDescending().subList(0, 3).sum()
    }

    // Test
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    // Final
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}