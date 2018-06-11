package com.bingo.controller;


import com.bingo.util.BarCodeUtils;
import com.bingo.model.Param;
import com.bingo.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping
public class ParamController {

    @Autowired
    private ParamService paramService;

    @Value("${com.bingo.printbarcode.codeImagePath}")
    String FILE_PATH;

    @RequestMapping("")
    public ModelAndView index(){
        Param param = paramService.selectLast();
//        request.setAttribute("param", param);
//        return "index";
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("codeParam", param);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/generateCode")
    public void generateCode(Param param, HttpServletResponse response) throws IOException {

        String filename = BarCodeUtils.generateCode(param);
        String filePath = FILE_PATH + filename;

        paramService.insert(param);

        // 读到流中
        InputStream inStream = new FileInputStream(filePath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
