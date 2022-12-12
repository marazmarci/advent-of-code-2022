import java.util.Stack

/**
 * https://adventofcode.com/2022/day/5
 */
fun main() {
    val inputLines = readInputFileLines(5).toList().dropLastWhile { it.isEmpty() }.asSequence()
    val (stacksLines, movesLines) = inputLines.split {
        it.isEmpty()
    }.toList().also {
        check(it.size == 2)
    }

    val stacks = parseStacks(stacksLines)

    val moves = parseMoves(movesLines)

    moves.forEach { (amount, from, to) ->
        repeat(amount) {
            val item = stacks[from].pop()
            stacks[to].push(item)
        }
    }

    val topOfStacks = stacks.map {
        it.peek()
    }.joinToString("") {
        it.letter.toString()
    }
    solution(1, topOfStacks, "TPGVQPFDH")
}

private fun parseMoves(movesLines: List<String>): List<Move> {
    val pattern = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    return movesLines.map {
        val match = pattern.matchEntire(it)!!
        val (amount, from, to) = match.destructured
        Move(amount.toInt(), from.toInt() - 1, to.toInt() - 1)
    }
}

private data class Move(
    val amount: Int,
    val from: Int,
    val to: Int,
)

private fun parseStacks(stacksLines: List<String>): List<Stack<Crate>> {
    val stackCount = stacksLines.last().trim().split(" +".toRegex()).run {
        count().also {
            check(last().toInt() == it)
        }
    }
    val maxStackSize = stacksLines.size - 1

    fun getNthStackXCoord(stack: Int) = 1 + (stack * 4)

    return (0 until stackCount).map { iStack ->
        val stack = Stack<Crate>()
        (0 until maxStackSize).reversed().filter { y ->
            val x = getNthStackXCoord(iStack)
            stacksLines[y][x - 1] == '[' && stacksLines[y][x + 1] == ']'
        }.map { y ->
            val x = getNthStackXCoord(iStack)
            val crate = Crate(stacksLines[y][x])
            stack.push(crate)
        }
        stack
    }
}

@JvmInline
private value class Crate(val letter: Char)