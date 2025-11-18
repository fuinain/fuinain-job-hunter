package com.example.service;

import com.example.domain.Company;
import com.example.domain.Job;
import com.example.domain.Skill;
import com.example.domain.response.ResultPaginantionDTO;
import com.example.domain.response.job.ResCreateJobDTO;
import com.example.domain.response.job.ResUpdateJobDTO;
import com.example.repository.CompanyRepository;
import com.example.repository.JobRepository;
import com.example.repository.SkillRepository;
import com.example.util.error.IdInvalidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository, CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
    }

    public ResCreateJobDTO create(Job j) {
        if (j.getSkills() != null) {
            List<Long> reqSkills = j.getSkills()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            j.setSkills(dbSkills);
        }

        if (j.getCompany() != null) {
            Optional<Company> companyOptional = this.companyRepository.findById(j.getCompany().getId());
            if (companyOptional.isPresent()) j.setCompany(companyOptional.get());
        }

        Job currentJob = this.jobRepository.save(j);

        ResCreateJobDTO dto = new ResCreateJobDTO();
        dto.setId(currentJob.getId());
        dto.setName(currentJob.getName());
        dto.setSalary(currentJob.getSalary());
        dto.setQuantity(currentJob.getQuantity());
        dto.setLocation(currentJob.getLocation());
        dto.setLevel(currentJob.getLevel());
        dto.setStartDate(currentJob.getStartDate());
        dto.setEndDate(currentJob.getEndDate());
        dto.setActive(currentJob.isActive());
        dto.setCreatedAt(currentJob.getCreatedAt());
        dto.setCreatedBy(currentJob.getCreatedBy());

        if (currentJob.getSkills() != null) {
            List<String> skillNames = currentJob.getSkills()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.toList());
            dto.setSkills(skillNames);
        }

        return dto;
    }

    public ResUpdateJobDTO update(Job j, Job jobInDb) {
        if (j.getSkills() != null) {
            List<Long> reqSkills = j.getSkills()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            jobInDb.setSkills(dbSkills);
        }

        if (j.getCompany() != null) {
            Optional<Company> companyOptional = this.companyRepository.findById(j.getCompany().getId());
            if (companyOptional.isPresent()) jobInDb.setCompany(companyOptional.get());
        }

        jobInDb.setName(j.getName());
        jobInDb.setSalary(j.getSalary());
        jobInDb.setQuantity(j.getQuantity());
        jobInDb.setLocation(j.getLocation());
        jobInDb.setLevel(j.getLevel());
        jobInDb.setStartDate(j.getStartDate());
        jobInDb.setEndDate(j.getEndDate());
        jobInDb.setActive(j.isActive());

        Job currentJob = this.jobRepository.save(jobInDb);

        ResUpdateJobDTO dto = new ResUpdateJobDTO();
        dto.setId(currentJob.getId());
        dto.setName(currentJob.getName());
        dto.setSalary(currentJob.getSalary());
        dto.setQuantity(currentJob.getQuantity());
        dto.setLocation(currentJob.getLocation());
        dto.setLevel(currentJob.getLevel());
        dto.setStartDate(currentJob.getStartDate());
        dto.setEndDate(currentJob.getEndDate());
        dto.setActive(currentJob.isActive());
        dto.setCreatedAt(currentJob.getCreatedAt());
        dto.setCreatedBy(currentJob.getCreatedBy());

        if (currentJob.getSkills() != null) {
            List<String> skillNames = currentJob.getSkills()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.toList());
            dto.setSkills(skillNames);
        }

        return dto;
    }

    public void delete(long id) {
        this.jobRepository.deleteById(id);
    }

    public Optional<Job> fetchJobById(long id) {
        return this.jobRepository.findById(id);
    }

    public ResultPaginantionDTO fetchAll(
            Specification<Job> spec,
            Pageable pageable
    ) {
        Page<Job> page = this.jobRepository.findAll(spec, pageable);
        ResultPaginantionDTO rs = new ResultPaginantionDTO();
        ResultPaginantionDTO.Meta mt = new ResultPaginantionDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(page.getContent());

        return rs;
    }
}
