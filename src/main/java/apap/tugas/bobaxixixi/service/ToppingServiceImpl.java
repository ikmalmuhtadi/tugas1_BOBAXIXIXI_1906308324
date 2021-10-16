package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ToppingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import apap.tugas.bobaxixixi.repository.ToppingDb;

@Service
@Transactional
public class ToppingServiceImpl implements ToppingService {
    @Autowired
    ToppingDb toppingDb;

    @Override
    public List<ToppingModel> getListTopping() {
        return toppingDb.findAll();
    }
}
