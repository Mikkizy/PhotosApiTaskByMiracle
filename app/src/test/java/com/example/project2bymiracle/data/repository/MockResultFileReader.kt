package com.example.project2bymiracle.data.repository

import java.io.InputStreamReader

class MockResultFileReader(path: String) {

    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}