package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }

    operator fun plus(interval: TimeInterval) : MyDate {
        return addTimeIntervals(interval, 1)
    }

    operator fun plus(repeatedInterval : RepeatedTimeInterval) : MyDate {
        return addTimeIntervals(repeatedInterval.interval, repeatedInterval.times)
    }
}

operator fun MyDate.rangeTo(that: MyDate) = DateRange(this, that)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(n: Int) : RepeatedTimeInterval {
        return RepeatedTimeInterval(this, n)
    }
}

class RepeatedTimeInterval(val interval: TimeInterval, val times: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(start, endInclusive)
}

class DateIterator(val start: MyDate, val endInclusive: MyDate) : Iterator<MyDate> {
    private var current = start

    override fun hasNext() = current <= endInclusive

    override fun next(): MyDate {
        val cur = current
        current = current.nextDay()
        return cur
    }
}