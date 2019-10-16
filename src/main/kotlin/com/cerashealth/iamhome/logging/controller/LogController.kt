package com.cerashealth.iamhome.logging.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.cerashealth.iamhome.logging.model.Log
import com.cerashealth.iamhome.logging.util.Constants
import com.cerashealth.iamhome.logging.repository.LogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.domain.PageImpl
import org.springframework.web.context.request.WebRequest
import org.springframework.data.mongodb.core.query.isEqualTo
import java.util.regex.Pattern


@RestController
@RequestMapping("/api/v1/log")
class LogController {

    @Autowired
    private lateinit var logRepository: LogRepository

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @GetMapping()
    fun getLog(pageable: Pageable):Page<Log>{

        return logRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()))

    }

    @GetMapping("/search")
    fun searchLog(webRequest: WebRequest, pageable: Pageable):Page<Log>{

        val parametersToIgnore = Constants.PARAMETERS_TO_IGNORE
        val availableFieldsToFilter = webRequest.getParameterValues("availableFilters[]")
        var existsFilter = ! webRequest.getParameter("filter")!!.isNullOrEmpty()
        var filters = webRequest.getParameter("filter")!!.toLowerCase().split(" ")
                .mapIndexed { index, s -> Pattern.compile("$s", Pattern.CASE_INSENSITIVE) }
        val query = Query()
        if (existsFilter){
            val criterias = mutableListOf<Criteria>()
            for (field in availableFieldsToFilter!!) {
                if(! parametersToIgnore.contains(field)){
                    for(regex in filters){
                        criterias.add(criterias.size, Criteria.where(field).isEqualTo(regex))
                    }
                }
            }
            if (criterias.isNotEmpty()) {
                query.addCriteria(Criteria().orOperator(*(criterias.toTypedArray())))
            }
        }

        query.with(pageable)
        val list = mongoTemplate.find(query, Log::class.java)
        val count = mongoTemplate.count(query, Log::class.java)

        return PageImpl<Log>(list, pageable, count)

    }

}