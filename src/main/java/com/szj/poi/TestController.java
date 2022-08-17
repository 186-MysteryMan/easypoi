package com.szj.poi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @author shenggongjie
 * @date 2021/3/6 0:15
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/reportOut")
    public void test(HttpServletResponse response){
        ArrayList<OrderOutExcelVO> out = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderOutExcelVO orderOutExcelVO = new OrderOutExcelVO();
            orderOutExcelVO.setGoodsName("测试");
            orderOutExcelVO.setOrderCode("daihao");
            out.add(orderOutExcelVO);
        }
        ReportParam rp = new ReportParam();
        rp.setList(out);
        rp.setClazz(OrderOutExcelVO.class);
        rp.setFileName("test");
        rp.setSheetName("sheet1");
        rp.setTitle("测试文件");
        rp.setResponse(response);
        ReportUtil.reportOut(rp);
    }
}
