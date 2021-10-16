package apap.tugas.bobaxixixi.repository;

import java.util.Optional;

import apap.tugas.bobaxixixi.model.BobateaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apap.tugas.bobaxixixi.model.ManagerModel;

@Repository
public interface ManagerDb extends JpaRepository<ManagerModel, Long>{
    //buat sql utk return listmanager model yang belom dipilih di store lain (add)
    //buat update sama sqlnya, tp ada tambahan yg udah dipilih distore ini
    //buat method lg di service buat manggil listmanager yg bakal ditampilin ke html
}
