package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.Patterns;
import eu.mrndesign.www.matned.dto.BranchDTO;
import eu.mrndesign.www.matned.dto.CompanyDTO;
import eu.mrndesign.www.matned.dto.DepartmentDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.company.Branch;
import eu.mrndesign.www.matned.model.company.Company;
import eu.mrndesign.www.matned.model.company.CompanyDetails;
import eu.mrndesign.www.matned.model.company.Department;
import eu.mrndesign.www.matned.repository.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;
import static eu.mrndesign.www.matned.service.PersonService.ADDRESS_NOT_FOUND;

@Service
public class CompanyService extends BaseService {

    public static final String COMPANY_NOT_FOUND = "Company not found";
    public static final String COMPANY_DETAILS_NOT_FOUND = "Company details not found";
    public static final String BRANCH_NOT_FOUND = "Branch not found";
    public static final String DEPARTMENT_NOT_FOUND = "Department not found";

    private final CompanyRepository companyRepository;
    private final CompanyDetailsRepository companyDetailsRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;


    public CompanyService(CompanyRepository companyRepository,
                          CompanyDetailsRepository companyDetailsRepository,
                          BranchRepository branchRepository,
                          DepartmentRepository departmentRepository,
                          AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.companyDetailsRepository = companyDetailsRepository;
        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
    }

    public CompanyDTO addCompany(CompanyDTO dto) {
        Company company = new Company(new EntityDescription(dto.getName(), dto.getDescription()));
        CompanyDetails details = new CompanyDetails();
        if (Patterns.isCorrectDate(dto.getCompanyCreationDate(), DATE_FORMATTER))
            details.setCompanyCreationDate(LocalDate.parse(dto.getCompanyCreationDate(), DATE_FORMATTER));
        details.setVAT_no(dto.getVAT_no());
        company.setCompanyDetails(companyDetailsRepository.save(details));
        return CompanyDTO.apply(companyRepository.save(company));
    }

    public List<CompanyDTO> findAllCompanies(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertCompanyToDTOList(companyRepository.findAll(pageable).getContent());
    }

    public CompanyDTO findCompanyById(Long id) {
        return CompanyDTO.apply(companyRepository.findById(id).orElseThrow(() -> new RuntimeException(COMPANY_NOT_FOUND)));
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyDTO updateCompany(Long id, CompanyDTO dto) {
        Company entity = companyRepository.findById(id).orElseThrow(() -> new RuntimeException(COMPANY_NOT_FOUND));
        CompanyDetails details = companyDetailsRepository.findByCompanyId(id).orElseThrow(() -> new RuntimeException(COMPANY_DETAILS_NOT_FOUND));
        if (Patterns.isCorrectDate(dto.getCompanyCreationDate(), DATE_FORMATTER))
            details.setCompanyCreationDate(LocalDate.parse(dto.getCompanyCreationDate(), DATE_FORMATTER));
        if (isChanged(dto.getVAT_no()))
            details.setVAT_no(dto.getVAT_no());
        if (isChanged(dto.getName()))
            entity.getEntityDescription().setName(dto.getName());
        if (isChanged(dto.getDescription()))
            entity.getEntityDescription().setDescription(dto.getDescription());
        companyDetailsRepository.save(details);
        return CompanyDTO.apply(companyRepository.save(entity));
    }

    public BranchDTO createBranch(Long companyId, BranchDTO branchDTO) {
        Branch branch = new Branch();
        branch.setEntityDescription(new EntityDescription(branchDTO.getName(), branchDTO.getDescription()));
        branch.setCompany(companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException(COMPANY_NOT_FOUND)));
        Branch _branch = branchRepository.save(branch);
        if (branchRepository.isFirstBranch(companyId)) {
            CompanyDetails details = companyDetailsRepository.findByCompanyId(companyId).orElseThrow(() -> new RuntimeException(COMPANY_DETAILS_NOT_FOUND));
            details.setMotherBranch(_branch);
            companyDetailsRepository.save(details);
        }
        return BranchDTO.apply(_branch);
    }

    public BranchDTO setBranchAddress(Long branchId, Long addressId) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException(BRANCH_NOT_FOUND));
        branch.setAddress(addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND)));
        return BranchDTO.apply(branchRepository.save(branch));
    }

    public BranchDTO findBranchById(Long branchId) {
        return BranchDTO.apply(branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException(BRANCH_NOT_FOUND)));
    }

    public void deleteBranch(Long branchId) {
        branchRepository.deleteById(branchId);
    }

    public BranchDTO updateBranch(Long branchId, BranchDTO dto) {
        String name = dto.getName();
        String description = dto.getDescription();
        String branchCreationDate = dto.getBranchCreationDate();
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException(BRANCH_NOT_FOUND));
        if (isChanged(name))
            branch.getEntityDescription().setName(name);
        if (isChanged(description))
            branch.getEntityDescription().setDescription(description);
        if (Patterns.isCorrectDate(branchCreationDate, DATE_FORMATTER))
            branch.setBranchCreationDate(LocalDate.parse(branchCreationDate, DATE_FORMATTER));
        return BranchDTO.apply(branchRepository.save(branch));
    }

    public List<BranchDTO> findAllCompanyBranches(Long companyId, Integer startPage, Integer itemsPerPage, String[] sortBy){
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertBranchToDTOList(branchRepository.findAllByCompanyId(companyId, pageable).getContent());
    }

    public List<BranchDTO> findAllBranches(Integer startPage, Integer itemsPerPage, String[] sortBy){
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertBranchToDTOList(branchRepository.findAll(pageable).getContent());
    }

    public DepartmentDTO createDepartment(Long branchId, DepartmentDTO departmentDTO){
        Department department = new Department(new EntityDescription(departmentDTO.getName(), departmentDTO.getDescription()));
        department.setBranch(branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException(BRANCH_NOT_FOUND)));
        return DepartmentDTO.apply(departmentRepository.save(department));
    }

    public List<DepartmentDTO> findAllDepartments(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertDepartmentToDTOList(departmentRepository.findAll(pageable).getContent());
    }

    public List<DepartmentDTO> findAllBranchDepartments(Long branchId, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertDepartmentToDTOList(departmentRepository.findAllByBranch(branchId, pageable).getContent());
    }

    public List<DepartmentDTO> findAllCompanyDepartments(Long companyId, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertDepartmentToDTOList(departmentRepository.findAllByCompany(companyId, pageable).getContent());
    }

    public DepartmentDTO findDepartmentById(Long deptId){
        return DepartmentDTO.apply(departmentRepository.findById(deptId).orElseThrow(()->new RuntimeException(DEPARTMENT_NOT_FOUND)));
    }

    public void deleteDepartment(Long deptId){
        departmentRepository.deleteById(deptId);
    }

    public DepartmentDTO updateDepartment(Long deptId, DepartmentDTO dto){
        Department department = departmentRepository.findById(deptId).orElseThrow(()->new RuntimeException(DEPARTMENT_NOT_FOUND));
        if(isChanged(dto.getDescription())) department.getEntityDescription().setDescription(dto.getDescription());
        if(isChanged(dto.getName())) department.getEntityDescription().setName(dto.getName());
        return DepartmentDTO.apply(departmentRepository.save(department));
    }

    public DepartmentDTO setDepartmentsBranch(Long deptId, Long branchId){
        Department department = departmentRepository.findById(deptId).orElseThrow(()->new RuntimeException(DEPARTMENT_NOT_FOUND));
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException(BRANCH_NOT_FOUND));
        department.setBranch(branch);
        return DepartmentDTO.apply(departmentRepository.save(department));
    }







    private List<DepartmentDTO> convertDepartmentToDTOList(List<Department> content) {
        return content.stream()
                .map(DepartmentDTO::apply)
                .collect(Collectors.toList());
    }

    private List<BranchDTO> convertBranchToDTOList(List<Branch> content) {
        return content.stream()
                .map(BranchDTO::apply)
                .collect(Collectors.toList());
    }

    private List<CompanyDTO> convertCompanyToDTOList(List<Company> content) {
        return content.stream()
                .map(CompanyDTO::apply)
                .collect(Collectors.toList());
    }
}
