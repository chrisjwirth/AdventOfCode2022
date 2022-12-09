import kotlin.math.abs

fun main() {
    open class TailTracker(numberOfKnots: Int) {
        private val head = Knot(0, 0)
        private val tail = Knot(0, 0)
        private val knots = if (numberOfKnots == 2) {
            arrayOf(head, tail)
        } else {
            val middleKnots = Array(numberOfKnots - 2) { Knot(0, 0) }
            arrayOf(head, *middleKnots, tail)
        }
        private var tailLocations = mutableSetOf(Pair(0, 0))

        private inner class Knot(
            var x: Int,
            var y: Int
        ) {
            fun move(direction: Char) {
                when (direction) {
                    'U' -> y++
                    'D' -> y--
                    'R' -> x++
                    'L' -> x--
                }
            }
        }

        private fun leaderAndFollowerXDifference(leader: Knot, follower: Knot): Int {
            return abs(leader.x - follower.x)
        }

        private fun leaderAndFollowerYDifference(leader: Knot, follower: Knot): Int {
            return abs(leader.y - follower.y)
        }

        private fun nonDiagonalFollowerMovementRequired(leader: Knot, follower: Knot): Boolean {
            return leaderAndFollowerXDifference(leader, follower) == 2 ||
                    leaderAndFollowerYDifference(leader, follower) == 2
        }

        private fun diagonalFollowerMovementRequired(leader: Knot, follower: Knot): Boolean {
            return (leaderAndFollowerXDifference(leader, follower) >= 1 &&
                    leaderAndFollowerYDifference(leader, follower) == 2) ||
                    (leaderAndFollowerXDifference(leader, follower) == 2 &&
                    leaderAndFollowerYDifference(leader, follower) >= 1)
        }

        private fun calculateXDirection(leader: Knot, follower: Knot): Char {
            return if ((leader.x - follower.x) >= 1) {
                'R'
            } else {
                'L'
            }
        }

        private fun calculateYDirection(leader: Knot, follower: Knot): Char {
            return if ((leader.y - follower.y) >= 1) {
                'U'
            } else {
                'D'
            }
        }

        private fun moveFollowerDiagonally(leader: Knot, follower: Knot) {
            follower.move(calculateXDirection(leader, follower))
            follower.move(calculateYDirection(leader, follower))
        }

        private fun moveFollowerNonDiagonally(leader: Knot, follower: Knot) {
            if (leaderAndFollowerXDifference(leader, follower) >= 1) {
                follower.move(calculateXDirection(leader, follower))
            } else {
                follower.move(calculateYDirection(leader, follower))
            }
        }

        private fun moveFollower(leader: Knot, follower: Knot) {
            if (diagonalFollowerMovementRequired(leader, follower)) {
                moveFollowerDiagonally(leader, follower)
            } else if (nonDiagonalFollowerMovementRequired(leader, follower)) {
                moveFollowerNonDiagonally(leader, follower)
            }
        }

        fun moveKnots(headDirection: Char, headDistance: Int) {
            repeat(headDistance) {
                head.move(headDirection)
                for (knotNumber in 1 until knots.size) {
                    val leader = knots[knotNumber - 1]
                    val follower = knots[knotNumber]
                    moveFollower(leader, follower)
                }
                tailLocations.add(Pair(tail.x, tail.y))
            }
        }

        fun getNumberUniqueTailLocations(): Int {
            return tailLocations.size
        }
    }

    fun numberUniqueTailLocations(numberOfKnots: Int, input: List<String>): Int {
        val tailTracker = TailTracker(numberOfKnots)
        input.forEach {
            val direction = it[0]
            val distance = it.substring(2).toInt()
            tailTracker.moveKnots(direction, distance)
        }
        return tailTracker.getNumberUniqueTailLocations()
    }

    fun part1(input: List<String>): Int {
        return numberUniqueTailLocations(2, input)
    }

    fun part2(input: List<String>): Int {
        return numberUniqueTailLocations(10, input)
    }

    // Test
    val testInput1 = readInput("Day09_test_1")
    check(part1(testInput1) == 13)
    check(part2(testInput1) == 1)
    val testInput2 = readInput("Day09_test_2")
    check(part2(testInput2) == 36)

    // Final
    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}