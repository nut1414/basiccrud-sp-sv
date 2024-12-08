package com.pannanap.demo.service

import com.pannanap.demo.model.Message
import com.pannanap.demo.repository.MessageRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull
import org.springframework.data.domain.Sort;

@Service
class MessageService(private val messageRepository: MessageRepository) {
    fun createMessage(text: String): Message {

        return messageRepository.save(Message(
            text = text
        ))
    }

    fun findMessage(id: String): Message? {
        return messageRepository.findById(id).getOrNull()
    }

    fun getAllMessage(): List<Message> {
        return messageRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
    }

    fun deleteMessage(id: String) {
        return messageRepository.deleteById(id)
    }

    fun updateMessage(message: Message): Message? {
        val toUpdate = messageRepository.findById((message.id ?: ""))
        if (!toUpdate.isEmpty){
            val updated = toUpdate.get().copy(
                text = message.text
            )
            val saved: Message = messageRepository.save(updated)
            return saved
        }
        return null
    }
}