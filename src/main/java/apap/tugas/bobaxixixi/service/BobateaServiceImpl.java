package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobateaModel;
import apap.tugas.bobaxixixi.model.StoreBobateaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.repository.BobateaDb;
import apap.tugas.bobaxixixi.repository.StoreBobateaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import apap.tugas.bobaxixixi.repository.BobateaDb;

@Service
@Transactional
public class BobateaServiceImpl implements BobateaService{

    @Autowired
    BobateaDb bobateaDb;

    @Autowired
    StoreBobateaDb storeBobateaDb;

    @Override
    public List<BobateaModel> getBobateaList() {
        return bobateaDb.findAll();
    }

    @Override
    public void addBobatea(BobateaModel bobatea) {
        bobateaDb.save(bobatea);
    }

    @Override
    public BobateaModel getBobateaByIdBobatea(Long idBobatea) {
        Optional<BobateaModel> bobatea = bobateaDb.findByIdBobatea(idBobatea);
        if(bobatea.isPresent()){
            return bobatea.get();
        }
        return null;
    }

    @Override
    public void updateBobatea(BobateaModel bobatea) {
        bobateaDb.save(bobatea);
    }

    @Override
    public boolean deleteBobatea(BobateaModel bobatea) {
//        bobateaDb.delete(bobatea);
        List<StoreBobateaModel> temp = storeBobateaDb.findAllBobateaStoreByBobatea(bobatea);
        System.out.print("INI TEMP : ");
        System.out.println(temp);
//        bobateaDb.delete(bobatea);
        if(temp.isEmpty()) {
            bobateaDb.delete(bobatea);
            return true;
        } else {

            for (StoreBobateaModel x : temp) {
                StoreModel store = x.getStore();
                System.out.print("INI WAKTU BUKA : ");
                System.out.println(store.getWaktuBuka());
                System.out.print("INI WAKTU TUTUP : ");
                System.out.println(store.getWaktuTutup());
                LocalTime timeNow = LocalTime.now();
                if (store.getWaktuBuka().isAfter(store.getWaktuTutup())) {
                    if (timeNow.isBefore(store.getWaktuBuka()) && timeNow.isAfter(store.getWaktuTutup())) {
                        if (store.getListStoreBobatea().isEmpty()) {
                            if (x.getBobatea().getListStoreBobatea().isEmpty()) {
                                bobateaDb.delete(bobatea);
                                return true;
                            }
                        }
                    } else {
                        System.out.println("MASUK ELSE 1");
                        return false;
                    }
                } else {
                    System.out.println("MASUK ELSE 2");
                    if (timeNow.isAfter(store.getWaktuTutup()) || timeNow.isBefore(store.getWaktuBuka())) {
                        if (x.getBobatea().getListStoreBobatea().isEmpty()) {
                            bobateaDb.delete(bobatea);
                            return true;
                        }
                    } else {
                        System.out.println("MASUK ELSE 3");
                        return false;
                    }
                }
                System.out.println("MASUK ELSE 4");
                return false;
            }
        }
        System.out.println("MASUK ELSE 5");
        return false;
    }

    @Override
    public List<StoreBobateaModel> getBobateaBasedonTopping(String bobaName, String namaTopping) {
        return storeBobateaDb.searchBobateaBasedonTopping(bobaName, namaTopping);
    }

    @Override
    public List<StoreBobateaModel> getFilterManagerbyBoba(String bobaName) {
        return storeBobateaDb.filterManagerByBoba(bobaName);
    }
}
