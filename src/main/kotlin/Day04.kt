/**
 * https://adventofcode.com/2022/day/4
 */
fun main() {
    val inputLines = readInputFileLines(4).filter { it.isNotBlank() }

    val rangePairs = inputLines.map { line ->
        val rangeStrList = line.split(',')
        val (firstRange, secondRange) = rangeStrList.map {
            val (rangeStart, rangeEnd) = it.split('-')
            rangeStart.toInt()..rangeEnd.toInt()
        }
        firstRange to secondRange
    }

    val countPart1 = rangePairs.count { (firstRange, secondRange) ->
        firstRange in secondRange || secondRange in firstRange
    }
    println(countPart1)
    check(countPart1 == 464)

    // part 2:
    val countPart2 = rangePairs.count { (firstRange, secondRange) ->
        firstRange.overlaps(secondRange)
    }
    println(countPart2)
    check(countPart2 == 770)
}

private operator fun IntRange.contains(other: IntRange) = other.first in this && other.last in this

private fun IntRange.overlaps(other: IntRange) =
    other.first in this || other.last in this || first in other || last in other