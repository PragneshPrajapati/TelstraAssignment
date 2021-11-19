package com.infosys.telstraassignment.util

import java.io.File

class MockResponseFileReader(path: String) {
    val content: String

    init {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        content = String(file.readBytes())
    }
}
