package com.szj.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author shenggongjie
 * @date 2020/12/24 12:46
 */
@Slf4j
public class ReportUtil {
    /**
     * 导出excel
     *
     * @param reportParam 所需要的参数
     */
    public static void reportOut(ReportParam reportParam) {
        HttpServletResponse response = reportParam.getResponse();
        try (ServletOutputStream out = response.getOutputStream()) {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(reportParam.getTitle(), reportParam.getSheetName(), ExcelType.XSSF),
                    reportParam.getClazz(), reportParam.getList());
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=" + reportParam.getFileName() + ".xlsx");
            workbook.write(out);
        } catch (IOException e) {
            log.error("导出excel失败!", e);
        }
    }

    /**
     * 下载模板
     *
     * @param response     响应流
     * @param absolutePath 绝对路径 不加/
     * @param fileName     文件名
     */
    public static void down(HttpServletResponse response, String absolutePath, String fileName) {
        try (OutputStream os = response.getOutputStream();
             InputStream is = new ClassPathResource(absolutePath).getInputStream()) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            byte[] bytes = new byte[1024];
            int i;
            while ((i = is.read(bytes)) != -1) {
                os.write(bytes, 0, i);
            }
        } catch (Exception e) {
            log.error("文件下载失败!", e);
        }
    }


    /**
     * 读取文件
     *
     * @param file  文件
     * @param clazz 根据这个类的字段得到数据
     * @param <T>   返回类型
     * @return
     */
    public static <T> List<T> readFile(MultipartFile file, Class<T> clazz) {
        try (InputStream is = file.getInputStream()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            return ExcelImportUtil.importExcel(is,
                    clazz, params);
        } catch (Exception e) {
            log.error("文件读取失败", e);
        }
        return null;
    }
}
