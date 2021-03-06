package com.td.mace.converter;

import com.td.mace.model.Project;
import com.td.mace.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A converter class used in views to map id's to actual project objects.
 */
@Component
public class ProjectsListToProjectConverter implements
		Converter<String, Project> {

	static final Logger logger = LoggerFactory
			.getLogger(ProjectsListToProjectConverter.class);

	@Autowired
	ProjectService projectService;

	/**
	 * Gets Project by Id
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public Project convert(String element) {
//		Integer id = Integer.parseInt((String) element);
		Integer id = Integer.parseInt(element);
		Project projectsList = projectService.findById(id);
		logger.info("Projects List : {}", projectsList);
		return projectsList;
	}

}