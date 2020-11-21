package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.CompanyDTO;
import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.company.Branch;
import eu.mrndesign.www.matned.model.company.Company;
import eu.mrndesign.www.matned.model.company.CompanyDetails;
import eu.mrndesign.www.matned.model.company.Department;
import eu.mrndesign.www.matned.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @MockBean
    private CompanyRepository companyRepository;
    @MockBean
    private CompanyDetailsRepository companyDetailsRepository;
    @MockBean
    private BranchRepository branchRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private AddressRepository addressRepository;

    private List<Company> companies;
    private List<CompanyDetails> companyDetails;
    private List<Branch> branches;
    private List<Department> departments;
    private List<Address> addresses;


    @BeforeEach
    void setUp(){
        companies = new LinkedList<>();
        companyDetails = new LinkedList<>();
        branches = new LinkedList<>();
        departments = new LinkedList<>();
        addresses = new LinkedList<>();

        companies.add(new Company(new EntityDescription("TestName", "TestDescription")));
        companyDetails.add(new CompanyDetails());
        companyDetails.add(new CompanyDetails());
        branches.add(new Branch(new EntityDescription("TestBranchName", "TestBranchDescription")));
        departments.add(new Department(new EntityDescription("TestDeptName", "TestDeptDesc")));
        addresses.add(new Address());

        companies.get(0).setCompanyDetails(companyDetails.get(0));
        branches.get(0).setCompany(companies.get(0));
        branches.get(0).setAddress(addresses.get(0));
        departments.get(0).setBranch(branches.get(0));
    }

    @Test
    void addCompany() {
        doReturn(companies.get(0)).when(companyRepository).save(any());
        doReturn(companyDetails.get(1)).when(companyDetailsRepository).save(any());

        assertEquals(companyService.addCompany(new CompanyDTO()), CompanyDTO.apply(companies.get(0)) );

    }

    @Test
    void findAllCompanies() {
    }

    @Test
    void findCompanyById() {
    }

    @Test
    void deleteCompany() {
    }

    @Test
    void updateCompany() {
    }

    @Test
    void createBranch() {
    }

    @Test
    void setBranchAddress() {
    }

    @Test
    void findBranchById() {
    }

    @Test
    void deleteBranch() {
    }

    @Test
    void updateBranch() {
    }

    @Test
    void findAllCompanyBranches() {
    }

    @Test
    void findAllBranches() {
    }

    @Test
    void createDepartment() {
    }

    @Test
    void findAllDepartments() {
    }

    @Test
    void findAllBranchDepartments() {
    }

    @Test
    void findAllCompanyDepartments() {
    }

    @Test
    void findDepartmentById() {
    }

    @Test
    void deleteDepartment() {
    }

    @Test
    void updateDepartment() {
    }

    @Test
    void setDepartmentsBranch() {
    }
}
