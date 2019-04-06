package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = compareBy<MyDate>(
            { it.year },
            { it.month },
            { it.dayOfMonth }
    ).compare(this, other)
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) = addTimeIntervals(timeIntervals.timeInterval, timeIntervals.count)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class RepeatedTimeInterval(val timeInterval: TimeInterval, val count: Int)

operator fun TimeInterval.times(count: Int) = RepeatedTimeInterval(this, count)

class DateRange(override val start: MyDate, override val endInclusive: MyDate)
    : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            private var current: MyDate = start

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val next = current
                current = current.nextDay()

                return next
            }
        }
    }
}