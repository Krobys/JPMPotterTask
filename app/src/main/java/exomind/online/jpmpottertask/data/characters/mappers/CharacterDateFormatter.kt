package exomind.online.jpmpottertask.data.characters.mappers

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDateFormatter @Inject constructor() {
    private val apiFormatter = DateTimeFormatter
        .ofPattern("dd-MM-uuuu", Locale.US)
        .withResolverStyle(ResolverStyle.STRICT)
    private val displayFormatter = DateTimeFormatter
        .ofPattern("dd MMM uuuu", Locale.US)

    fun format(rawDate: String?): String? {
        val s = rawDate?.trim().takeIf { !it.isNullOrEmpty() } ?: return null
        return try {
            LocalDate.parse(s, apiFormatter)
                .format(displayFormatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }
}