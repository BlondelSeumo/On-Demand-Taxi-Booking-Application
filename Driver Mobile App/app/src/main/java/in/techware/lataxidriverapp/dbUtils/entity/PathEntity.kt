package `in`.techware.lataxidriverapp.dbUtils.entity

import `in`.techware.lataxidriverapp.model.PathBean
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Jemsheer K D on 08 January, 2018.
 * Package `in`.techware.lataxidriver.database.entity
 * Project AFS_Bus_App
 */

@Entity(tableName = "path")
class PathEntity() {

    constructor(pathBean: PathBean) : this() {

        this.tripID = pathBean.tripID
        this.index = pathBean.index
        this.time = pathBean.time
        this.latitude = pathBean.latitude
        this.longitude = pathBean.longitude
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var index: Int = 0
    var tripID: String = ""
    var time: Long = 0
    var latitude: String = ""
    var longitude: String = ""


    fun getBean(): PathBean {
        var pathBean: PathBean = PathBean()

        pathBean.tripID = this.tripID
        pathBean.index = this.index
        pathBean.time = this.time
        pathBean.latitude = this.latitude
        pathBean.longitude = this.longitude

        return pathBean;
    }

}