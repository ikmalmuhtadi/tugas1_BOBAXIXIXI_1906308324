package apap.tugas.bobaxixixi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import apap.tugas.bobaxixixi.repository.*;
import apap.tugas.bobaxixixi.model.*;


@Service
@Transactional
public class StoreServiceImpl implements StoreService{

    @Autowired
    StoreDb storeDb;

    @Autowired
    StoreBobateaDb storeBobateaDb;

    @Override
    public List<StoreModel> getStoreList() {
        return storeDb.findAll();
    }

    @Override
    public boolean  addStore(StoreModel store) {
//        String storeCode = defineStorecode(store);
//        store.setNoTeleponToko(storeCode);
//        storeDb.save(store);

        try {
            for(StoreModel storeModel:storeDb.findAll()){
                if(storeModel.getManager().getNamaManager().equals(store.getManager().getNamaManager())) {
                    return false;
                } else {
                    String storeCode = defineStorecode(store);
                    store.setNoTeleponToko(storeCode);
                    storeDb.save(store);
                    return true;
                }
            }
        }
        catch(Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public String defineStorecode(StoreModel store) {
        String sc = "SC";

        //tiga huruf pertama nama toko reversed
        String temp1 = store.getNamaToko();
        String tigaAwal = temp1.substring(0,3);
        StringBuilder tigaAwal2 = new StringBuilder(tigaAwal);
        String tigaAwalRev = tigaAwal2.reverse().toString().toUpperCase();

        //3 karakter from hour
        String temp2 = String.valueOf(store.getWaktuBuka()).substring(0,2);
        String temp3 = String.valueOf(store.getWaktuTutup()).substring(0,2);
        String gabungHH = temp2 + temp3;
        Integer hhtemp = Integer.valueOf(gabungHH);
        Integer hhAkhir = Math.floorDiv(hhtemp, 10);
        String hhString = String.valueOf(hhAkhir).toUpperCase();

        //2 karakter random
        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // create random string builder
        StringBuilder sb = new StringBuilder();
        // create an object of Random class
        Random random = new Random();
        // specify length of random string
        int length = 2;
        for(int i = 0; i < length; i++) {
            // generate random index number
            int index = random.nextInt(alphabet.length());
            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);
            // append the character to string builder
            sb.append(randomChar);
        }
        String randomString = sb.toString().toUpperCase();

        String storeCode =  sc + tigaAwalRev + hhString + randomString;
        storeCode = storeCode.toUpperCase();
        System.out.print("INI STORE CODE :");
        System.out.println(storeCode);
        return storeCode;
    }

    @Override
    public StoreModel getStoreByIdToko(Long idToko) {
        Optional<StoreModel> store = storeDb.findByIdToko(idToko);
        if(store.isPresent()){
            return store.get();
        }
        return null;
    }

    @Override
    public boolean updateStore(StoreModel store) {
        LocalTime timeNow = LocalTime.now();

        if(store.getWaktuBuka().isAfter(store.getWaktuTutup())) {
            if(timeNow.isBefore(store.getWaktuBuka()) && timeNow.isAfter(store.getWaktuTutup())) {
                String storeCode = defineStorecode(store);
                store.setNoTeleponToko(storeCode);
                storeDb.save(store);
                return true;
            } else {
                return false;
            }
        } else {
            if(timeNow.isAfter(store.getWaktuTutup()) || timeNow.isBefore(store.getWaktuBuka())) {
                String storeCode = defineStorecode(store);
                store.setNoTeleponToko(storeCode);
                storeDb.save(store);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean deleteStore(StoreModel store) {
        LocalTime timeNow = LocalTime.now();

        if(store.getWaktuBuka().isAfter(store.getWaktuTutup())) {
            if(timeNow.isBefore(store.getWaktuBuka()) && timeNow.isAfter(store.getWaktuTutup())) {
                if(store.getListStoreBobatea().isEmpty()) {
                    storeDb.delete(store);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            if(timeNow.isAfter(store.getWaktuTutup()) || timeNow.isBefore(store.getWaktuBuka())) {
                if(store.getListStoreBobatea().isEmpty()) {
                    storeDb.delete(store);
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }




}
