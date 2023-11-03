package lk.ijse.vehicleservice.repository;

import lk.ijse.vehicleservice.entity.Vehicle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:36 AM on 10/7/2023
 * @project nexttravel
 **/
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query(value = "from Vehicle v")
    List<Vehicle> getVehicleHQLWithPageable(Pageable pageable);

    //@Query(value = "select v from Vehicle v where v.vehicleCategory = ?1")
    List<Vehicle> searchAllByVehicleCategory(String category, Pageable pageable);

    List<Vehicle> searchAllBySeatCapacity(Integer seatCapacity, Pageable pageable);

    @Query(value = "select v from Vehicle v where v.fuelAndTransmissionType = ?1")
    List<Vehicle> getVehicleByFuelAndTransmissionType(String fuelAndTransmissionType, Pageable pageable);

    @Query(value = "select v from Vehicle v where v.seatCapacity = ?1 and v.fuelAndTransmissionType = ?2")
    List<Vehicle> getVehicleBySeatAndFuelAndTransmissionType(Integer seatCapacity, String fuelAndTransmissionType, Pageable pageable);

}
