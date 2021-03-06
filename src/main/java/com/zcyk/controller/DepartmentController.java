package com.zcyk.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.zcyk.pojo.Department;
import com.zcyk.pojo.User;
import com.zcyk.pojo.UserDepartment;
import com.zcyk.service.CompanyService;
import com.zcyk.service.DepartmentFolderService;
import com.zcyk.service.DepartmentService;
import com.zcyk.service.UserService;
import com.zcyk.dto.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * 开发人员: xlyx
 * 创建日期: 2019/8/8 15:38
 */
@RestController
@RequestMapping("/Department")
public class DepartmentController {



    @Autowired
    DepartmentService departmentService;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentFolderService departmentFolderService;


    @Autowired
    ObjectMapper mapper;


    /**
     * 功能描述：查询企业下面的一级人员及部门
     * 开发人员： lyx
     * 创建时间： 2019/8/8 15:52
     * 参数： [company_id]
     * 返回值： java.util.List<com.zcyk.pojo.Department>
     * 异常：
     */
    @RequestMapping(value = "/selectDepartmentUser")
    public Map<String,Object> selectDepartmentUser(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,String company_id)throws Exception{
        Map<String,Object> map = new HashMap<>();
        List<Department> departments = departmentService.selectDepartment(company_id);
        map.put("department",departments);
        PageInfo<User> users = userService.selectAllUser(pageNum,pageSize,company_id);
        map.put("pageInfo",users);
        //将没有在部门的人员返回
        //departments.add(new Department().setDepartment_name("未加入部门").setUsers(departmentService.getNoDepartmentUsers()));
        return map;
    }

    /**
     * 功能描述：查询该部门的所有成员
     * 开发人员： lyx
     * 创建时间： 2019/8/8 21:32
     * 参数： [id, name]
     * 返回值： com.zcyk.dto.ResultData
     */
    @RequestMapping("/selectUserByDepartment")
    public PageInfo<User> selectUserByDepartment(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            String department_id)throws Exception{
        return departmentService.selectUserByDepartment(pageNum,pageSize,department_id);

    }

    /**
     * 功能描述：显示部门已经和部门同级的成员显示
     * 开发人员： lyx
     * 创建时间： 2019/8/27 15:44
     * 参数：department_id 一开始显示不传值，点击部门的时候显示部门id
     * 返回值：
     * 异常：
     */
    @RequestMapping(value = "/getDepartmentUser")
    @ResponseBody
    public Map<String, Object> getDepartmentUser(String department_id) throws Exception {
        return departmentService.getDepartmentUser(department_id);
    }

    /**
    * 功能描述：搜索部门人员
    * 开发人员：Wujiefeng
    * 创建时间：2019/9/3 17:36
    * 参数：[ * @param null]
    * 返回值：
   */
   @RequestMapping("/searchDepartmentUser")
   @ResponseBody
    public PageInfo<User>searchDepartmentUser(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              String department_id,
                                              String index)throws Exception{
        return departmentService.searchDepartmentUser(pageNum,pageSize,department_id,index);
    }
    /**
     * 功能描述：添加部门：在根目录下父id就是企业id
     * 开发人员： lyx
     * 创建时间： 2019/8/8 15:53
     * 参数： [department_name 企业信息, parent_id 父id]
     * 返回值： com.zcyk.dto.ResultData
     */
    @RequestMapping("/addDepartment")
    public ResultData addDepartment(Department department)throws Exception{
        return departmentService.addDepartment(department);
    }

    /**
     * 功能描述：移除部门成员
     * 开发人员： lyx
     * 创建时间： 2019/8/8 15:54
     * 参数： [user_id department_id]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @RequestMapping("/rmDepartmentUser")
    public ResultData rmDepartmentUser(UserDepartment userDepartment)throws Exception{
        return departmentService.rmDepartmentUser(userDepartment);
    }


    /**
     * 功能描述：设置部门管理员
     * 开发人员： lyx
     * 创建时间： 2019/8/8 15:54
     * 参数： [user_id, department_id, type]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @RequestMapping("/setDepartmentManager")
    public ResultData setDepartmentManager(UserDepartment userDepartment)throws Exception{
        return departmentService.setDepartmentManager(userDepartment);
    }

    /**
     * 功能描述：修改用户部门
     * 开发人员： lyx
     * 创建时间： 2019/8/8 15:55
     * 参数： [user_id, oldDepartment_id, newDepartment_id]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @RequestMapping("/UpdateUserDepartment")
    public ResultData UpdateUserDepartment(UserDepartment userDepartment, String newDepartment_id)throws Exception{
        return departmentService.UpdateUserDepartment(userDepartment,newDepartment_id);
    }

    /**
     * 功能描述：邀请个人进的部门
     * 开发人员： lyx
     * 创建时间： 2019/8/8 17:05
     * 参数： [user, newDepartment_id]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
   /* @RequestMapping("/inviteOneUser")
    public ResultData inviteOneUser(User user, String newDepartment_id){
        ResultData resultData = companyService.inviteOneUser(user);
        if("200".equals(resultData.getStatus())){
            return departmentService.inviteUserToDepartment((String)resultData.getData(), newDepartment_id);
        }
        return resultData;
    }*/

    /**
     * 功能描述：修改部门名称
     * 开发人员： lyx
     * 创建时间： 2019/8/8 21:32
     * 参数： [id, name]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @RequestMapping("/updateDepartmentName")
    public ResultData updateDepartmentName(Department department)throws Exception{
        return departmentService.updateDepartmentNameById(department);
    }


    /**
     * 功能描述：删除部门
     * 开发人员： lyx
     * 创建时间： 2019/8/8 21:32
     * 参数： [id, name]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @RequestMapping("/deleteDepartment")
    public ResultData deleteDepartment(String department_id)throws Exception{
        return departmentService.deleteDepartment(department_id);
    }



    /**
     * 功能描述：批量邀请
     * 开发人员： lyx
     * 创建时间： 2019/8/8 21:32
     * 参数： [id, name]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @RequestMapping("/inviteUserToDepartment")
    public ResultData inviteUserToDepartment(@RequestBody Map<String,Object> map)throws Exception{
        List<String> user_ids = new ArrayList<>();
        String department_id = "";
        try {
            user_ids = mapper.readValue(mapper.writeValueAsString(map.get("user_ids")),new TypeReference<ArrayList<String>>(){});
            department_id = map.get("department_id").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return departmentService.inviteUserToDepartment(user_ids, department_id);

    }

    /**
     * 功能描述：查询所有的未在部门成员
     * 开发人员： lyx
     * 创建时间： 2019/8/8 21:32
     * 参数： [id, name]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @RequestMapping("/selectNoDepartmentUser")
    public List<User> selectNoDepartmentUser(@RequestParam(defaultValue = "")String search)throws Exception{
        return departmentService.selectNoDepartmentUser(search);

    }


}