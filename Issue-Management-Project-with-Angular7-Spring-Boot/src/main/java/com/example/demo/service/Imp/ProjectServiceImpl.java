package com.example.demo.service.Imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.digester.ArrayStack;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ProjectDto;
import com.example.demo.Dto.UserDto;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProjectService;
import com.example.demo.util.TPage;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository,
			ModelMapper modelMapper) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ProjectDto save(ProjectDto projectDto) {
		Project projectChecked = projectRepository.getByProjectCode(projectDto.getProjectCode());
		if (projectChecked != null) {
			throw new IllegalArgumentException("Project code already exist");
		}
		Project p = modelMapper.map(projectDto, Project.class);
		User user=userRepository.getOne(projectDto.getManagerId());
		p.setManager(user);
		p = projectRepository.save(p);
		projectDto.setId(p.getId());
		return projectDto;
	}

	public List<ProjectDto> getAll(){
		List<Project> projectList = projectRepository.findAll();
		ProjectDto[] projectDtoArrays=modelMapper.map(projectList, ProjectDto[].class);
		return Arrays.asList(projectDtoArrays);
	}
	
	@Override
	public ProjectDto getById(Long id) {

		Project p = projectRepository.getOne(id);
//		if (p==null) {
//			throw new EntityNotFoundException("Project id does not exist");
//		}
		ProjectDto pDto = modelMapper.map(p, ProjectDto.class);
		return pDto;
	}

	@Override
	public TPage<ProjectDto> getAllPageable(Pageable pageable) {
		Page<Project> data=projectRepository.findAll(pageable);
        TPage<ProjectDto> response = new TPage<ProjectDto>();
		ProjectDto[] projectDtoArrays=modelMapper.map(data.getContent(), ProjectDto[].class);
		response.setStat(data, Arrays.asList(projectDtoArrays));
		return response;
	}

	
	
	@Override
	public ProjectDto getByProjectCode(String projectCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getByProjectCodeContains(String projectCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Project project) {
		return null;
	}
	@Override
	public Boolean delete(Long id) {
		Project projectDb=projectRepository.getOne(id);
		if(projectDb==null) {
			throw new IllegalArgumentException("Project does not exist id : "+id);
		}
		projectRepository.deleteById(id);
		return true;
	}
	@Override
	public ProjectDto update(Long id, ProjectDto project) {
		Project projectDb=projectRepository.getOne(id);
		if(projectDb==null) {
			throw new IllegalArgumentException("Project does not exist id : "+id);
		}

//		Project projectChecked = projectRepository.getByProjectCode(project.getProjectCode());
//		if (projectChecked != null && projectChecked.getId() != projectDb.getId()) {
//			throw new IllegalArgumentException("Project code already exist");
//		}
		
		Project projectChecked = projectRepository.getByProjectCodeAndIdNot(project.getProjectCode(), id);
		if(projectChecked != null) {
			throw new IllegalArgumentException("Project code already exist");
		}
		
		projectDb.setProjectCode(project.getProjectCode());
		projectDb.setProjectName(project.getProjectName());
		projectDb = projectRepository.save(projectDb);
		project= modelMapper.map(projectDb, ProjectDto.class);
		return project;
	}

}
