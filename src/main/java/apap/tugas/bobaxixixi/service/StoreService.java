package apap.tugas.bobaxixixi.service;

import java.util.List;
import apap.tugas.bobaxixixi.model.*;

public interface StoreService {
    List<StoreModel> getStoreList();
    boolean addStore(StoreModel store);
    String defineStorecode(StoreModel store);
    StoreModel getStoreByIdToko(Long idToko);
    boolean updateStore(StoreModel store);
    boolean deleteStore(StoreModel store);

}
