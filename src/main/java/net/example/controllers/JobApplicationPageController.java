package net.example.controllers;

import lombok.RequiredArgsConstructor;
import net.example.entities.JobApplication;
import net.example.enums.ApplicationStage;
import net.example.enums.ApplicationStatus;
import net.example.models.JobApplicationPatchRequest;
import net.example.models.JobApplicationRequest;
import net.example.services.JobApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class JobApplicationPageController {

    private final JobApplicationService service;

    @GetMapping
    public String list(@RequestParam(required = false) String company,
                       @RequestParam(required = false) ApplicationStatus status,
                       @RequestParam(required = false) ApplicationStage stage,
                       @RequestParam(required = false) String q,
                       @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {

        Page<JobApplication> page = service.findAll(company, status, stage, null, null, q, pageable);
        model.addAttribute("page", page);
        model.addAttribute("applications", page.getContent());
        model.addAttribute("company", company);
        model.addAttribute("status", status);
        model.addAttribute("stage", stage);
        model.addAttribute("q", q);
        model.addAttribute("statuses", ApplicationStatus.values());
        model.addAttribute("stages", ApplicationStage.values());
        model.addAttribute("form", emptyForm());

        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            model.addAttribute("sortProperty", order.getProperty());
            model.addAttribute("sortDirection", order.getDirection().name().toLowerCase());
        } else {
            model.addAttribute("sortProperty", "id");
            model.addAttribute("sortDirection", "desc");
        }

        return "applications/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("jobApplication", service.findById(id));
        return "applications/detail";
    }

    @PostMapping
    public String create(JobApplicationRequest form, RedirectAttributes redirectAttributes) {
        JobApplication created = service.create(form);
        redirectAttributes.addFlashAttribute("message", "Created application #" + created.getId());
        return "redirect:/applications";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("message", "Deleted application #" + id);
        return "redirect:/applications";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("jobApplication", service.findById(id));
        model.addAttribute("statuses", ApplicationStatus.values());
        model.addAttribute("stages", ApplicationStage.values());
        return "applications/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       JobApplicationPatchRequest form,
                       RedirectAttributes redirectAttributes) {

        service.patch(id, form);
        redirectAttributes.addFlashAttribute("message", "Updated application #" + id);
        return "redirect:/applications/" + id;
    }

    private JobApplicationRequest emptyForm() {
        return new JobApplicationRequest(
                null, null, null, null, null, null, null, null, null, null, null, null
        );
    }
}