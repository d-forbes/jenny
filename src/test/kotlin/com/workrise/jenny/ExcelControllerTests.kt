package com.workrise.jenny

import io.github.serpro69.kfaker.Faker
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.io.ByteArrayInputStream

@SpringBootTest
@AutoConfigureMockMvc
class ExcelControllerTests(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun `generateExcel should return an Excel file with the user input`() {
        val faker = Faker()
        val userInput = faker.chuckNorris.fact()

        val result = mockMvc.post("/api/generate-excel") {
            contentType = MediaType.TEXT_PLAIN
            MockMvcResultMatchers.content().string("[\"$userInput\"]")
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_OCTET_STREAM) }
            header { string("Content-Disposition", "attachment; filename=generated.xlsx") }
        }.andReturn()

        val excelBytes = result.response.contentAsByteArray
        ByteArrayInputStream(excelBytes).use { inputStream ->
            val workbook = XSSFWorkbook(inputStream)
            val sheet = workbook.getSheetAt(0)
            val row = sheet.getRow(0)
            val cell = row.getCell(0)

            assertEquals(userInput, cell.stringCellValue)
        }
    }
}
