package lk.ijse.packageservice.service.custom;


import lk.ijse.packageservice.dto.GuideDTO;
import lk.ijse.packageservice.dto.PackageDTO;
import lk.ijse.packageservice.dto.PaymentDetailsDTO;
import lk.ijse.packageservice.service.SuperService;
import lk.ijse.packageservice.util.ResponseUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:31 AM on 10/7/2023
 * @project nexttravel
 **/
public interface PackageService extends SuperService {

    String getNextPk();
    PackageDTO savePackage(PackageDTO packageDTO);

    PackageDTO updatePackage(PackageDTO packageDTO);

    PackageDTO updateNotExpirePackage(PackageDTO packageDTO);

    void deletePackage(String packageId);

    List<PackageDTO> getPageablePackage(Integer page, Integer count);

    PackageDTO getById(String packageId);

    ResponseUtil getVehicles(Integer page, Integer count, String category);

    ResponseUtil getHotels(Integer page, Integer count, Integer category);

    ResponseUtil getHotelById(Integer hotelId);

    ResponseUtil getVehicleById(Integer vehicleId);

    ResponseUtil getGuideById(Integer guideId);

    ResponseUtil getAllUsers(String header);

    ResponseUtil getUserByNic(String nic, String token);

    List<GuideDTO> getFreeGuides(String startDate, String endDate);

    List<PackageDTO> getPackageByUserNic(Integer page, Integer count, String nic);

    void addPaymentSlip(String packageId, MultipartFile file);

    void confirmPayment(String packageId);

    void rejectPayment(String packageId);

    List<PaymentDetailsDTO> getPending();

    PaymentDetailsDTO getPendingPaymentByPackageId(String packageId);

    List<PackageDTO> getAllPackages();

    List<PackageDTO> searchByText(String text);
}
