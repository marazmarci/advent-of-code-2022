/**
 * https://adventofcode.com/2022/day/2
 */
fun main() {
    fun Shape.playRound(otherShape: Shape) = when {
        this == otherShape -> RoundOutcome.DRAW
        this.isStrongerThan(otherShape) -> RoundOutcome.WIN
        else -> RoundOutcome.LOSE
    }

    val inputLines = readInputFileLines(2)

    val rounds = inputLines.filter {
        it.isNotBlank()
    }.map {
        val (opponentsChoiceStr, myChoiceStr) = it.split(" ")
        opponentsChoiceStr.single() to myChoiceStr.single()
    }.map { (opponentsChoice, myChoice) ->
        Shape.getByLetter(opponentsChoice) to Shape.getByLetter(myChoice)
    }

    val totalScore = rounds.sumOf { (opponentsChoice, myChoice) ->
        val outcome = myChoice.playRound(opponentsChoice)
        outcome.score + myChoice.score
    }

    println(totalScore)
}

private enum class Shape(
    val letters: List<Char>,
    val score: Int,
    private val getWeakerShape: () -> Shape
) {
    ROCK(listOf('X', 'A'), 1, { SCISSOR }),
    PAPER(listOf('Y', 'B'), 2, { ROCK }),
    SCISSOR(listOf('Z', 'C'), 3, { PAPER });

    fun isStrongerThan(otherShape: Shape) = getWeakerShape.invoke() == otherShape

    companion object {
        fun getByLetter(letter: Char) = values().first {
            it.letters.contains(letter)
        }
    }
}

private enum class RoundOutcome(
    val score: Int
) {
    LOSE(0),
    DRAW(3),
    WIN(6)
}