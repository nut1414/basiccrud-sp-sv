package com.pannanap.demo.controller

import com.pannanap.demo.model.Message
import com.pannanap.demo.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping

@CrossOrigin
@RestController
@RequestMapping("/messages")
class MessageController(
    @Autowired
    private val messageService: MessageService
) {
    @GetMapping
    fun listMessages() = ResponseEntity.ok(messageService.getAllMessage())

    @GetMapping("/{id}")
    fun getMessage(
        @PathVariable
        id: String
    ) = ResponseEntity.ok(messageService.findMessage(id))

    @PutMapping("/{id}")
    fun editMessage(
        @PathVariable
        id: String,
        @RequestBody
        text: String,
    ): ResponseEntity<Message> {
        val message = messageService.updateMessage(Message(id,text))
        if (message != null)
            return ResponseEntity.ok(message)
        return ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createMessage(
        @RequestBody
        text: String
    ) = ResponseEntity.ok(messageService.createMessage(text))


    @DeleteMapping("/{id}")
    fun deleteMessage(
        @PathVariable
        id: String
    ) = ResponseEntity.ok(messageService.deleteMessage(id))
}