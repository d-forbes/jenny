package com.workrise.jenny

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class GenerateExcelRequest(
    val data: List<Map<String, Any>>
)

@RestController
@RequestMapping("/api/v1/jenny")
@Tag(name = "Tag")
@Suppress("unused")
class ExcelController(@Autowired private val excelService: ExcelService) {

    @PostMapping("/generate-excel", consumes = ["application/json"])
    fun generateExcel(@RequestBody request: GenerateExcelRequest): ResponseEntity<String> {
        return try {
            excelService.createExcelFileWithUserInput(request.data)
            ResponseEntity.status(HttpStatus.OK).body("Excel file generated successfully.")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while generating the Excel file.")
        }
    }
}
