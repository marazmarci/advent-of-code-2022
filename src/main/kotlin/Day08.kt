/**
 * https://adventofcode.com/2022/day/8
 */
fun main() {
    val inputLines = readInputFileLines(8).filter { it.isNotBlank() }

    val forest = inputLines.map { line ->
        line.map { char ->
            char.digitToInt()
        }
    }.toList()

    val height = forest.size
    val width = forest.map { it.size }.distinct().single()

    val yIndices = forest.indices
    val xIndices = forest.first().indices

    fun row(y: Int) = forest[y]
    fun column(x: Int) = forest.map { row -> row[x] }

    fun isTreeVisible(y: Int, x: Int): Boolean {
        if (x == 0 || x == xIndices.last || y == 0 || y == yIndices.last) {
            return true
        }

        val treeHeight = forest[y][x]

        val row = row(y)
        val leftSectionProducer = { row.slice(0 until x) }
        val rightSectionProducer = { row.slice(x + 1 until width) }

        val column = column(x).toList()
        val upSectionProducer = { column.slice(0 until y) }
        val downSectionProducer = { column.slice(y + 1 until height) }

        listOf(leftSectionProducer, rightSectionProducer, upSectionProducer, downSectionProducer).forEach { sectionProducer ->
            val section = sectionProducer()
            if (section.all { it < treeHeight }) {
                return true
            }
        }

        return false
    }

    var visibleCount = 0
    for (y in yIndices) {
        for (x in xIndices) {
            if (isTreeVisible(y, x)) {
                visibleCount++
            }
        }
    }

    solution(1, visibleCount, 1845)
}
