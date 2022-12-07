/**
 * https://adventofcode.com/2022/day/4
 */
fun main() {
    val inputLines = readInputFileLines(4).filter { it.isNotBlank() }

    val count = inputLines.map { line ->
        val rangeStrList = line.split(',')
        val (firstRange, secondRange) = rangeStrList.map {
            val (rangeStart, rangeEnd) = it.split('-')
            rangeStart.toInt()..rangeEnd.toInt()
        }
        firstRange to secondRange
    }.count { (firstRange, secondRange) ->
        firstRange in secondRange || secondRange in firstRange
    }
    println(count)
    check(count == 464)

}

private operator fun IntRange.contains(other: IntRange) = other.first in this && other.last in this