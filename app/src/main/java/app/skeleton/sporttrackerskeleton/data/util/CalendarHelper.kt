package app.skeleton.sporttrackerskeleton.data.util

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

object CalendarHelper {

    /**
     * Returns a list of dates for the given month, including leading and trailing nulls
     * to align the first day to its day of the week (assuming Monday as the first day).
     */
    fun getDaysInMonth(monthDate: LocalDate): List<LocalDate?> {
        val firstDayOfMonth = monthDate.with(TemporalAdjusters.firstDayOfMonth())
        val lastDayOfMonth = monthDate.with(TemporalAdjusters.lastDayOfMonth())
        
        val daysInMonth = mutableListOf<LocalDate?>()
        
        // Monday = 1, Sunday = 7
        // We want Monday offset to be 0
        val offset = firstDayOfMonth.dayOfWeek.value - 1
        
        repeat(offset) {
            daysInMonth.add(null)
        }
        
        var current = firstDayOfMonth
        while (!current.isAfter(lastDayOfMonth)) {
            daysInMonth.add(current)
            current = current.plusDays(1)
        }
        
        return daysInMonth
    }

    fun getNextMonth(current: LocalDate): LocalDate {
        return current.plusMonths(1)
    }

    fun getPreviousMonth(current: LocalDate): LocalDate {
        return current.minusMonths(1)
    }
}
