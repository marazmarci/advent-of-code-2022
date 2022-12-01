/**
 * https://adventofcode.com/2022/day/1
 */
fun main() {
    val inputLines = readInputFileLines(1)

    val elfInventories = inputLines.split {
        it.isEmpty()
    }.map { elfInventoryStr ->
        elfInventoryStr.map { itemCalorieStr ->
            itemCalorieStr.toInt()
        }
    }

    val elfInventoryCalorieSums = elfInventories.map { elfInventory ->
        elfInventory.sum()
    }

    val maxElfInventoryCalorieSum = elfInventoryCalorieSums.maxOrNull()!!

    println(maxElfInventoryCalorieSum)
}