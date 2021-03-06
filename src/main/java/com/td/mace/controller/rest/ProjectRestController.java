package com.td.mace.controller.rest;

import com.td.mace.model.Project;
import com.td.mace.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@RestController
@RequestMapping("/project")
@SessionAttributes("projectleadslist")
public class ProjectRestController {

 @Autowired
 ProjectService projectService;
 /*
 @Autowired
 UserService userService;	
 
  * @Autowired WorkpackageService workpackageService;
  

 @Autowired
 MessageSource messageSource;

 @Autowired
 PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

 @Autowired
 AuthenticationTrustResolver authenticationTrustResolver;*/

 /**
  * This method will list all existing projects.
  */
 @RequestMapping(value = { "/projectlist" }, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
 public ResponseEntity<List<Project>> listProjects() {

  List<Project> projects = projectService.findAllProjects();
  //model.addAttribute("projects", projects);
  //model.addAttribute("loggedinuser", getPrincipal());
  ResponseEntity<List<Project>> entites = new ResponseEntity<List<Project>>(projects,HttpStatus.OK);
  
  return entites;
 }
/*
 *//**
  * This method will provide the medium to add a new project.
  *//*
 @RequestMapping(value = { "/newproject" }, method = RequestMethod.GET)
 public String newProject(ModelMap model) {
  Project project = new Project();
  model.addAttribute("project", project);
  model.addAttribute("edit", false);
  model.addAttribute("loggedinuser", getPrincipal());
  return "project";
 }

 *//**
  * This method will be called on form submission, handling POST request for
  * saving project in database. It also validates the project input
  *//*
 @RequestMapping(value = { "/newproject" }, method = RequestMethod.POST)
 public String saveProject(@Valid Project project, BindingResult result,
   ModelMap model) {

  if (result.hasErrors()) {
   return "project";
  }

  
   * Preferred way to achieve uniqueness of field [projectNumber] should
   * be implementing custom @Unique annotation and applying it on field
   * [projectNumber] of Model class [Project].
   * 
   * Below mentioned peace of code [if block] is to demonstrate that you
   * can fill custom errors outside the validation framework as well while
   * still using internationalized messages.
   
  if (!projectService.isProjectNumberUnique(project.getId(),
    project.getProjectNumber())) {
   FieldError projectNumberError = new FieldError("project",
     "projectNumber", messageSource.getMessage(
       "non.unique.projectNumber",
       new String[] { project.getProjectNumber() },
       Locale.getDefault()));
   result.addError(projectNumberError);
   return "project";
  }
  
  if (!projectService.isProjectNameUnique(project.getId(),
		    project.getProjectName())) {
		   FieldError projectNameError = new FieldError("project",
		     "projectName", messageSource.getMessage(
		       "non.unique.projectName",
		       new String[] { project.getProjectName() },
		       Locale.getDefault()));
		   result.addError(projectNameError);
		   return "project";
		  }

  projectService.saveProject(project);
  projectService.updateProject(project);

  // model.addAttribute("success", "Project " + project.getFirstName() +
  // " "+ project.getLastName() + " registered successfully");
  model.addAttribute("loggedinuser", getPrincipal());
  // return "success";
  return "redirect:/Project/projectslist";
 }

 *//**
  * This method will provide the medium to update an existing project.
  *//*
 @RequestMapping(value = { "/edit-project-{projectNumber}" }, method = RequestMethod.GET)
 public String editProject(@PathVariable String projectNumber, ModelMap model) {
  Project project = projectService.findByProjectNumber(projectNumber);
  model.addAttribute("project", project);
  model.addAttribute("edit", true);
  model.addAttribute("loggedinuser", getPrincipal());
  return "project";
 }

  *//**
     * This method will provide Users list to views
     *//*
    @ModelAttribute("projectleadslist")
    public List<User> initializeProjectLeads() {
        return userService.findAllUsersByType("Project Lead");
    }
 
 
 *//**
  * This method will be called on form submission, handling POST request for
  * updating project in database. It also validates the project input
  *//*
 @RequestMapping(value = { "/edit-project-{projectNumber}" }, method = RequestMethod.POST)
 public String updateProject(@Valid Project project, BindingResult result,
   ModelMap model, @PathVariable String projectNumber) {

  if (result.hasErrors()) {
   return "project";
  }

  
   * //Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in
   * UI which is a unique key to a Project.
   * if(!projectService.isProjectSSOUnique(project.getId(),
   * project.getProjectNumber())){ FieldError projectNumberError =new
   * FieldError("project","projectNumber",messageSource.getMessage(
   * "non.unique.projectNumber", new String[]{project.getProjectNumber()},
   * Locale.getDefault())); result.addError(projectNumberError); return
   * "project"; }
   

  projectService.updateProject(project);

  // model.addAttribute("success", "Project " + project.getFirstName() +
  // " "+ project.getLastName() + " updated successfully");
  model.addAttribute("loggedinuser", getPrincipal());
  return "redirect:/Project/projectslist";
 }

 *//**
  * This method will delete an project by it's SSOID value.
  *//*
 @RequestMapping(value = { "/delete-project-{projectNumber}" }, method = RequestMethod.GET)
 public String deleteProject(@PathVariable String projectNumber) {
  projectService.deleteProjectByProjectNumber(projectNumber);
  return "redirect:/Project/projectslist";
 }
 
 *//**
  * This method returns the principal[user-name] of logged-in user.
  *//*
 private String getPrincipal() {
  String userName = null;
  Object principal = SecurityContextHolder.getContext()
    .getAuthentication().getPrincipal();

  if (principal instanceof UserDetails) {
   userName = ((UserDetails) principal).getUsername();
  } else {
   userName = principal.toString();
  }
  return userName;
 }
*/
}
