package com.workrise.jenny

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JennyApplication

fun main(args: Array<String>) {
	runApplication<JennyApplication>(*args)
}
