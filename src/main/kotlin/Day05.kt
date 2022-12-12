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

    fun List<Stack<Crate>>.clone() = map { stack ->
        stack.fold(Stack<Crate>()) { newStacks, crate ->
            newStacks.apply {
                push(crate)
            }
        }
    }

    fun List<Stack<Crate>>.getTopOfStacksAsString() = map {
        it.peek()
    }.joinToString("") {
        it.letter.toString()
    }

    stacks.clone().also {
        CrateMover9000.performMoves(it, moves)
        solution(1, it.getTopOfStacksAsString(), "TPGVQPFDH")
    }

    stacks.clone().also {
        CrateMover9001.performMoves(it, moves)
        solution(2, it.getTopOfStacksAsString(), "DMRDFRHHH")
    }
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

private sealed class CrateMover {
    fun performMoves(stacks: List<Stack<Crate>>, moves: List<Move>) {
        moves.forEach {
            performMove(stacks, it)
        }
    }

    protected abstract fun performMove(stacks: List<Stack<Crate>>, move: Move)
}

private object CrateMover9000 : CrateMover() {
    override fun performMove(stacks: List<Stack<Crate>>, move: Move) {
        repeat(move.amount) {
            val item = stacks[move.from].pop()
            stacks[move.to].push(item)
        }
    }
}

private object CrateMover9001 : CrateMover() {
    override fun performMove(stacks: List<Stack<Crate>>, move: Move) {
        val pickedUpCrates = buildList {
            repeat(move.amount) {
                val item = stacks[move.from].pop()
                add(item)
            }
        }.reversed()
        pickedUpCrates.forEach {
            stacks[move.to].push(it)
        }
    }
}
