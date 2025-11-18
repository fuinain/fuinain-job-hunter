package com.example.controller;

import com.example.domain.Resume;
import com.example.domain.response.ResultPaginantionDTO;
import com.example.domain.response.resume.ResCreateResumeDTO;
import com.example.domain.response.resume.ResFetchResumeDTO;
import com.example.domain.response.resume.ResUpdateResumeDTO;
import com.example.service.ResumeService;
import com.example.util.annotation.ApiMessage;
import com.example.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/resumes")
    @ApiMessage("Create a new resume" )
    public ResponseEntity<ResCreateResumeDTO> createResume(@Valid @RequestBody Resume resume) throws IdInvalidException {
        // check id
        boolean isIdExist = this.resumeService.checkResumeExistByUseAndJob(resume);
        if (!isIdExist) throw new IdInvalidException("User ID or Job ID is invalid");

        return ResponseEntity.status(HttpStatus.CREATED).body(this.resumeService.create(resume));
    }

    @PutMapping("/resumes")
    @ApiMessage("Update a resume")
    public ResponseEntity<ResUpdateResumeDTO> update(@RequestBody Resume resume) throws IdInvalidException {
        // check id
        Optional<Resume> reqResumeOptional = this.resumeService.fetchById(resume.getId());
        if (reqResumeOptional.isEmpty()) throw new IdInvalidException("Resume ID is invalid");

        Resume reqResume = reqResumeOptional.get();
        reqResume.setStatus(resume.getStatus());

        return ResponseEntity.ok().body(this.resumeService.update(reqResume));
    }

    @DeleteMapping("/resumes/{id}")
    @ApiMessage("Delete a resume by ID")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Resume> reqResumeOptional = this.resumeService.fetchById(id);
        if (reqResumeOptional.isEmpty()) throw new IdInvalidException("Resume ID is invalid");

        this.resumeService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/resumes/{id}")
    @ApiMessage("Get a resume by ID")
    public ResponseEntity<ResFetchResumeDTO> fetchById(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Resume> reqResumeOptional = this.resumeService.fetchById(id);
        if (reqResumeOptional.isEmpty()) throw new IdInvalidException("Resume ID is invalid");
        return ResponseEntity.ok().body(this.resumeService.getResume(reqResumeOptional.get()));
    }

    @GetMapping("resumes")
    @ApiMessage("Fetch all resumes")
    public ResponseEntity<ResultPaginantionDTO> fetchAll(
            @Filter Specification<Resume> spec,
            Pageable pageable
            ) {
        return ResponseEntity.ok().body(this.resumeService.fetchAllResume(spec, pageable));
    }
}
