package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.*;

import java.util.List;

public interface BobateaService {
    List<BobateaModel> getBobateaList();
    void addBobatea(BobateaModel bobatea);
    BobateaModel getBobateaByIdBobatea(Long idBobatea);
    void updateBobatea(BobateaModel bobatea);
    boolean deleteBobatea(BobateaModel bobatea);
    List<StoreBobateaModel> getBobateaBasedonTopping(String bobaName, String namaTopping);
    List<StoreBobateaModel> getFilterManagerbyBoba(String bobaName);
}
