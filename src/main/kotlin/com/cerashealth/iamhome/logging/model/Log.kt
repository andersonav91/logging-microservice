package com.cerashealth.iamhome.logging.model

import java.io.Serializable
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "logs")
class Log : Serializable {

    @Id
    @NotNull
    var logId: String? = null

    @NotNull
    var created: String? = null

    @NotNull
    var type: String? = null

    @NotNull
    var category: String? = null

    @NotNull
    var data: Map<String,String>? = null

    @NotNull
    var sourceMicroservice: String? = null

    var performer: String? = null

    var action: String? = null

    var status: String? = null

    var message: String? = null

    companion object {
      private const val serialVersionUID = -7788619177798333712L
    }

}
