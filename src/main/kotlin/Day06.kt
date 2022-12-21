/**
 * https://adventofcode.com/2022/day/6
 */
fun main() {
    val input = readInputFileLines(6).filter { it.isNotBlank() }.single()

    val solution1 = input.findStartMarker(4)
    solution(1, solution1, 1356)

    val solution2 = input.findStartMarker(14)
    solution(2, solution2, 2564)
}

private fun String.findStartMarker(size: Int) = asSequence()
    .windowed(size)
    .withIndex()
    .first { (_, list) ->
        val distinctSize = list.distinct().size
        distinctSize == size
    }.index + size
