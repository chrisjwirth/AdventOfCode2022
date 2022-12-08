import kotlin.math.max

fun main() {
    class VisibleTreeCounter(input: List<String>) {
        val rowLength = input.size
        val colLength = input[0].length
        val lastRow = rowLength - 1
        val lastCol = colLength - 1
        val grid = List(rowLength) { List(colLength) { Tree() } }

        inner class Tree(
            var row: Int = 0,
            var col: Int = 0,
            var height: Int = 0,
            var isOnBorder: Boolean = false,
            var maxTreeAbove: Int = 0,
            var maxTreeRight: Int = 0,
            var maxTreeBelow: Int = 0,
            var maxTreeLeft: Int = 0,
            var isVisible: Boolean = false
        )

        private fun determineIfTreeIsOnBorder(tree: Tree) {
            if (tree.row == 0 || tree.row == lastRow) tree.isOnBorder = true
            if (tree.col == 0 || tree.col == lastCol) tree.isOnBorder = true
            if (tree.isOnBorder) tree.isVisible = true
        }

        init {
            for (row in 0..lastRow) {
                for (col in 0..lastCol) {
                    val tree = grid[row][col]
                    tree.row = row
                    tree.col = col
                    tree.height = input[tree.row][tree.col].digitToInt()
                    determineIfTreeIsOnBorder(tree)
                }
            }
        }

        private fun calculateMaxTreeAbove(tree: Tree) {
            if (tree.row > 0) {
                val treeAbove = grid[tree.row - 1][tree.col]
                tree.maxTreeAbove = max(treeAbove.height, treeAbove.maxTreeAbove)
            }

            if (tree.height > tree.maxTreeAbove) tree.isVisible = true
        }

        private fun calculateMaxTreeRight(tree: Tree) {
            if (tree.col < lastCol) {
                val treeRight = grid[tree.row][tree.col + 1]
                tree.maxTreeRight = max(treeRight.height, treeRight.maxTreeRight)
            }

            if (tree.height > tree.maxTreeRight) tree.isVisible = true
        }

        private fun calculateMaxTreeBelow(tree: Tree) {
            if (tree.row < lastRow) {
                val treeBelow = grid[tree.row + 1][tree.col]
                tree.maxTreeBelow = max(treeBelow.height, treeBelow.maxTreeBelow)
            }

            if (tree.height > tree.maxTreeBelow) tree.isVisible = true
        }

        private fun calculateMaxTreeLeft(tree: Tree) {
            if (tree.col > 0) {
                val treeLeft = grid[tree.row][tree.col - 1]
                tree.maxTreeLeft = max(treeLeft.height, treeLeft.maxTreeLeft)
            }

            if (tree.height > tree.maxTreeLeft) tree.isVisible = true
        }

        private fun scenicScoreUp(tree: Tree): Int {
            var visibleTrees = 0

            for (row in (tree.row - 1)downTo 0) {
                visibleTrees++
                val comparisonTree = grid[row][tree.col]
                if (tree.height <= comparisonTree.height) break
            }

            return visibleTrees
        }

        private fun scenicScoreRight(tree: Tree): Int {
            var visibleTrees = 0

            for (col in (tree.col + 1) .. lastCol) {
                visibleTrees++
                val comparisonTree = grid[tree.row][col]
                if (tree.height <= comparisonTree.height) break
            }

            return visibleTrees
        }

        private fun scenicScoreDown(tree: Tree): Int {
            var visibleTrees = 0

            for (row in (tree.row + 1) .. lastRow) {
                visibleTrees++
                val comparisonTree = grid[row][tree.col]
                if (tree.height <= comparisonTree.height) break
            }

            return visibleTrees
        }

        private fun scenicScoreLeft(tree: Tree): Int {
            var visibleTrees = 0

            for (col in (tree.col - 1) downTo 0) {
                visibleTrees++
                val comparisonTree = grid[tree.row][col]
                if (tree.height <= comparisonTree.height) break
            }

            return visibleTrees
        }

        private fun calculateScenicScore(tree: Tree): Int {
            return if (tree.isOnBorder) {
                0
            } else {
                scenicScoreUp(tree) * scenicScoreRight(tree) * scenicScoreDown(tree) * scenicScoreLeft(tree)
            }
        }

        fun numberVisibleTrees(): Int {
            var numberVisibleTrees = 0

            // First pass - upper left to bottom right
            for (row in 0..lastRow) {
                for (col in 0..lastCol) {
                    val tree = grid[row][col]
                    calculateMaxTreeAbove(tree)
                    calculateMaxTreeLeft(tree)
                }
            }

            // Second pass - bottom right to upper left
            for (row in lastRow downTo 0) {
                for (col in lastCol downTo 0) {
                    val tree = grid[row][col]
                    calculateMaxTreeBelow(tree)
                    calculateMaxTreeRight(tree)
                    if (tree.isVisible) numberVisibleTrees++
                }
            }

            return numberVisibleTrees
        }

        fun maxScenicScore(): Int {
            var maxScenicScore = 0

            for (row in 0..lastRow) {
                for (col in 0..lastCol) {
                    val tree = grid[row][col]
                    val treeScenicScore = calculateScenicScore(tree)
                    maxScenicScore = max(maxScenicScore, treeScenicScore)
                }
            }

            return maxScenicScore
        }
    }

    fun part1(input: List<String>): Int {
        val visibleTreeCounter = VisibleTreeCounter(input)
        return visibleTreeCounter.numberVisibleTrees()
    }

    fun part2(input: List<String>): Int {
        val visibleTreeCounter = VisibleTreeCounter(input)
        return visibleTreeCounter.maxScenicScore()
    }

    // Test
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    // Final
    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}