package lk.ijse.hotelservice.repository;

import lk.ijse.hotelservice.entity.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:35 AM on 10/7/2023
 * @project nexttravel
 **/
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query(value = "from Hotel h")
    List<Hotel> getHotelHQLWithPageable(Pageable pageable);

   // @Query(value = "select * from _hotel where hotelCategory = ?1 or hotelCategory = ?2", nativeQuery = true)
    @Query(value = "select h from Hotel h where h.hotelCategory = ?1 or h.hotelCategory = ?2")
    List<Hotel> getAllByHotelCategory(Integer categoryOne, Integer categoryTwo,  Pageable pageable);

}
