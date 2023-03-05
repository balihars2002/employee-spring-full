package com.increff.employee.dto;

import com.increff.employee.controller.ui.SampleController;
import com.increff.employee.util.IOUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Service
public class SampleDto {

    public void get(String fileName, HttpServletResponse response)throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-disposition:", "attachment; filename=" + fileName);
        String fileClasspath = "/com/increff/employee/" + fileName;
        InputStream is = SampleController.class.getResourceAsStream(fileClasspath);
        try {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtil.closeQuietly(is);
        }
    }
}
