package com.workrise.jenny

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/jenny")
@Tag(name = "Tag")
@Suppress("unused")
class ExcelController(@Autowired private val excelService: ExcelService) {

    @PostMapping("/generate-excel")
    fun generateExcel(@RequestParam userInput: String): ResponseEntity<String> {
        return try {
            excelService.createExcelFileWithUserInput(userInput)
            ResponseEntity("Excel file generated successfully.", HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("An error occurred while generating the Excel file: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
