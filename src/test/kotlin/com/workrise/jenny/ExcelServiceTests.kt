package com.workrise.jenny

import io.github.serpro69.kfaker.Faker
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootTest
class ExcelServiceTests(@Autowired private val excelService: ExcelService) {

    @Test
    fun testCreateExcelFileWithUserInput() {
        val faker = Faker()
        val userInput = faker.chuckNorris.fact()

        // Create an Excel file with the user input
        excelService.createExcelFileWithUserInput(userInput)

        // Read the generated Excel file
        val generatedFilePath = "src/main/resources/generated.xlsx"
        val inputStream = FileInputStream(generatedFilePath)
        val workbook = XSSFWorkbook(inputStream)
        val sheet = workbook.getSheetAt(0)
        val cell = sheet.getRow(0).getCell(0)
        val cellValue = cell.stringCellValue

        // Clean up the generated file
        try {
            Files.delete(Paths.get(generatedFilePath))
        } catch (e: IOException) {
            // Ignore the exception if the file is not found
        }

        // Verify the user input in the generated Excel file
        assertEquals(userInput, cellValue)
    }
}
