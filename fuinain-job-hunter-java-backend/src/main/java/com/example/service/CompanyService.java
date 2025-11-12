package com.example.service;

import com.example.domain.Company;
import com.example.domain.User;
import com.example.domain.dto.Meta;
import com.example.domain.dto.ResultPaginantionDTO;
import com.example.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public ResultPaginantionDTO handleGetAllCompanies(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompanies = this.companyRepository.findAll(spec, pageable);
        ResultPaginantionDTO rs = new ResultPaginantionDTO();
        Meta mt = new Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageCompanies.getTotalPages());
        mt.setTotal(pageCompanies.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompanies.getContent());
        return rs;

    }

    public Company handleUpdateCompany(Company company) {
        Optional<Company> companyOptional = this.companyRepository.findById(company.getId());
        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setLogo(company.getLogo());
            currentCompany.setName(company.getName());
            currentCompany.setDescription(company.getDescription());
            currentCompany.setAddress(company.getAddress());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }

    public void handleDeleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }
}
