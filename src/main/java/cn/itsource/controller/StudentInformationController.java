package cn.itsource.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.itsource.dao.StudentInformationDao;
import cn.itsource.domain.AJAXResult;
import cn.itsource.domain.Query;
import cn.itsource.domain.StudentInformation;
import cn.itsource.domain.StudentMessages;
import cn.itsource.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/info")
public class StudentInformationController {
    @Autowired
    StudentInformationDao studentInformationDao;
    @RequestMapping("/search")
    @ResponseBody
    public Map search(Query query,HttpSession session){
        Map map = new HashMap();
        if(query.getPage()!=null&&query.getRows()!=null) {
            query.setPage((query.getPage() - 1) * query.getRows());
        }
        map.put("total", studentInformationDao.getSize(query));
        map.put("rows", studentInformationDao.search(query));
      return   map;
    }
    @RequestMapping("toInfoPage")

    public String toInfoPage(){
        return "info";
    }
    @RequestMapping("/save")
    @ResponseBody
    public AJAXResult save(StudentInformation studentInformation){
        System.out.println("faq");
        String msg="";
        if(studentInformation.getId()==null){
            try {
                studentInformationDao.insertStudentInformation(studentInformation);

                AJAXResult ajaxResult = new AJAXResult();
                ajaxResult.setResultMsg("添加成功");
                return ajaxResult;
            }catch (Exception e){

            }
            AJAXResult ajaxResult = new AJAXResult();
            ajaxResult.setMsg(msg);
            System.out.println(msg);
            return ajaxResult;
        }else {
            System.out.println("just for test");
            try {
                studentInformationDao.updateByPrimaryKey(studentInformation);
            }catch (Exception e){
                AJAXResult ajaxResult = new AJAXResult();
                ajaxResult.setResultMsg("修改失败");
                return ajaxResult;
            }
            AJAXResult ajaxResult = new AJAXResult();
            ajaxResult.setResultMsg("修改成功");
            return ajaxResult;
        }

    }
    @RequestMapping("/delete")
    @ResponseBody
    public AJAXResult delete(Integer id){
        System.out.println(id);
        AJAXResult ajaxResult=null;
        try {
            studentInformationDao.deleteByPrimaryKey(id);
            ajaxResult=new AJAXResult();
            ajaxResult.setResultMsg("删除失败");
        }catch (Exception e){

        }
        return ajaxResult;

    }
    @RequestMapping("/deleteAll")
    @ResponseBody
    public AJAXResult deleteAll(){

        AJAXResult ajaxResult=null;
        try {

            studentInformationDao.deleteAll();
            ajaxResult = new AJAXResult();
            ajaxResult.setResultMsg("删除失败");

        }catch (Exception e){

        }
        return ajaxResult;

    }
    @RequestMapping("/importExcel")
    @ResponseBody
    public AJAXResult importExcel(@RequestParam("file") MultipartFile file){

        System.out.println("faq");
        ImportParams importParams = new ImportParams();

        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(0);
        // 需要验证
        importParams.setNeedVerfiy(false);

        try {
            ExcelImportResult<StudentInformation> result = ExcelImportUtil.importExcelMore(file.getInputStream(), StudentInformation.class,
                    importParams);
            List<StudentInformation> userList = result.getList();
            //System.out.println(userList+"-----------------------------------------");
            for (StudentInformation User : userList) {
                // System.out.println(User);
                // log.info("从Excel导入数据到数据库的详细为 ：{}", JSONObject.toJSONString(User));
               // System.out.println(User+"-----------------------------");
                //TODO 将导入的数据做保存数据库操作
            }
            studentInformationDao.insertList(userList);
            //log.info("从Excel导入数据一共 {} 行 ", userList.size());
            System.out.println("从Excel导入数据一共 {} 行 " + userList.size());
        } catch (IOException e) {
            // log.error("导入失败：{}", e.getMessage());
            AJAXResult ajaxResult = new AJAXResult();
            ajaxResult.setResultMsg("导入失败");
            System.out.println("导入失败：{}" + e.getMessage());
            return ajaxResult;
        } catch (Exception e) {
            AJAXResult ajaxResult = new AJAXResult();
            ajaxResult.setResultMsg("导入失败");
            System.out.println("导入失败：{}" + e.getMessage());
            return ajaxResult;

        }

        AJAXResult ajaxResult = new AJAXResult();
        ajaxResult.setResultMsg("导入成功");
        return ajaxResult;
    }
    @RequestMapping("/exportExcel")
    @ResponseBody
    public void exportExcel(Query query, HttpServletResponse response, HttpSession session){
        Map search = search(query,session);
        List list=(ArrayList)search.get("rows");
        ExportParams exportParams = new ExportParams();

        ExcelUtils.exportExcel(list, "", "导出sheet1", StudentInformation.class, "测试user1.xls", response);


    }

}
