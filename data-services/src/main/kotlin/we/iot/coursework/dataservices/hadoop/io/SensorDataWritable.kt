package we.iot.coursework.dataservices.hadoop.io

import org.apache.hadoop.io.WritableComparable
import we.iot.coursework.dataservices.mapper.SensorDataFromCSVString
import we.iot.coursework.dataservices.model.SensorDataImpl
import java.io.DataInput
import java.io.DataOutput

class SensorDataWritable(
    var value: SensorDataImpl
) : WritableComparable<SensorDataWritable> {
    override fun write(out: DataOutput?) {
        out?.writeUTF(SensorDataFromCSVString.mapTo(value))
    }

    override fun readFields(`in`: DataInput?) {
        value = SensorDataFromCSVString.mapFrom(`in`?.readUTF() ?: return)
    }

    override fun compareTo(other: SensorDataWritable?): Int {
        return value.toString().compareTo(other.toString())
    }
}