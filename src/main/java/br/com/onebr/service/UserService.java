package br.com.onebr.service;

import br.com.onebr.controller.request.PasswordRecoverReq;
import br.com.onebr.controller.request.UserReq;
import br.com.onebr.controller.response.UserRes;
import br.com.onebr.exception.ConflictApiException;
import br.com.onebr.exception.ForbiddenApiException;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.exception.UnprocessableEntityApiException;
import br.com.onebr.model.Specie;
import br.com.onebr.model.security.User;
import br.com.onebr.repository.SpecieRepository;
import br.com.onebr.repository.UserRepository;
import br.com.onebr.service.util.PasswordGenerator;
import br.com.onebr.service.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UserService {

    private static final String USER_CACHE = "user";

    private static final PasswordGenerator PASSWORD_GENERATOR = new PasswordGenerator.PasswordGeneratorBuilder()
        .useDigits(true)
        .useUpper(true)
        .useLower(true)
        .build();

    private static final int PASSWORD_LENGTH = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecieRepository specieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MailService mailService;

    @Autowired
    private SecurityUtil securityUtil;

    @Value("${app.img.path}")
    private String appImagePath;

    @Cacheable(value = USER_CACHE, key = "#username")
    public User findUserByUsername(String username) {
        return userRepository.findOneWithRolesByUsername(username);
    }

    @Transactional
    public UserRes patchUser(Long id, UserReq userReq) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NotFoundApiException("user.invalid"));

        securityUtil.validateLoggedUserCanChangeResource(user.getId());

        Optional.ofNullable(userReq.getActive()).ifPresent(status -> {
            if (user.isAdmin() && !status) {
                log.warn("message=Attempt of change ADMIN user activation status. id={}, status={}", id, status);
                throw new UnprocessableEntityApiException("user.admin.active.status.change");
            }
            user.setActive(status);
        });

        final String cacheKey = user.getUsername();

        if (!StringUtils.isEmpty(userReq.getUsername())) {
            final User userByUsername = userRepository.findOneWithRolesByUsername(userReq.getUsername());
            if (userByUsername != null && userByUsername.getId() != user.getId()) {
                throw new ConflictApiException("username.already.exists");
            }

            user.setUsername(userReq.getUsername());
        }

        Optional.ofNullable(userReq.getEmail()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> {
            final User userByEmail = userRepository.findByProfileEmail(x);
            if (userByEmail != null && userByEmail.getId() != user.getId()) {
                throw new ConflictApiException("email.already.exists");
            }

            user.getProfile().setEmail(x);
        });

        Optional.ofNullable(userReq.getName()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> user.getProfile().setName(x));
        Optional.ofNullable(userReq.getPassword()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> user.setPassword(passwordEncoder.encode(x)));

        if (!CollectionUtils.isEmpty(userReq.getSpecies())) {
            final List<Long> speciesReq = userReq.getSpecies().stream().map(x -> x.getId()).filter(x -> x != null).collect(Collectors.toList());

            final Set<Specie> species = specieRepository.findAllByIdIn(speciesReq);
            if (CollectionUtils.isEmpty(species)) {
                log.error("message=Attempt to update user with invalid species. userId={}, species={}", id, userReq.getSpecies());
                throw new ForbiddenApiException("");
            }

            final User loggedUser = userRepository.findById(securityUtil.getAuthentication().getId())
                .orElseThrow(() -> new NotFoundApiException("user.invalid"));
            if (!loggedUser.isAdmin()) {
                if (!species.containsAll(loggedUser.getSpecies()) || !loggedUser.getSpecies().containsAll(species)) {
                    log.error("message=Attempt to update user species with non admin user. userId={}, species={}", id, userReq.getSpecies());
                    throw new ForbiddenApiException("");
                }
            }

            user.setSpecies(species);
        }

        User userDb = userRepository.save(user);

        // Invalidate cache for updated user
        cacheManager.getCache(USER_CACHE).evictIfPresent(cacheKey);

        return UserRes.builder()
            .id(userDb.getId())
            .username(userDb.getUsername())
            .active(userDb.isActive())
            .profile(userDb.getProfile())
            .species(userDb.getSpecies())
            .build();
    }

    public UserRes getUser(Long id) {
        final Optional<User> user = userRepository.findById(id);
        User userDB = user.orElseThrow(() -> new NotFoundApiException("user.invalid"));

        securityUtil.validateLoggedUserCanChangeResource(userDB.getId());

        return UserRes.builder()
            .id(userDB.getId())
            .username(userDB.getUsername())
            .active(userDB.isActive())
            .profile(userDB.getProfile())
            .species(userDB.getSpecies())
            .build();
    }

    public Page<UserRes> findAll(String searchTerm, Pageable page) {
        if (searchTerm == null) {
            searchTerm = "";
        }

        final Page<User> users = userRepository.findAllForAdminList(searchTerm, page);

        if (users.isEmpty()) {
            log.warn("message=No users found on database.");
            throw new NotFoundApiException("user.not.found");
        }

        final List<UserRes> userResList = new ArrayList<>();
        users.forEach(u -> userResList.add(new UserRes().build(u)));

        return new PageImpl<>(userResList, page, users.getTotalElements());
    }

    @Transactional
    public void updateActiveStatus(Long id, boolean status) {
        final Optional<User> user = userRepository.findById(id);

        User userDB = user.orElseThrow(() -> new NotFoundApiException("user.invalid"));
        if (userDB.isAdmin()) {
            log.warn("message=Attempt of change ADMIN user activation status. id={}, status={}", id, status);
            throw new UnprocessableEntityApiException("user.admin.active.status.change");
        }

        userDB.setActive(status);
        userRepository.save(userDB);

        // Invalidate cache for updated user
        cacheManager.getCache(USER_CACHE).evictIfPresent(userDB.getUsername());
    }

    @Transactional
    public void passwordRecover(PasswordRecoverReq req) {
        User user = userRepository.findByUsername(req.getUsername());
        if (user == null) {
            user = userRepository.findByProfileEmail(req.getUsername());
        }

        Optional.ofNullable(user).orElseThrow(() -> new NotFoundApiException("user.invalid"));
        if (!user.isActive()) {
            log.warn("message=Attempt of recover password of inactive user. id={}", user.getId());
            throw new NotFoundApiException("user.invalid");
        }

        final String password = PASSWORD_GENERATOR.generate(PASSWORD_LENGTH);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        mailService.sendPasswordRecoverMail(user, password);

        cacheManager.getCache(USER_CACHE).evictIfPresent(user.getUsername());
    }
}
