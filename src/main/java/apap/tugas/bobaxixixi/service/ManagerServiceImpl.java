package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreBobateaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.repository.StoreBobateaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import apap.tugas.bobaxixixi.repository.ManagerDb;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService{

    @Autowired
    ManagerDb managerDb;

    @Autowired
    StoreBobateaDb storeBobateaDb;

    @Override
    public List<ManagerModel> getListManager() {
        return managerDb.findAll();
    }



}
