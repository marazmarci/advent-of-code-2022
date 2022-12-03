/**
 * https://adventofcode.com/2022/day/2
 */
fun main() {
    fun Shape.playRound(opponentShape: Shape) = when {
        this == opponentShape -> RoundOutcome.DRAW
        this.isStrongerThan(opponentShape) -> RoundOutcome.WIN
        else -> RoundOutcome.LOSE
    }

    val inputLines = readInputFileLines(2)

    val rounds = inputLines.filter {
        it.isNotBlank()
    }.map {
        val (letter1, letter2) = it.split(" ")
        letter1.single() to letter2.single()
    }

    fun Sequence<Pair<RoundOutcome, Shape>>.calculateScore() = sumOf { (outcome, myChoice) ->
        outcome.score + myChoice.score
    }

    // region Part 1
    val part1Score = rounds.map { (opponentsChoice, myChoice) ->
        Shape.getByLetter(opponentsChoice) to Shape.getByLetter(myChoice)
    }.map { (opponentsChoice, myChoice) ->
        val outcome = myChoice.playRound(opponentsChoice)
        outcome to myChoice
    }.calculateScore()
    println(part1Score)
    // endregion Part 1

    // region Part 2
    fun getShapeChoiceToProduceOutcome(opponentShape: Shape, desiredOutcome: RoundOutcome) =
        Shape.values().first { myChoice ->
            myChoice.playRound(opponentShape) == desiredOutcome
        }
    val part2Score = rounds.map { (opponentsChoice, desiredOutcome) ->
        Shape.getByLetter(opponentsChoice) to RoundOutcome.getByLetter(desiredOutcome)
    }.map { (opponentsChoice, desiredOutcome) ->
        val myChoice = getShapeChoiceToProduceOutcome(opponentsChoice, desiredOutcome)
        desiredOutcome to myChoice
    }.calculateScore()
    println(part2Score)
    // endregion Part 2


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
    val letter: Char,
    val score: Int,
) {
    LOSE('X', 0),
    DRAW('Y', 3),
    WIN('Z', 6);

    companion object {
        fun getByLetter(letter: Char) = values().first {
            it.letter == letter
        }
    }
}