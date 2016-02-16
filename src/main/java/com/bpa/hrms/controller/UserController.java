package com.bpa.hrms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bpa.hrms.entity.Role;
import com.bpa.hrms.entity.User;
import com.bpa.hrms.entity.UserRole;
import com.bpa.hrms.service.RoleService;
import com.bpa.hrms.service.UserRoleService;
import com.bpa.hrms.service.UserService;




@Controller
public class UserController {

	protected final Log logger = LogFactory.getLog(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	
	private HttpHeaders addAccessControllAllowOrigin() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		return headers;
	}
	
	// Get Record Service

	@RequestMapping(value = "/user/loginVerification", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> loginVerification(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		try{
			
			// Constructing User Search Object
					User searchUser = new User();
					logger.info("User Controller is working");
					String userEmail = request.getParameter("userEmail");
					//if (userEmail != null && userEmail.isEmpty() == false) {
						searchUser.setUserEmail(userEmail);
					//}
					logger.info("User Email is"+userEmail);
					String password = request.getParameter("password");
					//if (password != null && userEmail.isEmpty() == false) {
						searchUser.setPassword(password);
					//}
						
						logger.info("User Password  is"+password);
			
		User user = userService.getUserBySearchParameter(searchUser);
      
		
		
		if(user != null)
		{	
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole = userRoleService.searchbyUser(userRole);
			logger.info("user role object :" + userRole);

			Role role = new Role();
			role.setRoleId(userRole.getRole().getRoleId());
			logger.info("user roleid" + userRole.getRole().getRoleId());
			role = roleService.getRoleById(role.getRoleId());
			logger.info("loginVerification Executed.,");
			modelMap.put("success", true);
			modelMap.put("message", "Saved Successfully");
			return getModelMapUser(user, role);

		} else {
			logger.info("loginVerification Error .,");
			modelMap.put("success", false);
			modelMap.put("message", "Saved Successfully");
			return getModelMapError("Login Failed");
		}

	} 
		
	catch(Exception ex)
	{
		ex.printStackTrace();
		return getModelMapError("Login Failed");
	}


}
	@RequestMapping(value = "/user/userId", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> userId(HttpServletRequest request)
			throws ParseException {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		Date todayDate = new Date();

		String id_value = "";
		id_value = request.getParameter("userId");
		try {
			User user = new User();
			Role role = new Role();
			if ((StringUtils.isNotBlank(request.getParameter("userId")))
					|| (StringUtils.isNotEmpty(request.getParameter("userId")))) {
				id_value = request.getParameter("userId");
				user = userService.getUserById((Integer.parseInt(id_value)));

			} else {
				return getModelMapError("Login Failed");
			}

			return getModelMapUser(user, role);

		} catch (Exception e) {
			e.printStackTrace();
			String msg = "Sorry problem in saving data";
			modelMap.put("success", false);
			modelMap.put("message", msg);
			return modelMap;

		}
	}
	
	
	
	
	
	

	//json methods
    	private Map<String, Object> getModelMapUser(User user,Role role) {

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		final String createdOn = formatter.format(user.getCreatedOn());
		final String updatedOn = formatter.format(user.getUpdatedOn());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonBeanProcessor(User.class,
				new JsonBeanProcessor() {
					@SuppressWarnings("restriction")
					public JSONObject processBean(Object bean,
							JsonConfig jsonConfig) {
						if (!(bean instanceof User)) {
							return new JSONObject(true);
						}

						User user = (User) bean;
						
						return new JSONObject()
						.element("userId", user.getUserId())
						.element("userEmail", user.getUserEmail())
						.element("password", user.getPassword());
					}
				});		
						
		JSON json = JSONSerializer.toJSON(user, jsonConfig);
		
		JsonConfig jsonConfig1 = new JsonConfig();
		jsonConfig1.registerJsonBeanProcessor(Role.class,
				new JsonBeanProcessor() {
					public JSONObject processBean(Object bean,
							JsonConfig jsonConfig) {
						if (!(bean instanceof Role)) {
							return new JSONObject(true);
						}

						Role role = (Role) bean;

						return new JSONObject()
								.element("roleId", role.getRoleId())
								.element("roleName", role.getRoleName())
								.element("roleDescription",
										role.getRoleDescription())

						;
					}
				});
		JSON json1 = JSONSerializer.toJSON(role, jsonConfig1);
		
		
	modelMap.put("data", json);
	modelMap.put("data_Role", json1);
	modelMap.put("success", true);

	return modelMap;

}
	
	private Map<String, Object> getModelMapError(String msg) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("message", msg);
		modelMap.put("success", false);
		modelMap.put("data", "");

		return modelMap;
	}
	

	
	
}
	