package apap.tugas.bobaxixixi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apap.tugas.bobaxixixi.model.BobateaModel;

@Repository
public interface BobateaDb extends JpaRepository<BobateaModel, Long>{
    Optional<BobateaModel> findByIdBobatea(Long idBobatea);
}
