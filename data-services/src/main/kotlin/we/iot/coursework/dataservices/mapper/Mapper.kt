package we.iot.coursework.dataservices.mapper

interface Mapper<I,O> {
    fun mapFrom(o:I) : O
    fun mapTo(o:O) : I
}