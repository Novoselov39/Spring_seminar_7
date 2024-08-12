package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.page.ProjectPageDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectPageService {

    private final ProjectService projectService;


    public List<ProjectPageDto> findAll() {
        return projectService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<ProjectPageDto> findById(Long id) {
        return projectService.findById(id)
                .map(this::convert);
    }

    private ProjectPageDto convert(Project project) {


        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setId(String.valueOf(project.getId()));
        projectPageDto.setProjectName(String.valueOf(project.getName()));
        // 150 -> 2h30m

        return projectPageDto;
    }

}
