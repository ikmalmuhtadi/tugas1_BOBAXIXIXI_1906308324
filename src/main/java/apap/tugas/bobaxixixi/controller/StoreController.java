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
public class StoreController {
    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @Qualifier("bobateaServiceImpl")
    @Autowired
    private BobateaService bobateaService;

    @Qualifier("storeBobateaServiceImpl")
    @Autowired
    private StoreBobateaService storeBobateaService;

    @GetMapping("/store")
    public String listStore(Model model){
        List<StoreModel> listStore = storeService.getStoreList();
        model.addAttribute("listStore", listStore);
        return "viewall-store";
    }

    @GetMapping("/store/add")
    public String addStoreForm(Model model){
        List<ManagerModel> listManager = managerService.getListManager();
        model.addAttribute("store", new StoreModel());
        model.addAttribute("listManager", listManager);
        return "form-add-store";
    }

    @PostMapping("/store/add")
    public String addStoreForm(
            @ModelAttribute StoreModel store,
            Model model
    ){
        boolean flagAdd = storeService.addStore(store);
        if(flagAdd == true ) {
            String storeCode = storeService.defineStorecode(store);
            store.setNoTeleponToko(storeCode);
            storeService.addStore(store);
            model.addAttribute("namaToko", store.getNamaToko());
            model.addAttribute("storeCode", store.getNoTeleponToko());
            return "add-store";
        }
//        storeService.addStore(store);
//        model.addAttribute("namaToko", store.getNamaToko());
//        model.addAttribute("storeCode", store.getNoTeleponToko());
//        return "add-store";

        return "error-add-store";
    }

    @GetMapping("/store/{idToko}")
    public String viewDetailToko(
            @PathVariable Long idToko,
            Model model
    ) {
        StoreModel store = storeService.getStoreByIdToko(idToko);
        List<StoreBobateaModel> listTemp = storeBobateaService.getStoreBobateaByStore(storeService.getStoreByIdToko(idToko));
        List<BobateaModel> listBoba = new ArrayList<>();
        for(StoreBobateaModel i : listTemp){
            listBoba.add((i.getBobatea()));
        }
        model.addAttribute("listStoreBobatea" ,listBoba);
        model.addAttribute("store", store);

        return "view-store";
    }

    @GetMapping("/store/update/{idToko}")
    public String updateStoreForm(
            @PathVariable Long idToko,
            Model model
    ) {
        List<ManagerModel> listManager = managerService.getListManager();
        StoreModel store = storeService.getStoreByIdToko(idToko);

        model.addAttribute( "store", store);
        model.addAttribute("listManager", listManager);
        return "form-update-store" ;
    }
    @PostMapping("/store/update/{idToko}")
    public String updateStoreSubmit(
            @ModelAttribute StoreModel store,
            @PathVariable Long idToko,
            Model model
    ) {
        boolean flagUpdate = storeService.updateStore(store);

        if(flagUpdate == true) {
            model.addAttribute("namaToko", store.getNamaToko());
            model.addAttribute("storeCode", store.getNoTeleponToko());
            return "update-store";
        } else {
            model.addAttribute("namaToko", store.getNamaToko());
            return "error-update-store";
        }
    }

    @GetMapping("/store/delete/{idToko}")
    public String deleteStore(
            @PathVariable Long idToko,
            Model model
    ){
        StoreModel store = storeService.getStoreByIdToko(idToko);
        boolean flagDelete = storeService.deleteStore(store);

        if(flagDelete == true) {
            model.addAttribute("namaToko", store.getNamaToko());
            return "delete-store";
        }
        model.addAttribute("namaToko", store.getNamaToko());
        return "error-delete-store";
    }

    @GetMapping("/store/{idToko}/assign-boba")
    public String assignBobateaToStoreForm(
            @PathVariable Long idToko,
            Model model
    ){
        StoreModel store = storeService.getStoreByIdToko(idToko);
        List<BobateaModel> listBobatea = bobateaService.getBobateaList();
        model.addAttribute("namaToko", store.getNamaToko());
        model.addAttribute("storebobatea", new StoreBobateaModel());
        model.addAttribute("listBobatea", listBobatea);
        model.addAttribute("idToko", idToko);
        return "assign-bobatea-to-store";
    }

    @PostMapping("/store/{idToko}/assign-boba")
    public String assignBobateaToStoreSubmit(
            @ModelAttribute StoreBobateaModel storebobatea,
            @PathVariable Long idToko,
            @RequestParam(value = "listIdBobatea") ArrayList<Integer> listIdBobatea,
            Model model
    ){
        StoreModel store = storeService.getStoreByIdToko(idToko);
        List<StoreBobateaModel> temp = storeBobateaService.getStoreBobateaByStore(store);
        for (StoreBobateaModel x : temp) {
            storeBobateaService.deleteStoreBobatea(x);
        }

        for(int i = 0; i < listIdBobatea.size(); i++) {
            StoreBobateaModel storeBobatea = new StoreBobateaModel();
            storeBobatea.setStore(storeService.getStoreByIdToko(idToko));
            storeBobatea.setBobatea(bobateaService.getBobateaByIdBobatea(Long.valueOf(listIdBobatea.get(i))));
            storeBobatea.setKodeProduksi(storeBobateaService.defineProductioncode(storeBobatea));

            storeBobateaService.addStoreBobatea(storeBobatea);

        }
        List<BobateaModel> listBoba = storeBobateaService.getListBobaByStore(store);
        model.addAttribute("storebobatea", storebobatea);
        model.addAttribute("listBobatea", listBoba);
        model.addAttribute("store", store);
        model.addAttribute("namaToko" ,store.getNamaToko());
        model.addAttribute("idToko", idToko);
        return "assign-bobatea-to-store-success";
    }

}
