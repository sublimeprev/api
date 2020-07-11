package com.sublimeprev.api.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.CancelNotificationReqDTO;
import com.sublimeprev.api.dto.req.SchedulingPageReq;
import com.sublimeprev.api.dto.req.SchedulingReqDTO;
import com.sublimeprev.api.dto.res.SchedulingByDateResDTO;
import com.sublimeprev.api.dto.res.SchedulingResDTO;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.service.SchedulingService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/schedulings", produces = MediaType.APPLICATION_JSON_VALUE)
public class SchedulingController {

    private final SchedulingService service;

    @GetMapping
    public PageRes<SchedulingResDTO> index(SchedulingPageReq query) {
        Page<Scheduling> page = this.service.findAll(query);
        return new PageRes<SchedulingResDTO>(
                page.getContent().stream().map(SchedulingResDTO::of).collect(Collectors.toList()),
                page.getTotalElements(), page.getTotalPages());
    }

    @PostMapping
    public SchedulingResDTO store(@Valid @RequestBody SchedulingReqDTO dto) {
        return SchedulingResDTO.of(this.service.create(dto.getUserId(), dto.getDate(), dto.getTime(), dto.getStatus(), dto.getIdTypeExercise()));
    }

    @GetMapping("/{id}")
    public SchedulingResDTO show(@PathVariable("id") Long id) {
        return SchedulingResDTO.of(this.service.findById(id));
    }

    @PutMapping("/{id}")
    public SchedulingResDTO update(@PathVariable("id") Long id, @Valid @RequestBody SchedulingReqDTO dto) {
        return SchedulingResDTO.of(this.service.save(dto.toEntity(this.service.findById(id))));
    }

    @DeleteMapping("/{id}")
    public void logicalExclusion(@PathVariable("id") Long id) {
        this.service.logicalExclusion(id);
    }

    @GetMapping("/many")
    public List<SchedulingResDTO> showMany(@RequestParam Long[] ids) {
        return this.service.findByIds(ids).stream().map(SchedulingResDTO::of).collect(Collectors.toList());
    }
    
    @GetMapping("/training-dashboard/{date}")
    public List<SchedulingByDateResDTO> getTrainingByDate(@PathVariable ("date") Date date){
    	return SchedulingByDateResDTO.getListByDate(service.getSchedulesByDate(date));
    }

    @PutMapping("/restore/{id}")
    public void restoreDeleted(@PathVariable("id") Long id) {
        this.service.restoreDeleted(id);
    }

    @DeleteMapping("/destroy/{id}")
    public void permanentDestroy(@PathVariable("id") Long id) {
        this.service.permanentDestroy(id);
    }

    @PutMapping("/cancel")
    public List<SchedulingResDTO> cancelMany(@RequestParam Long[] ids, @Valid @RequestBody CancelNotificationReqDTO dto) {
        return this.service.cancelMany(ids, dto.getContent(), dto.getTitle(), dto.isSendNotify()).stream().map(SchedulingResDTO::of).collect(Collectors.toList());
    }
}
