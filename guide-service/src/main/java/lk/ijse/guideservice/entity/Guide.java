package lk.ijse.guideservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:26 AM on 10/7/2023
 * @project nexttravel
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@Entity
//@Table(name = "_guide")
public class Guide implements SuperEntity{

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guideId;

    ///@Column(nullable = false)
    private String name;

    //@Column(nullable = false)
    private String address;

   // @Column(nullable = false)
    private Integer age;

    //@Column(nullable = false)
    private String gender;

    //@Column(nullable = false)
    private String contact;

    //@Column(nullable = false)
    private Double manDayValue;

    //@Column(nullable = false)
    private String guideImage;

    //@Column(nullable = false)
    private String folderLocation;

    //@Column(nullable = false, name = "nice_img_1")
    private String nicImage1;

    //@Column(nullable = false, name = "nice_img_2")
    private String nicImage2;

    //@Column(nullable = false, name = "guid_id_img_1")
    private String guideIdImage1;

    //@Column(nullable = false, name = "guid_id_img_2")
    private String guideIdImage2;

}
