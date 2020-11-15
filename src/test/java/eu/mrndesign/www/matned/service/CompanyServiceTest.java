package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Test
    void addCompany() {
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
