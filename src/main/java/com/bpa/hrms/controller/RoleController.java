package com.bpa.hrms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bpa.hrms.entity.Role;
import com.bpa.hrms.entity.User;
import com.bpa.hrms.service.RoleService;




@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	protected final Log logger = LogFactory.getLog(RoleController.class);
	
	
	// List Service
	@RequestMapping(value = "/role/viewRoleList", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> viewRoleList(HttpServletRequest request) {

		try {

			// Constructing User Search Object
			Role role = new Role();

			String searchRoleName = request.getParameter("searchRoleName");
			if (searchRoleName != null && searchRoleName.isEmpty() == false) {
				role.setRoleName(searchRoleName);
			}

			int totalResults = roleService.getRoleFilterCount(role);
			List<Role> list = roleService.getAllRoleList(role);

			logger.info("Returned list size" + list.size());

			return getModelMapRoleList(list, totalResults);
		} catch (Exception e) {
			return getModelMapError("Error trying to List. " + e.getMessage());
		}
	}

	
	
	
	
	// Save Service
		@RequestMapping(value = "/role/saveRole", method = RequestMethod.POST)
		public @ResponseBody
		Map<String, Object> saveRole(HttpServletRequest request) {
			logger.info("Insert Method Strarted.,");
			Map<String, Object> modelMap = new HashMap<String, Object>(2);
			try {
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date todayDate = (Date) formatter.parse(formatter
						.format(currentDate.getTime()));

				Role role = new Role();
			/*	String id_value = "";

				if ((StringUtils.isNotBlank(request.getParameter("roleId")))
						|| (StringUtils.isNotEmpty(request.getParameter("roleId")))) {
					id_value = request.getParameter("roleId");
					
					role = roleService.getRoleById(Integer.parseInt(id_value));
					// role.setUpdatedBy(1);; // need to change after getting user
					// from session
					role.setUpdatedOn(todayDate);
					role.setCreatedOn(todayDate);

				} else {
					// role.setCreatedBy(1); // need to change after getting user
					// from session
					role.setUpdatedOn(todayDate);
					role.setCreatedOn(todayDate);

				}*/

				String roleName = request.getParameter("roleName");
				role.setRoleName(roleName);

				String roleDescription = request.getParameter("roleDescription");
				role.setRoleDescription(roleDescription);

				String isActive = request.getParameter("isActive");
				role.setIsActive('Y');

				
				role.setCreatedBy(1);
				role.setUpdatedBy(1);
				role.setUpdatedOn(todayDate);
				role.setCreatedOn(todayDate);

				roleService.saveRole(role);
				logger.info("Insert Method Executed.,");
				modelMap.put("success", true);
				modelMap.put("message", "Saved Successfully");
				return modelMap;
				
			} catch (Exception ex) {
				ex.printStackTrace();
				String msg = "Sorry problem in saving data";
				modelMap.put("success", false);
				modelMap.put("message", msg);
				return modelMap;
			}
		}
		
		
		
		
		
		// Pagination Service

		@RequestMapping(value = "/role/viewRoleListPagination", method = RequestMethod.GET)
		public @ResponseBody
		Map<String, Object> viewPaginationService(HttpServletRequest request) {
			int start = 0;
			int limit = 10;

			start = Integer.parseInt(request.getParameter("iDisplayStart"));
			limit = Integer.parseInt(request.getParameter("iDisplayLength"));
			try {
				Role role = new Role();

				User user = new User();
				/*String id_value = "";
				if ((StringUtils.isNotBlank(request.getParameter("userId")))
						|| (StringUtils.isNotEmpty(request.getParameter("userId")))) {
					id_value = request.getParameter("userId");
					// id_value = request.getParameter("projectId");

					user = userService.getUserById((Integer.parseInt(id_value)));
					project.setOrganization(user.getOrganization());
				}*/

				if ((StringUtils.isNotBlank(request.getParameter("roleName")))
						|| (StringUtils.isNotEmpty(request
								.getParameter("roleName")))) {
					role.setRoleName(request.getParameter("roleName"));
					logger.info("Search roleName JData table Project Name is :"
							+ request.getParameter("roleName"));
				} else {
					role.setRoleName(null);
				}

				if ((StringUtils.isNotBlank(request.getParameter("roleDescription")))
						|| (StringUtils.isNotEmpty(request
								.getParameter("roleDescription")))) {
					role.setRoleDescription(request.getParameter("roleDescription"));
					logger.info("Search project JData table project Path is :"
							+ request.getParameter("roleDescription"));
				} else {
					role.setRoleDescription(null);
				}

				if ((StringUtils.isNotBlank(request.getParameter("createdOn")))
						|| (StringUtils.isNotEmpty(request
								.getParameter("createdOn")))) {

					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					Date createdOnDate = (Date) formatter.parse(request
							.getParameter("createdOn"));

					logger.info("Search role JData table Created Date is :"
							+ createdOnDate);
					role.setCreatedOn(createdOnDate);
					logger.info("Search role JData table created On is :"
							+ request.getParameter("createdOn"));
					
					
					
					
					
					
				} else {
					role.setCreatedOn(null);
				}

				int totalResults = roleService.getRoleFilterCount(role);
				List<Role> list = roleService.getPaginationList(role,
						start, limit);
				return getModelMapRoleListPagination(list, totalResults);
			} catch (Exception e) {
				e.printStackTrace();
				return getModelMapError("Error trying to List. " + e.getMessage());
			}
		}
		
// to get role info for edit		
		@RequestMapping(value = "/role/getRoleInfo", method = RequestMethod.POST)
		public @ResponseBody
		Map<String, Object> userId(HttpServletRequest request)
				throws ParseException {
			Map<String, Object> modelMap = new HashMap<String, Object>(2);
			
			String roleId_Str = request.getParameter("roleId");
			Role role = new Role();
			try {
				if (roleId_Str != null) {
					role = roleService.getRoleById(Integer
							.parseInt(roleId_Str));
					
				}
				else
				{
					return getModelMapError("Failed to Load Data");
				}
				
				return getModelMapRoleInfo(role);

			} catch (Exception ex) {
				ex.printStackTrace();
				String msg = "Sorry problem in loading data";
				modelMap.put("success", false);
				modelMap.put("message", msg);
				return modelMap;
			}

		
		}
		
		
		// Delete Service
		@RequestMapping(value = "/role/deleteRole", method = RequestMethod.GET)
		public @ResponseBody
		Map<String, Object> deleteRole(HttpServletRequest request) {

			logger.info("Delete Method Strarted.,");
			Map<String, Object> modelMap = new HashMap<String, Object>(2);

			int roleId = Integer.parseInt(request.getParameter("roleId"));
			try {
				Role role = roleService.getRoleById(roleId);
				roleService.removeRole(role);
				logger.info("Delete Method Completed.,");
				modelMap.put("success", true);
				modelMap.put("message", "Deleted Successfully");
				return modelMap;

			} catch (Exception ex) {
				modelMap.put("success", false);
				modelMap.put("message", "Error in deletion");
				return modelMap;
			}
		}	
		
		// JSON Construction details by role
		private Map<String, Object> getModelMapDetailsByRoleList(List<User> list,
				int totalResults) {

			Map<String, Object> modelMap = new HashMap<String, Object>(3);
			modelMap.put("total", totalResults);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonBeanProcessor(User.class,
					new JsonBeanProcessor() {
						public JSONObject processBean(Object bean,
								JsonConfig jsonConfig) {
							if (!(bean instanceof User)) {
								return new JSONObject(true);
							}
							User user = (User) bean;

							return new JSONObject().element("userId",
									user.getUserId()).element("userEmail",
									user.getUserEmail());

						}
					});

			JSON json = JSONSerializer.toJSON(list, jsonConfig);

			/*---*/
			modelMap.put("data", json);
			modelMap.put("success", true);

			return modelMap;
		}

		// JSon Construction
		private Map<String, Object> getModelMapRoleList(List<Role> list,
				int totalResults) {

			Map<String, Object> modelMap = new HashMap<String, Object>(3);
			modelMap.put("total", totalResults);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonBeanProcessor(Role.class,
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
									.element("isActive", role.getIsActive())
									.element("createdBy", role.getCreatedBy())
									.element("createdOn", role.getCreatedOn())
									.element("updatedBy", role.getUpdatedBy())
									.element("updatedOn", role.getUpdatedOn());

						}
					});

			JSON json = JSONSerializer.toJSON(list, jsonConfig);

			/*---*/
			modelMap.put("data", json);
			modelMap.put("success", true);

			return modelMap;
		}

		/*
		 * Common json methods
		 */

		private ModelAndView getModelMap() {

			Map<String, Object> modelMap = new HashMap<String, Object>(1);
			modelMap.put("success", true);
			return new ModelAndView("jsonView", modelMap);
		}

		private Map<String, Object> getModelMapError(String msg) {

			Map<String, Object> modelMap = new HashMap<String, Object>(2);
			modelMap.put("message", msg);
			modelMap.put("success", false);
			modelMap.put("data", "");

			return modelMap;
		}
		
		
		
		
		private Map<String, Object> getModelMapRoleListPagination(
				List<Role> list, int totalResults) {

			Map<String, Object> modelMap = new HashMap<String, Object>(4);
			modelMap.put("recordsTotal", totalResults);
			modelMap.put("recordsFiltered", totalResults);

			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonBeanProcessor(Role.class,
					new JsonBeanProcessor() {
						public JSONObject processBean(Object bean,
								JsonConfig jsonConfig) {
							if (!(bean instanceof Role)) {
								return new JSONObject(true);
							}
							Role role = (Role) bean;

							SimpleDateFormat importDateFormat = new SimpleDateFormat(
									"MM/dd/yyyy");

							String createdOnString = "";
							if (role.getCreatedOn() != null) {
								createdOnString = importDateFormat.format(role
										.getCreatedOn());
							}
							String updatedOnString = "";
							if (role.getUpdatedOn() != null) {
								updatedOnString = importDateFormat.format(role
										.getUpdatedOn());
							}


							return new JSONObject()
									.element("roleId", role.getRoleId())
									.element("roleName",
											role.getRoleName())
										.element("createdOn", createdOnString)
										.element("updatedOn", updatedOnString)

							;
						}
					});

			JSON json = JSONSerializer.toJSON(list, jsonConfig);

			/*---*/
			modelMap.put("data", json);
			modelMap.put("success", true);
			return modelMap;
						
			}	
		
		
		
		// JSon Construction
				private Map<String, Object> getModelMapRoleInfo(Role role) {

					Map<String, Object> modelMap = new HashMap<String, Object>(3);
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonBeanProcessor(Role.class,
							new JsonBeanProcessor() {
								public JSONObject processBean(Object bean,
										JsonConfig jsonConfig) {
									if (!(bean instanceof Role)) {
										return new JSONObject(true);
									}
									Role role = (Role) bean;
									SimpleDateFormat importDateFormat = new SimpleDateFormat(
											"MM/dd/yyyy");

									String startDateString = "";
									if (role.getRoleName() != null) {
										startDateString = importDateFormat.format(role
												.getRoleName());

									}
									String endDateString = "";
									if (role.getRoleDescription() != null) {
										endDateString = importDateFormat.format(role
												.getRoleDescription());
									}
									
									String createdOnString = "";
									if (role.getCreatedOn() != null) {
										createdOnString = importDateFormat.format(role
												.getCreatedOn());
									}
									String updatedOnString = "";
									if (role.getUpdatedOn() != null) {
										updatedOnString = importDateFormat.format(role
												.getUpdatedOn());
									}
									
									return new JSONObject()
									.element("roleId", role.getRoleId())
									.element("roleName",
											role.getRoleName())
									.element("roleDescription", role.getRoleDescription())
									.element("createdOn", createdOnString)
									.element("updatedOn", updatedOnString)
									.element("createdBy", role.getCreatedBy())
									.element("createdOn", createdOnString)
									.element("updatedBy", role.getUpdatedBy())
									.element("updatedOn", updatedOnString)

							;

								}
							});

					JSON json = JSONSerializer.toJSON(role, jsonConfig);

					/*---*/
					modelMap.put("data", json);
					modelMap.put("success", true);

					return modelMap;
				}
				
		
		
		
		
		
		
	}
