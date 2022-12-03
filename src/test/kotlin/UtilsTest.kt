import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UtilsTest {

    @Test
    fun `Test List split`() {

        assertSequencesEqual(
            sequenceOf(listOf(), listOf("asd"), listOf("lol1", "lol2"), listOf()),
            sequenceOf(";", "asd", ";", "lol1", "lol2", ";").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf(), listOf("asd"), listOf("lol1", "lol2")),
            sequenceOf(";", "asd", ";", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf("asd"), listOf("lol1", "lol2")),
            sequenceOf("asd", ";", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf(), listOf("asd", "lol1", "lol2")),
            sequenceOf(";", "asd", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf("asd", "lol1", "lol2")),
            sequenceOf("asd", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf("asd")),
            sequenceOf("asd").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf(), listOf()),
            sequenceOf(";").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf()),
            sequenceOf<String>().split { it == ";" }
        )

    }

    private fun <T> assertSequencesEqual(expected: Sequence<T>, actual: Sequence<T>) {
        assertEquals(
            expected.toList(),
            actual.toList()
        )
    }
}