import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UtilsTest {

    @Test
    fun `Test List split`() {

        assertSequencesEqual(
            sequenceOf(listOf(), listOf("asd"), listOf("lol1", "lol2"), listOf()),
            listOf(";", "asd", ";", "lol1", "lol2", ";").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf(), listOf("asd"), listOf("lol1", "lol2")),
            listOf(";", "asd", ";", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf("asd"), listOf("lol1", "lol2")),
            listOf("asd", ";", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf(), listOf("asd", "lol1", "lol2")),
            listOf(";", "asd", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf("asd", "lol1", "lol2")),
            listOf("asd", "lol1", "lol2").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf("asd")),
            listOf("asd").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf(), listOf()),
            listOf(";").split { it == ";" }
        )

        assertSequencesEqual(
            sequenceOf(listOf()),
            listOf<String>().split { it == ";" }
        )

    }

    private fun <T> assertSequencesEqual(expected: Sequence<T>, actual: Sequence<T>) {
        assertEquals(
            expected.toList(),
            actual.toList()
        )
    }
}