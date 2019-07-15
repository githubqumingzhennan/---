package cn.itsource.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.itsource.dao.AccountDao;
import cn.itsource.dao.AudioDao;
import cn.itsource.dao.StudentMessagesDao;
import cn.itsource.domain.*;
import cn.itsource.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/employee")
public class StudentMessageController {
    @Autowired
    AccountDao accountDao;
    @Autowired
    AudioDao audioDao;
    @Autowired
    StudentMessagesDao studentMessagesDao;
    @RequestMapping("/search")
    @ResponseBody
    public Map search(Query query,HttpSession session){
       Account account=(Account) session.getAttribute("Account");
       if (account.getDept().equals("市场部")&&(query.getMinSalary()==null||query.getMinSalary().equals(""))){
           query.setMinSalary(5000);
       }
        //System.out.println(query.getSalary()+"faqqqqqqqqqqqqqqqqq");
        System.out.println(query+"%%%%%%%%%%%%");
        Map map = new HashMap();
        if(query.getPage()!=null&&query.getRows()!=null) {
            System.out.println("faqqqqqqq");
            query.setPage((query.getPage() - 1) * query.getRows());
        }
           // List<StudentMessages> search = studentMessagesDao.search(query);
            System.out.println("1111111111111111111111111111111111111");
           // System.out.println(search + "1111111111111111111111111111111111111" + search.size());


            map.put("total", studentMessagesDao.getSize(query));
            map.put("rows", studentMessagesDao.search(query));
            System.out.println(query);
             return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public AJAXResult save(StudentMessages studentMessages){
        System.out.println("faq");
        String msg="";
        if(studentMessages.getId()==null){
            try {
                studentMessagesDao.insertUser(studentMessages);

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
            try {
               studentMessagesDao.updateByPrimaryKey(studentMessages);
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
    @RequestMapping("/page")
   @ResponseBody
    public List<StudentMessages> getAll(){
        System.out.println("faq1");
        System.out.println(studentMessagesDao.queryAll().size()+"33");
        return studentMessagesDao.queryAll();
    }
    @RequestMapping("/AudioPage")
    @ResponseBody
    public List<Audio> getAllAudiao(){
        System.out.println("faq");
        return audioDao.queryAll();
    }
    @RequestMapping("/upload")
    @ResponseBody
    public AJAXResult upload(@RequestParam(value = "file", required = false)CommonsMultipartFile[]  files, HttpServletRequest  request){
        System.out.println("faq1");
        List<Audio> audios=new ArrayList<>();
        long  startTime=System.currentTimeMillis();
        //上传文件保存位置
        String pathString =  request.getSession().getServletContext().getRealPath("/upload/");
        //判断文件夹是否存在
        System.out.println(pathString+"jjjjjjjj");
        File file=new File(pathString);
        System.out.println(file.isDirectory());
        if(!file.isDirectory())
        {
            //创建文件夹
            file.mkdirs();
            System.out.println("kkkkkkkkkkk");

        }
        //批量上传
        for(int i = 0;i<files.length;i++)
        {

            if(!files[i].getOriginalFilename().isEmpty())
            {
                String path = pathString+files[i].getOriginalFilename();

                File newFile=new File(path);
                try
                {
                    //上传日志
                    files[i].transferTo(newFile);
                    Audio audio=new Audio();
                    audio.setName(files[i].getOriginalFilename());
                    audio.setCreateTime(new Date());
                    audio.setPath(path);
                   // audioDao.insertUser(audio);
                    audios.add(audio);
                } catch (IllegalStateException | IOException e)
                {
                    e.printStackTrace();
                }
            }else if(files.length == 1)
            {
                //返回提示选择文件
                AJAXResult ajaxResult = new AJAXResult();
                ajaxResult.setResultMsg("上传错误");
                return ajaxResult;
            }


        }
        audioDao.insertList(audios);
        long  endTime=System.currentTimeMillis();
        AJAXResult ajaxResult = new AJAXResult();
        ajaxResult.setResultMsg("上传成功");
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
            ExcelImportResult<StudentMessages> result = ExcelImportUtil.importExcelMore(file.getInputStream(), StudentMessages.class,
                    importParams);
            List<StudentMessages> userList = result.getList();
            for (StudentMessages User : userList) {
                // System.out.println(User);
                // log.info("从Excel导入数据到数据库的详细为 ：{}", JSONObject.toJSONString(User));
                System.out.println(User);
                //TODO 将导入的数据做保存数据库操作
            }
            studentMessagesDao.insertList(userList);
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
    public void exportExcel(Query query,HttpServletResponse response,HttpSession session){
       Map search = search(query,session);
       List list=(ArrayList)search.get("rows");
        ExportParams exportParams = new ExportParams();

        ExcelUtils.exportExcel(list, "", "导出sheet1", StudentMessages.class, "测试user.xls", response);


    }
    @RequestMapping("/test")

    public  String test(){
        System.out.println("faqqqqqqqq");
       return "account";




    }
    @RequestMapping("/toAccountPage")

    public  String toAccountPage(){
        return "account";




    }
    @RequestMapping("/delete")
    @ResponseBody
    public AJAXResult delete(Integer id){
        System.out.println(id);
        AJAXResult ajaxResult=null;
        try {
            if (id==3) {
                studentMessagesDao.deleteByPrimaryKey(id);
                ajaxResult = new AJAXResult();
                ajaxResult.setResultMsg("删除失败");
            }
        }catch (Exception e){

        }
    return ajaxResult;

    }
    @RequestMapping("/test01")

    public  String test01(){
        return "redirect:G:\\SSMstudentManage\\target\\employment\\upload\\123.txt";


    }
    @RequestMapping("/login")

    public void login(String username, String password, HttpSession session,HttpServletResponse httpServletResponse){

        Account accountByLogin = accountDao.getAccountByLogin(new Account(username, password));
        if (accountByLogin!=null) {
            session.setAttribute("Account",accountByLogin);
            try {
                httpServletResponse.sendRedirect("/index.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                httpServletResponse.sendRedirect("/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @RequestMapping("/AccountPage")
    @ResponseBody
    public  List<Account> AccountPage(){
        List<Account> accounts = accountDao.queryAll();

        return accounts;
    }
    @RequestMapping("/deleteAccount")
    @ResponseBody
    public AJAXResult deleteAccount(Integer id){
        System.out.println(id);
        AJAXResult ajaxResult=null;
        try {
            Account accountById = accountDao.getAccountById(id);
            System.out.println(("manage".equals(accountById.getJurisdiction())));
            if (!("manage".equals(accountById.getJurisdiction()))) {
                System.out.println("faqqqqqqqqqqqqqqqqqqqqq");
                accountDao.deleteByPrimaryKey(id);
                ajaxResult = new AJAXResult();
                ajaxResult.setResultMsg("导入失败");
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        return ajaxResult;

    }
    @RequestMapping("/saveAccount")
    @ResponseBody
    public AJAXResult saveAccount(Account account){
        System.out.println("faq");
        String msg="";
        if(account.getId()==null){
            try {
                if (accountDao.getAccountByNum(account)==null) {
                    String num1=account.getNum();
                    String dept1=account.getDept();

                    String num = num1.trim();
                    String dept = dept1.trim();

                    account.setNum(num);
                    account.setDept(dept);
                    System.out.println(account+"test");
                    accountDao.insertAccount(account);
                }else {
                    AJAXResult ajaxResult = new AJAXResult();
                    ajaxResult.setResultMsg("账号重复");
                    return ajaxResult;
                }
                AJAXResult ajaxResult = new AJAXResult();
                ajaxResult.setResultMsg("success");
                return ajaxResult;
            }catch (Exception e){

            }
            AJAXResult ajaxResult = new AJAXResult();
            ajaxResult.setMsg(msg);
            System.out.println(msg);
            return ajaxResult;
        }else {
            try {
                accountDao.updateByPrimaryKey(account);
            }catch (Exception e){
                AJAXResult ajaxResult = new AJAXResult();
                ajaxResult.setResultMsg("修改失败");
                return ajaxResult;
            }
            AJAXResult ajaxResult = new AJAXResult();
            ajaxResult.setResultMsg("success");
            return ajaxResult;
        }

    }
    @RequestMapping("/exitLogin")

    public void exitLogin(HttpSession session,HttpServletResponse httpServletResponse){
            session.removeAttribute("Account");
        try {
            httpServletResponse.sendRedirect("/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/updatePassword")
    @ResponseBody
    public AJAXResult updatePassword(Account account,HttpSession session,HttpServletResponse httpServletResponse) {
        System.out.println("faq");
        String msg = "";
        AJAXResult ajaxResult=new AJAXResult();
        try {
            System.out.println(account+"11111111");
            accountDao.updateByPrimaryKey(account);
            System.out.println("faqqqqqqqqqqqqqqqqqqqqqqqq");
            session.removeAttribute("Account");
            ajaxResult.setResultMsg("密码修改成功");
            return ajaxResult;
        }catch (Exception e){
            return new AJAXResult();
        }

    }

    @RequestMapping("/toStudentMessagePage")

    public void toStudentMessagePage(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.sendRedirect("/index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
