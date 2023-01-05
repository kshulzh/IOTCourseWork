package we.iot.coursework.common.model

import java.time.Period

enum class Interval(
    val period: Period
) {
    DAY(Period.ofDays(1)),
    WEEK(Period.ofWeeks(1)),
    MONTH(Period.ofMonths(1))
}