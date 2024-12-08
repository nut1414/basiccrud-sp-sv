package com.pannanap.demo.repository

import com.pannanap.demo.model.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message, String> {

}