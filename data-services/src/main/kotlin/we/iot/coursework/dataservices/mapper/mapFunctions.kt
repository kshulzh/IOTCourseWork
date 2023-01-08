package we.iot.coursework.dataservices.mapper

fun mapToDouble(any: Any) : Double {
    return when(any::class) {
        Double::class -> any as Double
        String::class -> (any as String).toDouble()
        else -> any as Double
    }
}