package we.iot.coursework.dataservices.filter

object FilterStorage : HashMap<String,SensorDataFilter>() {
    var count = 1L
    fun add(filter:SensorDataFilter) : String {
        count+=1
        val name = count.toString()
        put(name,filter)
        return count.toString()
    }
}