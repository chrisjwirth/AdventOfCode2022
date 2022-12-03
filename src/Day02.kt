fun main() {
    fun part1AsChoice(letter: Char): String? {
        return when(letter) {
            'A', 'X' -> "Rock"
            'B', 'Y' -> "Paper"
            'C', 'Z' -> "Scissors"
            else -> null
        }
    }

    fun part2AsChoice(ourLetter: Char, opponentChoice: String): String? {
        val winningMatch = mapOf(
            "Rock" to "Paper",
            "Paper" to "Scissors",
            "Scissors" to "Rock"
        )
        val loosingMatch = mapOf(
            "Rock" to "Scissors",
            "Paper" to "Rock",
            "Scissors" to "Paper"
        )
        return when(ourLetter) {
            'X' -> loosingMatch[opponentChoice]
            'Y' -> opponentChoice
            'Z' -> winningMatch[opponentChoice]
            else -> null
        }
    }

    fun pointsForOutcome(ourChoice: String, opponentChoice: String): Int {
        return if (
            ourChoice == "Rock" && opponentChoice == "Scissors" ||
            ourChoice == "Paper" && opponentChoice == "Rock" ||
            ourChoice == "Scissors" && opponentChoice == "Paper"
        ) {
            6
        } else if (ourChoice == opponentChoice) {
            3
        } else {
            0
        }
    }

    fun pointsForChoice(choice: String): Int {
        return when(choice) {
            "Rock" -> 1
            "Paper" -> 2
            "Scissors" -> 3
            else -> 0
        }
    }

    fun totalPointsForGame(ourChoice: String, opponentChoice: String): Int {
        return pointsForOutcome(ourChoice, opponentChoice) + pointsForChoice(ourChoice)
    }

    fun part1(input: List<String>): Int {
        var score = 0

        input.forEach {
            val ourChoice = part1AsChoice(it[2])
            val opponentChoice = part1AsChoice(it[0])
            score += totalPointsForGame(ourChoice!!, opponentChoice!!)
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0

        input.forEach {
            val opponentChoice = part1AsChoice(it[0])
            val ourChoice = part2AsChoice(it[2], opponentChoice!!)
            score += totalPointsForGame(ourChoice!!, opponentChoice)
        }

        return score
    }

    // Test
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    // Final
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}