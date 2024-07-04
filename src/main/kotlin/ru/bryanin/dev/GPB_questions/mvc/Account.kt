package ru.bryanin.dev.GPB_questions.mvc

import java.util.UUID

data class Account(val id: UUID, val title: String, val clientId: UUID)
