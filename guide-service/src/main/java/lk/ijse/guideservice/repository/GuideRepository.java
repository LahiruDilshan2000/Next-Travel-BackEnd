package lk.ijse.guideservice.repository;

import lk.ijse.guideservice.entity.Guide;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:35 AM on 10/7/2023
 * @project nexttravel
 **/
@Repository
public interface GuideRepository extends MongoRepository<Guide, Integer> {

    /*@Query(value = "from Guide g")
    List<Guide> getGuideHQLWithPageable(Pageable pageable);

    @Query(value = "select g from Guide g where g.name like ?1 " +
            "or g.address like ?2 " +
            "or g.gender like ?3 " +
            "or g.contact like ?4 ")
    List<Guide> searchByText(String t1, String t2, String t3, String t4);*/

    @Query("{}")
    Optional<Guide> findLastInsertedData();

    @Query("{'$or':[ {'name': {$regex : ?0, $options: 'i'}}, {'address': {$regex : ?1, $options: 'i'}}, {'gender': {$regex : ?2, $options: 'i'}}, {'contact': {$regex : ?3, $options: 'i'}} ]}")
    List<Guide> searchByText(@Param("name") String name,
                                          @Param("address") String address,
                                          @Param("gender") String gender,
                                          @Param("contact") String contact);
}
