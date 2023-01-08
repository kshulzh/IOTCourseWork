package we.iot.coursework.dataservices.filter

fun<T> `true`() : Filter<T> {
    return TrueFilter()
}
class TrueFilter<T> : Filter<T> {
    override fun check(value: T): Boolean {
        return true
    }
}