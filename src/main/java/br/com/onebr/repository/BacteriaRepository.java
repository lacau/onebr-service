package br.com.onebr.repository;

import br.com.onebr.model.Bacteria;
import br.com.onebr.model.query.BacteriaAdminQueryResult;
import br.com.onebr.model.query.BacteriaQueryResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BacteriaRepository extends PagingAndSortingRepository<Bacteria, Long> {

    @Query(value = CoordinateQueryResult.QUERY, nativeQuery = true)
    List<CoordinateQueryResult> findAllCoordinates(@Param("specieId") Long specieId);

    @Query(value = BacteriaQueryResult.QUERY, nativeQuery = true)
    Page<BacteriaQueryResult> findAllBySearchTerm(@Param("specie") Long specie, @Param("searchTerm") String searchTerm,
        @Param("resistomes") List<Integer> resistomes,
        @Param("yearStart") int yearStart,
        @Param("yearEnd") int yearEnd,
        @Param("ids") List<Integer> ids,
        Pageable pageable);

    @Query(value = BacteriaAdminQueryResult.QUERY, nativeQuery = true)
    Page<BacteriaAdminQueryResult> findAllBySearchTermForAdmin(@Param("specieIds") List<Integer> specieIds, @Param("searchTerm") String searchTerm,
        @Param("yearStart") int yearStart,
        @Param("yearEnd") int yearEnd,
        Pageable pageable);

    @Query("SELECT b FROM Bacteria b LEFT JOIN FETCH b.city c LEFT JOIN FETCH c.country co WHERE b.id = :id")
    Optional<Bacteria> findByIdForEdit(@Param("id") Long id);
}
