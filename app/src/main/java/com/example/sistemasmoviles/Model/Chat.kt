package com.example.sistemasmoviles.Model

data class Chat(
    var id: String = "",
    var name: String = "",
    var users: List<String> = emptyList()
)