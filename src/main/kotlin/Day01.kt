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

    // region Part 1
    val maxElfInventoryCalorieSum = elfInventoryCalorieSums.maxOrNull()!!
    println(maxElfInventoryCalorieSum)
    check(maxElfInventoryCalorieSum == 66186)
    // endregion Part 1

    // region Part 2
    val top3InventoryCalorieSums = elfInventoryCalorieSums.sortedDescending().take(3).toList()
    val top3InventoryCalorieSumsTotal = top3InventoryCalorieSums.sum()
    println(top3InventoryCalorieSumsTotal)
    check(top3InventoryCalorieSumsTotal == 196804)
    // endregion Part 2
}