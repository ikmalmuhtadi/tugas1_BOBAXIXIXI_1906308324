package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreBobateaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import apap.tugas.bobaxixixi.repository.StoreBobateaDb;
import java.util.Random;
import apap.tugas.bobaxixixi.repository.*;
import apap.tugas.bobaxixixi.model.*;
import apap.tugas.bobaxixixi.service.*;

@Service
@Transactional
public class StoreBobateaServiceImpl implements StoreBobateaService{

    @Autowired
    StoreBobateaDb storeBobateaDb;

    @Autowired
    BobateaDb bobateaDb;

    @Autowired
    StoreDb storeDb;

    @Override
    public String defineProductioncode(StoreBobateaModel storebobatea) {
        //1 digit isTrue bobatea punya topping
        String mergeAll = "";
        String mergeAllCapitalized = "";
        if(storebobatea.getBobatea().getTopping() == null) {
            String isToppingString =  "0";
            String pc = "PC";
            //3 digit idToko
            String digitIdToko = String.format("%03d", storebobatea.getStore().getIdToko());
            //3 digit idBobatea
            String digitIdBobatea = String.format("%03d",storebobatea.getBobatea().getIdBobatea());
            mergeAll = pc + digitIdToko + isToppingString + digitIdBobatea;
            mergeAllCapitalized = mergeAll.toUpperCase();
        } else {
            String isToppingString =  "1";
            String pc = "PC";
            //3 digit idToko
            String digitIdToko = String.format("%03d", storebobatea.getStore().getIdToko());
            //3 digit idBobatea
            String digitIdBobatea = String.format("%03d", storebobatea.getBobatea().getIdBobatea());
            mergeAll = pc + digitIdToko + isToppingString + digitIdBobatea;
            mergeAllCapitalized = mergeAll.toUpperCase();
        }

        return mergeAllCapitalized;
    }

    @Override
    public List<StoreBobateaModel> getStoreBobateaByStore(StoreModel store) {
        List<StoreBobateaModel> listStoreBobatea = new ArrayList<>();
        for(StoreBobateaModel i : storeBobateaDb.findAllBobateaStoreByStore(store)) {
            listStoreBobatea.add(i);
        }
        return listStoreBobatea;
    }

    @Override
    public List<StoreBobateaModel> getStoreBobateaByBobatea(BobateaModel bobatea) {
        List<StoreBobateaModel> listStoreBobatea = new ArrayList<>();
        for(StoreBobateaModel i : storeBobateaDb.findAllBobateaStoreByBobatea(bobatea)) {
            listStoreBobatea.add(i);
        }
        return listStoreBobatea;
    }

    @Override
    public List<BobateaModel> getListBobaByStore(StoreModel store) {
        List<BobateaModel> listBoba = new ArrayList<>();
        for(StoreBobateaModel i : storeBobateaDb.findAllBobateaStoreByStore(store)) {
            listBoba.add(i.getBobatea());
        }
        return listBoba;
    }

    @Override
    public List<StoreModel> getListStoreByBoba(BobateaModel bobatea) {
        List<StoreModel> listStore = new ArrayList<>();
        for(StoreBobateaModel i : storeBobateaDb.findAllBobateaStoreByBobatea(bobatea)) {
            listStore.add(i.getStore());
        }
        return listStore;
    }


    @Override
    public void addStoreBobatea(StoreBobateaModel storebobatea) {
        storeBobateaDb.save(storebobatea);
    }

    @Override
    public void deleteStoreBobatea(StoreBobateaModel storebobatea) {
        List<StoreBobateaModel> listStoreBobatea = storeBobateaDb.findAllBobateaStoreByStore(storebobatea.getStore());
        for (StoreBobateaModel i : listStoreBobatea) {
            storeBobateaDb.delete(i);
        }
    }


}
