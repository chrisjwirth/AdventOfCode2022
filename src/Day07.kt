fun main() {
    class Dir(
        val name: String,
        val parentDir: Dir? = null,
        val childrenDirs: MutableMap<String, Dir> = mutableMapOf(),
        var sizeOfDirectFiles: Int = 0,
        var totalSize: Int? = null
    )

    fun linePurpose(line: String): String {
        return if (line.substring(0, 4) == "$ cd") {
            "cd"
        } else if (line.substring(0, 4) == "$ ls") {
            "ls"
        } else if (line.substring(0, 3) == "dir") {
            "dir"
        } else {
            "fileSize"
        }
    }

    fun buildDirTree(input: List<String>): MutableMap<String, Dir> {
        val dirs = mutableMapOf(
            "/" to Dir("/")
        )
        var currentDir = Dir("")

        input.forEach inputLoop@ {
            when (linePurpose(it)) {
                "cd" -> {
                    val dirName = it.substring(5)
                    currentDir = if (dirName == "..") {
                        currentDir.parentDir!!
                    } else {
                        dirs[currentDir.name + dirName]!!
                    }
                }
                "dir" -> {
                    val childDirName = currentDir.name + it.substring(4)
                    val childDir = Dir(childDirName, currentDir)
                    currentDir.childrenDirs[childDirName] = childDir
                    dirs[childDirName] = childDir
                }
                "fileSize" -> {
                    val fileSize = it.split(" ")[0].toInt()
                    currentDir.sizeOfDirectFiles += fileSize
                }
                else -> return@inputLoop
            }
        }

        return dirs
    }

    fun dirSize(dir: Dir): Int {
        var size = dir.sizeOfDirectFiles
        val stack = mutableListOf<Dir>()
        stack.addAll(dir.childrenDirs.values)

        while (stack.isNotEmpty()) {
            val childDir = stack.removeLast()
            if (childDir.totalSize != null) {
                size += childDir.totalSize!!
            } else {
                size += childDir.sizeOfDirectFiles
                stack.addAll(childDir.childrenDirs.values)
            }
        }

        return size
    }

    fun listOfDirSizes(dirs: MutableMap<String, Dir>): MutableList<Int> {
        val listOfSizes = mutableListOf<Int>()

        dirs.values.forEach {
            listOfSizes.add(dirSize(it))
        }

        return listOfSizes
    }

    fun part1(input: List<String>): Int {
        val dirs = buildDirTree(input)
        val listOfDirSizes = listOfDirSizes(dirs)

        return listOfDirSizes.filter { it <= 100_000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val dirs = buildDirTree(input)
        val listOfDirSizes = listOfDirSizes(dirs)

        val totalSpace = 70_000_000
        val remainingSpace = totalSpace - listOfDirSizes.maxOf { it }
        val spaceToFree = 30_000_000 - remainingSpace

        return listOfDirSizes.filter { it >= spaceToFree }.min()
    }

    // Test
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    // Final
    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}