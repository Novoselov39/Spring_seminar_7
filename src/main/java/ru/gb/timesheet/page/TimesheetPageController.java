package ru.gb.timesheet.page;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.timesheet.rest.TimesheetController;
import ru.gb.timesheet.service.TimesheetPageService;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/home/timesheets")
@RequiredArgsConstructor
//@PreAuthorize("hasRole(\"admin\")") // SPel - spring expression language
//@Secured({"admin"})
public class TimesheetPageController {

  private final TimesheetPageService service;

  // Вместо описания, можно использовать @RequiredArgsConstructor
//  public TimesheetPageController(TimesheetService service) {
//    this.service = service;
//  }

  @GetMapping
  // @Secured({"admin", "user"})
  public String getAllTimesheets(Model model) {
    List<TimesheetPageDto> timesheets = service.findAll();
    model.addAttribute("timesheets", timesheets);
    return "timesheets-page.html";
  }

  // GET /home/timesheets/{id}
  @GetMapping("/{id}")
  public String getTimesheetPage(@PathVariable Long id, Model model) {
    Optional<TimesheetPageDto> timesheetOpt = service.findById(id);
    if (timesheetOpt.isEmpty()) {
      throw new NoSuchElementException();
    }

    model.addAttribute("timesheet", timesheetOpt.get());
    return "timesheet-page.html";
  }

}
