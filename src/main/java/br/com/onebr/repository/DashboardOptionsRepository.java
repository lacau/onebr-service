package br.com.onebr.repository;

import br.com.onebr.model.dashboard.DashboardOptions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardOptionsRepository extends JpaRepository<DashboardOptions, String> {

    @Query(value = "SELECT do FROM DashboardOptions do WHERE do.active IS TRUE AND (do.admin = :admin OR do.admin IS FALSE)")
    List<DashboardOptions> findByAdminAndActiveIsTrueOrderByOrder(@Param("admin") boolean admin);
}
