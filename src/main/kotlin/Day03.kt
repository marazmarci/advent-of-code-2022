/**
 * https://adventofcode.com/2022/day/3
 */
fun main() {
    val inputLines = readInputFileLines(3).filterNot { it.isBlank() }

    // region Part 1
    val sumOfPriorities1 = inputLines.map {
        check(it.length.mod(2) == 0)
        val halfLength = it.length / 2
        val firstHalf = it.take(halfLength).toList()
        val secondHalf = it.takeLast(halfLength).toList()
        val intersection = firstHalf.intersect(secondHalf.toSet())
        intersection.single()
    }.map {
        it.priority
    }.sum()
    println(sumOfPriorities1)
    check(sumOfPriorities1 == 8176)
    // endregion Part 1

    // region Part 2
    val sumOfPriorities2 = inputLines.chunked(3)
        .map { rucksacks ->
            val (first, second, third) = rucksacks.map { it.toList() }
            val intersection = first.intersect(second.toSet()).intersect(third.toSet())
            intersection.single()
        }.map {
            it.priority
        }.sum()
    println(sumOfPriorities2)
    check(sumOfPriorities2 == 2689)
    // endregion Part 2

}

private val Char.priority
    get() = when (this) {
        in 'a'..'z' -> code - 'a'.code + 1 // 1..26
        in 'A'..'Z' -> code - 'A'.code + 27 // 27..52
        else -> error("invalid letter: $this")
    }
