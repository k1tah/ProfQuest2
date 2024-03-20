package com.example.data.api.body

import kotlinx.serialization.Serializable

@Serializable
class FileRequestBody(val file: ByteArray)