package lk.ijse.packageservice.repository;

import lk.ijse.packageservice.entity.Package;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:36 AM on 10/7/2023
 * @project nexttravel
 **/
@Repository
public interface PackageRepository extends JpaRepository<Package, String> {

    @Query(value = "select p from Package p order by p.packageId desc limit 1")
    Optional<Package> getLastId();

    @Query(value = "from Package p")
    List<Package> getPackageHQLWithPageable(Pageable pageable);


    List<Package> findAllByUserNic(String nic, Pageable pageable);

    Optional<Package> findByPackageId(String packageId);

    @Query(value = "select p from Package p where p.packageId like ?1 " +
            "or p.packageCategory like ?2 " +
            "or p.userNic like ?3 " +
            "or p.hotelName like ?4 " +
            "or p.contact like ?5 " +
            "or p.email like ?6 " +
            "or p.paidValue like  ?7 " +
            "or p.withPetOrNo like ?8")
    List<Package> searchByText(String t1, String t2, String t3, String t4, String t5, String t6, String t7, String t8);
}
