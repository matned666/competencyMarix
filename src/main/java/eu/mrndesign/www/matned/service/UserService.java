package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.dto.UserRegistrationDTO;
import eu.mrndesign.www.matned.dto.UserDTO;
import eu.mrndesign.www.matned.model.personal.Person;
import eu.mrndesign.www.matned.model.security.User;
import eu.mrndesign.www.matned.model.security.UserRole;
import eu.mrndesign.www.matned.repository.UserRepository;
import eu.mrndesign.www.matned.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService extends BaseService{

    @Value("${default.admin.username}")
    private String defaultAdminLogin;

    public static final String USER_NOT_FOUND = "User not found";
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PersonService personService;

    public UserService(PasswordEncoder passwordEncoder,
                       HttpServletRequest request,
                       UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PersonService personService) {
        this.passwordEncoder = passwordEncoder;
        this.request = request;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.personService = personService;
    }

    public List<UserDTO> findAll(){
        return convertEntityToUserDTOList(userRepository.findAll());
    }

    public List<UserDTO> findAll(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        Page<User> users = userRepository.findAll(pageable);
        List<User> _users = users.getContent();
        return convertEntityToUserDTOList(_users);
    }

    public UserDTO findUserById(Long id) {return UserDTO.applyWithAudit(userRepository.findById(id).orElseThrow(()->new RuntimeException(USER_NOT_FOUND)));}

    public UserDTO add(UserRegistrationDTO dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserRole role = userRoleRepository.findByRoleName(UserRole.Role.USER.roleName());
        return UserDTO.applyWithAudit(userRepository.save(User.applyRegistration(dto, role)));
    }

    public UserDTO updatePassword(Long id, UserRegistrationDTO dto){
        User entity = userRepository.findById(id).orElseThrow(()-> new RuntimeException(USER_NOT_FOUND));
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));  //  encoding password inside DTO for a better security
        entity.setPassword(dto.getPassword());
        userRepository.save(entity);
        return UserDTO.applyWithAudit(entity);
    }

    public UserDTO updateLogin(Long id, UserDTO dto){
        User entity = userRepository.findById(id).orElseThrow(()->new RuntimeException(USER_NOT_FOUND));
        entity.setLogin(dto.getLogin());
        return UserDTO.applyWithAudit(userRepository.save(entity));
    }

    public UserDTO assignNewRole(Long userId, String role){
        UserRole.Role parsedRole = UserRole.Role.valueOf(role);
        User entity = userRepository.findById(userId).orElseThrow(()->new RuntimeException(USER_NOT_FOUND));
        UserRole userRole = userRoleRepository.findByRoleName(parsedRole.roleName());
        entity.addRole(userRole);
        return UserDTO.applyWithAudit(userRepository.save(entity));
    }

    public UserDTO takeAwayRole(Long userId, String role){
        UserRole.Role parsedRole = UserRole.Role.valueOf(role);
        User entity = userRepository.findById(userId).orElseThrow(()->new RuntimeException(USER_NOT_FOUND));
        UserRole userRole = userRoleRepository.findByRoleName(parsedRole.roleName());
        entity.removeRole(userRole);
        return UserDTO.applyWithAudit(userRepository.save(entity));
    }

    public UserDTO updateUserPersonalData(Long userId, Long personId){
        Person person = personService.findPersonByIdService(personId);
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException(USER_NOT_FOUND));
        user.setPerson(person);
        userRepository.save(user);
        return UserDTO.applyWithAudit(user);
    }


    public UserDTO deletePersonalDataFromUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException(USER_NOT_FOUND));
        user.setPerson(null);
        userRepository.save(user);
        return UserDTO.applyWithAudit(user);
    }

    public List<UserDTO> deleteUser(Long id, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        userRepository.deleteById(id);
        return findAll(startPage, itemsPerPage, sortBy);
    }

    public PersonDTO getUserPersonalData(Long id) {
        return PersonDTO.apply(personService.findPersonByIdService(id));
    }


    public UserDTO findUserByLogin(String login){
        return UserDTO.applyWithAudit(userRepository.findByLogin(login));
    }

    public Optional<User> getAuditor() {
        return userRepository.findAuditorByLogin(request.getUserPrincipal() != null ?
                request.getUserPrincipal().getName() :
                defaultAdminLogin);
    }


//            Private

    List<UserDTO> convertEntityToUserDTOList(List<User> all) {
        return all.stream()
                .map(UserDTO::applyWithAudit)
                .collect(Collectors.toList());
    }
}
