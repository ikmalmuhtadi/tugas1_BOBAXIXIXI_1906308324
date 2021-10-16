package apap.tugas.bobaxixixi.service;

import java.util.List;
import apap.tugas.bobaxixixi.model.*;

public interface StoreBobateaService {
    String defineProductioncode(StoreBobateaModel storebobatea);
    List<StoreBobateaModel> getStoreBobateaByStore(StoreModel store);
    List<StoreBobateaModel> getStoreBobateaByBobatea(BobateaModel bobatea);
    List<BobateaModel> getListBobaByStore(StoreModel store);
    List<StoreModel> getListStoreByBoba(BobateaModel bobatea);
    void addStoreBobatea(StoreBobateaModel storebobatea);
    void deleteStoreBobatea(StoreBobateaModel storebobatea);
}
