package apap.tugas.bobaxixixi.repository;

import java.util.Optional;

import apap.tugas.bobaxixixi.model.StoreBobateaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apap.tugas.bobaxixixi.model.StoreModel;

@Repository
public interface StoreDb extends JpaRepository<StoreModel, Long> {
    Optional<StoreModel> findByIdToko(Long idToko);
}
