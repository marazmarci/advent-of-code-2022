/**
 * https://adventofcode.com/2022/day/3
 */
fun main() {
    val inputLines = readInputFileLines(3)

    val sumOfPriorities = inputLines
        .filterNot {
            it.isBlank()
        }.map {
            check(it.length.mod(2) == 0)
            val halfLength = it.length / 2
            val firstHalf = it.take(halfLength).toList()
            val secondHalf = it.takeLast(halfLength).toList()
            val intersection = firstHalf.intersect(secondHalf.toSet())
            val commonItem = intersection.single()
            commonItem
        }.map {
            it.priority
        }.sum()

    println(sumOfPriorities)
}

private val Char.priority
    get() = when (this) {
        in 'a'..'z' -> code - 'a'.code + 1 // 1..26
        in 'A'..'Z' -> code - 'A'.code + 27 // 27..52
        else -> error("invalid letter: $this")
    }
