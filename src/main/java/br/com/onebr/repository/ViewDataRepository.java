package br.com.onebr.repository;

import br.com.onebr.enumeration.Scope;
import br.com.onebr.model.config.ViewData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewDataRepository extends JpaRepository<ViewData, String> {

    List<ViewData> findByScopeAndAdminOrderByKey(Scope scope, boolean admin);

    ViewData findOneByScopeAndKeyAndAdmin(Scope scope, String key, boolean admin);
}
