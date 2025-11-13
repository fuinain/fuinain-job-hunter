package com.example.controller;

import com.example.domain.Skill;
import com.example.domain.response.ResultPaginantionDTO;
import com.example.service.SkillService;
import com.example.util.annotation.ApiMessage;
import com.example.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService){
        this.skillService = skillService;
    }

    @PostMapping("/skills")
    @ApiMessage("Create a skill")
    public ResponseEntity<Skill> create(@Valid @RequestBody Skill skill) throws IdInvalidException {
        if (skill.getName() != null && this.skillService.isNameExist(skill.getName())){
            throw new IdInvalidException("Skill name already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.createSkill(skill));
    }

    @PutMapping("/skills")
    @ApiMessage("Update a skill")
    public ResponseEntity<Skill> update(@Valid @RequestBody Skill skill) throws IdInvalidException {
        Skill currentSkill = this.skillService.fetchSkillById(skill.getId());
        if (currentSkill == null) throw new IdInvalidException("Skill ID not exist");

        if (skill.getName() != null && this.skillService.isNameExist(skill.getName())) throw new IdInvalidException("Skill name already exists");

        currentSkill.setName(skill.getName());
        return ResponseEntity.ok().body(this.skillService.updateSkill(currentSkill));
    }

    @GetMapping("/skills")
    @ApiMessage("fetch all skill")
    public ResponseEntity<ResultPaginantionDTO> getAll (
            @Filter Specification<Skill> spec,
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.fetchAllSkills(spec, pageable));
    }


    @DeleteMapping("/skills/{id}")
    @ApiMessage("Delete a skill")
    public ResponseEntity<Void> deleteSkill(@PathVariable long id) throws IdInvalidException {
        Skill currentSkill = this.skillService.fetchSkillById(id);
        if (currentSkill == null) throw new IdInvalidException("Skill ID not exist");
        this.skillService.deleteSkill(id);
        return ResponseEntity.ok().body(null);
    }
}
