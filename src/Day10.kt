import kotlin.math.abs

fun main() {
    class SignalCalculator(
        val input: List<String>,
        val cyclesToConsider: List<Int>,
        val shouldUpdateScreen: Boolean
    ) {
        private var cycle = 1
        private var registerValue = 1
        private var sumOfConsideredCycles = 0
        private val screenHeight = cyclesToConsider.size
        private val screenWidth = cyclesToConsider[0]
        private val screen = List(screenHeight) { MutableList(screenWidth) { '.' } }

        init {
            processInstructions()
        }

        private fun parsedInstruction(instruction: String): String {
            return instruction.substring(0, 4)
        }

        private fun parsedValueToAdd(instruction: String): Int {
            return instruction.substring(5).toInt()
        }

        private fun addConsideredCyclesToSum() {
            if (cycle in cyclesToConsider) sumOfConsideredCycles += (cycle * registerValue)
        }

        private fun updateScreen() {
            if (!shouldUpdateScreen) return

            val crtVerticalPosition = (cycle - 1).floorDiv(screenWidth)
            val crtHorizontalPosition = (cycle - 1) % screenWidth
            val spriteHorizontalPosition = registerValue % screenWidth

            if (abs(crtHorizontalPosition - spriteHorizontalPosition) <= 1) {
                screen[crtVerticalPosition][crtHorizontalPosition] = '#'
            }
        }

        private fun incrementCycle() {
            cycle++
            addConsideredCyclesToSum()
        }

        private fun noopHandler() {
            updateScreen()
            incrementCycle()
            updateScreen()
        }

        private fun addxHandler(value: Int) {
            updateScreen()
            incrementCycle()
            updateScreen()
            registerValue += value
            incrementCycle()
        }

        private fun processInstructions() {
            input.forEach {
                if (parsedInstruction(it) == "noop") {
                    noopHandler()
                } else {
                    addxHandler(parsedValueToAdd(it))
                }
            }
        }

        fun getSumOfConsideredCycles(): Int {
            return sumOfConsideredCycles
        }

        fun drawScreen() {
            screen.forEach {
                println(it.joinToString(" "))
            }
        }
    }

    fun part1(input: List<String>): Int {
        val cyclesToConsider = listOf(20, 60, 100, 140, 180, 220)
        val signalCalculator = SignalCalculator(input, cyclesToConsider, false)
        return signalCalculator.getSumOfConsideredCycles()
    }

    fun part2(input: List<String>) {
        val cyclesToConsider = listOf(40, 80, 120, 160, 200, 240)
        val signalCalculator = SignalCalculator(input, cyclesToConsider, true)
        signalCalculator.drawScreen()
    }

    // Test
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)

    // Final
    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}