package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.Store;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OnDelete;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "manager")
public class ManagerModel implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idManager;

    @NotNull
    @Size(max=255)
    @Column(name="name_manager", nullable = false)
    private String namaManager;

    @NotNull
    @Column(name = "gender", nullable = false)
    private Integer jenisKelamin;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalLahirManager;

    // Relasi dengan StoreModel
    @OneToOne(mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StoreModel store;


}
