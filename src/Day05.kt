fun main() {
    fun parsedInstructions(input: List<String>): MutableList<Map<String, Int>> {
        val parsedInstructions = mutableListOf<Map<String, Int>>()
        val instructions = input.subList(
            input.indexOfFirst { it.isEmpty() } + 1,
            input.size
        )

        instructions.forEach {
            val splitInstruction = it.split(" ")
            val instructionMap = mapOf(
                "count" to splitInstruction[1].toInt(),
                "origin" to splitInstruction[3].toInt(),
                "destination" to splitInstruction[5].toInt()
            )
            parsedInstructions.add(instructionMap)
        }

        return parsedInstructions
    }

    fun parsedStacks(input: List<String>): MutableList<MutableList<Char>> {
        val stacks = mutableListOf<MutableList<Char>>()

        run stackLoop@ {
            input.forEach {
                if (it.isEmpty()) return@stackLoop
                var index = 1
                var stack = 0
                while (it.length > index) {
                    if (stack >= stacks.size) stacks.add(mutableListOf())
                    if (it[index].isLetter()) {
                        stacks[stack].add(it[index])
                    }
                    index += 4
                    stack++
                }
            }
        }

        return stacks
    }

    fun moveItems9000(stacks: MutableList<MutableList<Char>>, count: Int, origin: Int, destination: Int) {
        val originStack = stacks[origin - 1]
        val destinationStack = stacks[destination - 1]

        repeat (count) {
            destinationStack.add(0, originStack.removeFirst())
        }
    }

    fun moveItems9001(stacks: MutableList<MutableList<Char>>, count: Int, origin: Int, destination: Int) {
        val originStack = stacks[origin - 1]
        val destinationStack = stacks[destination - 1]

        destinationStack.addAll(0, originStack.subList(0, count))
        originStack.subList(0, count).clear()
    }

    fun topItems(stacks: MutableList<MutableList<Char>>): String {
        val topItems = StringBuilder()
        stacks.forEach {
            topItems.append(it.first())
        }
        return topItems.toString()
    }

    fun part1(input: List<String>): String {
        val stacks = parsedStacks(input)
        val instructions = parsedInstructions(input)

        instructions.forEach {
            moveItems9000(stacks, it["count"]!!, it["origin"]!!, it["destination"]!!)
        }

        return topItems(stacks)
    }

    fun part2(input: List<String>): String {
        val stacks = parsedStacks(input)
        val instructions = parsedInstructions(input)

        instructions.forEach {
            moveItems9001(stacks, it["count"]!!, it["origin"]!!, it["destination"]!!)
        }

        return topItems(stacks)
    }

    // Test
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    // Final
    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}