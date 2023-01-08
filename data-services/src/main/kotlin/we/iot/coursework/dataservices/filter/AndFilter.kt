package we.iot.coursework.dataservices.filter

fun <T> and(vararg filters: Filter<T>): Filter<T> {
    return AndFilter(*filters, AndFilter())
}

class AndFilter<T>(
    vararg val filters: Filter<T>
) : Filter<T> {
    override fun check(value: T): Boolean {
        if (filters.isEmpty()) {
            return true
        }
        return filters.map { it.check(value) }
            .reduce(Boolean::and)
    }
}