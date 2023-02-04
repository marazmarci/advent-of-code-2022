import kotlin.math.sign

/**
 * https://adventofcode.com/2022/day/9
 */
fun main() {
    val inputLines = readInputFileLines(9).filter { it.isNotBlank() }

    var head = Vec2(0, 0)
    var tail = Vec2(0, 0)

    val visited = mutableSetOf(tail)

    val headSteps = buildList {
        inputLines.forEach {
            val (dirStr, stepsStr) = it.split(" ")
            val dir = when (dirStr.uppercase()) {
                "R" -> Vec2(1, 0)
                "L" -> Vec2(-1, 0)
                "U" -> Vec2(0, 1)
                "D" -> Vec2(0, -1)
                else -> error("")
            }
            val steps = stepsStr.toInt()
            repeat(steps) {
                add(dir)
            }
        }
    }

    for (headStep in headSteps) {
        head += headStep

        if (tail.isTouching(head)) {
            continue
        }

        val tailStep = tail.getStepTowardsHead(head)
        tail += tailStep
        visited.add(tail)
    }

    solution(1, visited.size, 0)

}

data class Vec2(
    val x: Int,
    val y: Int,
) {
    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)

    operator fun minus(other: Vec2) = Vec2(x - other.x, y - other.y)

    fun isTouching(other: Vec2) = with(this - other) {
        x in (-1..1) && y in (-1..1)
    }

    fun getStepTowardsHead(head: Vec2) = with(head - this) {
        Vec2(x.sign, y.sign)
    }
}

