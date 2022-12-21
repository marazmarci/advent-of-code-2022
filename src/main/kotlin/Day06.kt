/**
 * https://adventofcode.com/2022/day/6
 */
fun main() {
    val input = readInputFileLines(6).filter { it.isNotBlank() }.single()

    val index = input.asSequence().windowed(4).withIndex().first { (_, list) ->
        val distinctSize = list.distinct().size
        distinctSize == 4
    }.index + 4

    solution(1, index, 1356)
}
