package com.pannanap.demo.model

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
data class Message (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,
    val text: String = "",
)