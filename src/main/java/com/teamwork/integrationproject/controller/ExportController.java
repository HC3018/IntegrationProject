package com.teamwork.integrationproject.controller;

import com.teamwork.integrationproject.common.export.ExportTemplate;
import com.teamwork.integrationproject.dto.GenericTypeResponse;
import com.teamwork.integrationproject.dto.StudentDto;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.service.StudentService;
import com.teamwork.integrationproject.service.impl.StudentServiceImpl;
import com.teamwork.integrationproject.utils.excel.ExcelImportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2020/10/21 11:00
 */
@RestController
@RequestMapping("v1")
public class ExportController {

    @Autowired
    private StudentService studentService;

    //针对表数据进行导出
    @PostMapping("/exportStudentList")
    public void exportStudentList(HttpServletRequest request, HttpServletResponse response) {
        List<StudentDto> studentDtoList = studentService.selectStudentList();
        List<Map<String, Object>> dataList = new ArrayList<>();
        studentDtoList.forEach(student -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",student.getName());
            map.put("grade",student.getGrade());
            map.put("age",student.getAge());
            dataList.add(map);
        });
        ExportTemplate.exportStudent(response,"学生表导出",dataList);
    }

    /**
     * 导入student
     * @param file
     */
    @PostMapping("/importStudent")
    public GenericTypeResponse importStudent(@RequestParam("file") MultipartFile file) {
        // 从Excel第一行起到最后一行结束,
        try {
            List<Student> students = new ArrayList<>();
            List<List<String>>  importData = ExcelImportUtils.readExcel(file,0,2);
            importData.forEach(data -> {
                Student student = new Student();
                student.setName(data.get(0));
                student.setAge(Integer.parseInt(data.get(1)));
                student.setGrade(data.get(2));
                students.add(student);
            });
            GenericTypeResponse<Object> objectGenericTypeResponse = new GenericTypeResponse<>();
            studentService.addStudentList(students);
            return objectGenericTypeResponse;
        } catch (IOException e) {
            throw new RuntimeException("导入异常!",e);
        }
    }
}