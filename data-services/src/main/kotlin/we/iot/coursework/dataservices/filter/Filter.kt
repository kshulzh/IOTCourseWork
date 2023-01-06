package we.iot.coursework.dataservices.filter

interface Filter<T> {
    fun check(value: T): Boolean
}