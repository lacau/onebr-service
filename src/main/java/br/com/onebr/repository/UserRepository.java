package br.com.onebr.repository;

import br.com.onebr.model.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u JOIN FETCH u.roles r WHERE u.username = :username")
    User findOneWithRolesByUsername(@Param("username") String username);

    User findByUsername(String username);

    User findByProfileEmail(String email);

    @Query(value = "SELECT u.* FROM \"user\" u "
        + "INNER JOIN profile profile ON profile.id = u.fk_profile "
        + "WHERE "
        + ":searchTerm IS NOT NULL AND ("
        + "     LOWER(unaccent(u.username)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "     LOWER(unaccent(profile.name)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "     LOWER(unaccent(profile.email)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) "
        + ") OR :searchTerm IS NULL", nativeQuery = true)
    Page<User> findAllForAdminList(@Param("searchTerm") String searchTerm, Pageable pageable);
}
