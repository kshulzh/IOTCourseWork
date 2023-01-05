package we.iot.coursework.dataservices.mapper

fun mapToDouble(any: Any) : Double {
    return when(any.javaClass) {
        Double.javaClass -> any as Double
        String.javaClass -> (any as String).toDouble()
        else -> any as Double
    }
}