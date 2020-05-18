package br.com.onebr.repository;

import br.com.onebr.model.dashboard.DashboardMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardMenuRepository extends JpaRepository<DashboardMenu, String> {

    @Query(value = "SELECT dm FROM DashboardMenu dm WHERE dm.active IS TRUE AND (dm.admin = :admin OR dm.admin IS FALSE)")
    List<DashboardMenu> findByAdminAndActiveIsTrueOrderByOrder(boolean admin);
}
