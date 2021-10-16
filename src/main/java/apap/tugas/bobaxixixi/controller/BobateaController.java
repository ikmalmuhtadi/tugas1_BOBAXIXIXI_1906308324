package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.service.*;
import apap.tugas.bobaxixixi.model.*;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import java.time.LocalTime;

@Controller
public class BobateaController {
    @Qualifier("bobateaServiceImpl")
    @Autowired
    private BobateaService bobateaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @Qualifier("storeBobateaServiceImpl")
    @Autowired
    private StoreBobateaService storeBobateaService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @GetMapping("/boba")
    public String listBobatea(
//            @RequestParam(value = "idBobatea") Long idBobatea,
            Model model){
        List<BobateaModel> listBobatea = bobateaService.getBobateaList();
//        BobateaModel bobatea2 = bobateaService.getBobateaByIdBobatea(idBobatea);

        model.addAttribute("listBobatea", listBobatea);
//        model.addAttribute("bobatea2", bobatea2);
        return "viewall-bobatea";
    }

    @GetMapping("/boba/add")
    public String addBobateaForm(Model model){
        List<ToppingModel> listTopping = toppingService.getListTopping();
        model.addAttribute("bobatea", new BobateaModel());
        model.addAttribute("listTopping", listTopping);
        return "form-add-bobatea";
    }

    @PostMapping("/boba/add")
    public String addBobateaSubmit(
            @ModelAttribute BobateaModel bobatea,
            Model model
    ){
        bobateaService.addBobatea(bobatea);
        model.addAttribute("namaVarian", bobatea.getNamaVarian());
        return "add-bobatea";
    }

    @GetMapping("/boba/update/{idBobatea}")
    public String updateBobateaForm(
            @PathVariable Long idBobatea,
            Model model
    ) {
        List<ToppingModel> listTopping = toppingService.getListTopping();
        BobateaModel bobatea = bobateaService.getBobateaByIdBobatea(idBobatea);

        model.addAttribute( "bobatea", bobatea);
        model.addAttribute("listTopping", listTopping);
        return "form-update-bobatea" ;
    }

    @PostMapping("/boba/update/{idBobatea}")
    public String updateBobateaSubmit(
            @PathVariable Long idBobatea,
            @ModelAttribute BobateaModel bobatea,
            Model model
    ) {
        List<StoreModel> temp = storeBobateaService.getListStoreByBoba(bobatea);
        for(StoreModel x : temp) {
            LocalTime timeNow = LocalTime.now();
            LocalTime tempWaktuBukaStore = x.getWaktuBuka();
            LocalTime tempWaktuTutupStore = x.getWaktuTutup();
            System.out.print("INI WAKTU SEKARANG : ");
            System.out.println(timeNow);
            System.out.print("INI WAKTU BUKA : ");
            System.out.println(tempWaktuBukaStore);
            System.out.print("INI WAKTU TUTUP : ");
            System.out.println(tempWaktuTutupStore);
            if(tempWaktuBukaStore.isAfter(tempWaktuTutupStore)) {
                if(timeNow.isBefore(tempWaktuBukaStore) && timeNow.isAfter(tempWaktuTutupStore)) {
                    List<ToppingModel> listTopping = toppingService.getListTopping();
                    bobateaService.updateBobatea(bobatea);
                } else {
                    model.addAttribute("namaVarian", bobatea.getNamaVarian());
                    return "error-update-bobatea";
                }
            } else {
                if(timeNow.isAfter(tempWaktuTutupStore) || timeNow.isBefore(tempWaktuBukaStore)) {
                    List<ToppingModel> listTopping = toppingService.getListTopping();
                    bobateaService.updateBobatea(bobatea);
                } else {
                    model.addAttribute("namaVarian", bobatea.getNamaVarian());
                    return "error-update-bobatea";
                }
            }
        }

        model.addAttribute("bobatea", bobatea);
        model.addAttribute("namaVarian", bobatea.getNamaVarian());
        return "update-bobatea";
    }

    @GetMapping("/boba/delete/{idBobatea}")
    public String deleteStore(
            @PathVariable Long idBobatea,
            Model model
    ){
        model.addAttribute("namaVarian", bobateaService.getBobateaByIdBobatea(idBobatea).getNamaVarian());
        boolean flagDelete = bobateaService.deleteBobatea(bobateaService.getBobateaByIdBobatea(idBobatea));

        if(flagDelete == true) {
            model.addAttribute("namaVarian", bobateaService.getBobateaByIdBobatea(idBobatea).getNamaVarian());
            return "delete-bobatea";
        }
        return "error-delete-bobatea";


//        BobateaModel bobatea = bobateaService.getBobateaByIdBobatea(idBobatea);
//        model.addAttribute("namaVarian", bobatea.getNamaVarian());
//        bobateaService.deleteBobatea(bobatea);
//        return "delete-bobatea";
    }

    @GetMapping("/boba/{idBobatea}/assign-store")
    public String assignStoreToBobateaForm(
            @PathVariable Long idBobatea,
            Model model
    ){

//        List<StoreModel> temp = storeBobateaService.getListStoreByBoba(bobateaService.getBobateaByIdBobatea(idBobatea));
//        for(StoreModel x : temp) {
//            if()
//        }

        BobateaModel bobatea = bobateaService.getBobateaByIdBobatea(idBobatea);
        List<StoreModel> listStore = storeService.getStoreList();
        model.addAttribute("namaVarian", bobatea.getNamaVarian());
        model.addAttribute("storebobatea", new StoreBobateaModel());
        model.addAttribute("listStore", listStore);
        model.addAttribute("idBobatea", idBobatea);
        return "assign-store-to-bobatea";
    }

    @PostMapping("/boba/{idBobatea}/assign-store")
    public String assignStoreToBobateaSubmit(
            @ModelAttribute StoreBobateaModel storebobatea,
            @PathVariable Long idBobatea,
            @RequestParam(value = "listIdToko") ArrayList<Integer> listIdToko,
            Model model
    ){
        BobateaModel bobatea = bobateaService.getBobateaByIdBobatea(idBobatea);
        List<StoreBobateaModel> temp = storeBobateaService.getStoreBobateaByBobatea(bobatea);
        for (StoreBobateaModel x : temp) {
            storeBobateaService.deleteStoreBobatea(x);
        }

        for(int i = 0; i < listIdToko.size(); i++) {
            StoreBobateaModel storeBobatea = new StoreBobateaModel();
            storeBobatea.setBobatea(bobateaService.getBobateaByIdBobatea(idBobatea));
            storeBobatea.setStore(storeService.getStoreByIdToko(Long.valueOf(listIdToko.get(i))));
            storeBobatea.setKodeProduksi(storeBobateaService.defineProductioncode(storeBobatea));

            storeBobateaService.addStoreBobatea(storeBobatea);

        }
        List<StoreModel> listStore = storeBobateaService.getListStoreByBoba(bobatea);
        model.addAttribute("storebobatea", storebobatea);
        model.addAttribute("listStore", listStore);
        model.addAttribute("bobatea", bobatea);
        model.addAttribute("namaVarian" ,bobatea.getNamaVarian());
        model.addAttribute("idBobatea", idBobatea);
        return "assign-store-to-bobatea-success";
    }


    @GetMapping("/search")
    public String searchBobateaBasedonToppingForm (
            @RequestParam(value = "bobaName", required = false) String namaVarian,
            @RequestParam(value = "topping", required = false) String namaTopping,
            Model model) {

        List<ToppingModel> listTopping = toppingService.getListTopping();
        List<BobateaModel> listBobateaname = bobateaService.getBobateaList();
        List<StoreBobateaModel> temp = bobateaService.getBobateaBasedonTopping(namaVarian, namaTopping);
        List<BobateaModel> listHasilBoba = new ArrayList<>();
        for (StoreBobateaModel x : temp){
            listHasilBoba.add(x.getBobatea());
        }
        List<String> listHasilStore = new ArrayList<>();
        for (StoreBobateaModel i : temp) {
            listHasilStore.add(i.getStore().getNamaToko());
        }
        model.addAttribute("listHasilStore", listHasilStore);
        model.addAttribute("listHasilBoba", listHasilBoba);
        model.addAttribute("listStorebobatea", temp);
        model.addAttribute("listBobateaname", listBobateaname);
        model.addAttribute("listTopping", listTopping);
        return"search-bobatea-basedon-topping";
    }

    @GetMapping("/filter/manager")
    public String searchBobateaBasedonToppingForm (
            @RequestParam(value = "bobaName", required = false) String namaVarian,
            Model model) {


        List<BobateaModel> listBobateaname = bobateaService.getBobateaList();
        List<StoreBobateaModel> temp = bobateaService.getFilterManagerbyBoba(namaVarian);
        List<ManagerModel> listHasilManager = new ArrayList<>();
        for (StoreBobateaModel x : temp){
            listHasilManager.add(x.getStore().getManager());
        }


        model.addAttribute("listHasilManager", listHasilManager);
        model.addAttribute("listStorebobatea", temp);
        model.addAttribute("listBobateaname", listBobateaname);

        return"filter-manager-by-boba";
    }

}
