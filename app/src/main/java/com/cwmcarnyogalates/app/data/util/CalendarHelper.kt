package com.cwmcarnyogalates.app.data.util

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

object CalendarHelper {

    fun getDaysInMonth(monthDate: LocalDate): List<LocalDate?> {
        val firstDay = monthDate.with(TemporalAdjusters.firstDayOfMonth())
        val lastDay = monthDate.with(TemporalAdjusters.lastDayOfMonth())
        val days = mutableListOf<LocalDate?>()
        val offset = firstDay.dayOfWeek.value - 1
        repeat(offset) { days.add(null) }
        var current = firstDay
        while (!current.isAfter(lastDay)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    fun getNextMonth(current: LocalDate): LocalDate = current.plusMonths(1)
    fun getPreviousMonth(current: LocalDate): LocalDate = current.minusMonths(1)
}
