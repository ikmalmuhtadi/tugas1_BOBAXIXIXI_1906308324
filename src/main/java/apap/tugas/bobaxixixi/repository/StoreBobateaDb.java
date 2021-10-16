package apap.tugas.bobaxixixi.repository;

import java.util.Optional;

import apap.tugas.bobaxixixi.model.BobateaModel;
import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import apap.tugas.bobaxixixi.model.StoreBobateaModel;
import java.util.List;

@Repository
public interface StoreBobateaDb extends JpaRepository<StoreBobateaModel, Long>{
    List<StoreBobateaModel> findAllBobateaStoreByBobatea(BobateaModel bobatea);
    List<StoreBobateaModel> findAllBobateaStoreByStore(StoreModel store);

    @Modifying
    @Query(
            value = "SELECT BT.price_bobatea, BT.size, BT.ice_level, BT.sugar_level, S.store_name, SBT.id_store_bobatea, SBT.id_bobatea, SBT.id_Toko, SBT.production_code\n" +
                    "FROM store_bobatea as SBT, bobatea as BT, store as S, topping as T\n" +
                    "WHERE SBT.id_toko = S.id_toko AND SBT.id_bobatea = BT.id_bobatea AND T.id_topping = BT.topping_id_topping\n" +
                    "AND BT.name_bobatea = (:bobaName) AND T.name_topping = (:namaTopping);",
            nativeQuery = true
    )
    List<StoreBobateaModel> searchBobateaBasedonTopping (@Param("bobaName") String bobaName, @Param("namaTopping") String namaTopping);

    @Modifying
    @Query(
            value = "select distinct * from manager, store,bobatea,store_bobatea " +
                    "WHERE manager.id_manager=store.id_manager AND bobatea.name_bobatea = (:bobaName)" +
                    "AND bobatea.id_bobatea=store_bobatea.id_bobatea AND store.id_toko=store_bobatea.id_toko;",
            nativeQuery = true
    )
    List<StoreBobateaModel> filterManagerByBoba (@Param("bobaName") String bobaName);


}
