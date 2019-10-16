package com.cerashealth.iamhome.logging.repository

import com.cerashealth.iamhome.logging.model.Log
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LogRepository : MongoRepository<Log, String>{

}
