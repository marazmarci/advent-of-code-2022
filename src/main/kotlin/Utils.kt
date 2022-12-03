fun readInputFile(day: Int): String {
    check(day in 0..99)
    val dayPadded = day.toString().padStart(2, '0')
    val fileName = "day${dayPadded}_input.txt"
    val resourceUri = ClassLoader.getSystemResource(fileName)
    return resourceUri.readText()
}

fun readInputFileLines(day: Int) = readInputFile(day).lineSequence()

fun <T> Sequence<T>.split(predicate: (T) -> Boolean): Sequence<List<T>> = sequence {
    var list = mutableListOf<T>()
    forEach {
        if (!predicate(it)) {
            list.add(it)
        } else {
            yield(list)
            list = mutableListOf()
        }
    }
    yield(list)
}