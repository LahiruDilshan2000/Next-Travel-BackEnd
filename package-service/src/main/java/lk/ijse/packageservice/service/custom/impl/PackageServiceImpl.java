package lk.ijse.packageservice.service.custom.impl;

import lk.ijse.packageservice.dto.GuideDTO;
import lk.ijse.packageservice.dto.PackageDTO;
import lk.ijse.packageservice.dto.PaymentDetailsDTO;
import lk.ijse.packageservice.entity.Package;
import lk.ijse.packageservice.entity.PaymentDetails;
import lk.ijse.packageservice.exception.NotFoundException;
import lk.ijse.packageservice.feign.Guide;
import lk.ijse.packageservice.feign.Hotel;
import lk.ijse.packageservice.feign.User;
import lk.ijse.packageservice.feign.Vehicle;
import lk.ijse.packageservice.repository.PackageRepository;
import lk.ijse.packageservice.repository.PaymentDetailsRepository;
import lk.ijse.packageservice.service.custom.PackageService;
import lk.ijse.packageservice.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:33 AM on 10/7/2023
 * @project nexttravel
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    private final Vehicle vehicle;

    private final Hotel hotel;

    private final Guide guide;

    private final User user;

    private final ModelMapper modelMapper;

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final String mainPath = "C:\\Images\\Package payment\\";

    @Override
    public String getNextPk() {

        Optional<Package> isExistId = packageRepository.getLastId();

        if (isExistId.isPresent()) {

            String[] split = isExistId.get().getPackageId().split("[-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            return String.format("MD-%08d", lastDigits);
        } else {
            return "MD-00000001";
        }
    }

    @Override
    public PackageDTO savePackage(PackageDTO packageDTO) {

        if (packageRepository.existsById(packageDTO.getPackageId()))
            throw new NotFoundException(packageDTO.getPackageId() + " Package already exist");

        packageDTO.setPaidValue("Not payed");
        PackageDTO dto = modelMapper.map(packageRepository.save(modelMapper.map(packageDTO, Package.class)), PackageDTO.class);

        paymentDetailsRepository.save(PaymentDetails
                .builder()
                .packageId(packageDTO.getPackageId())
                .status("Not payed")
                .packageValue(packageDTO.getPackageValue())
                .paidValue(0.0)
                .build());
        return dto;
    }

    @Override
    public PackageDTO updatePackage(PackageDTO packageDTO) {

        if (!packageRepository.existsById(packageDTO.getPackageId()))
            throw new NotFoundException(packageDTO.getPackageId() + " Package doesn't exist");

        return modelMapper
                .map(packageRepository.save(modelMapper.map(packageDTO, Package.class)), PackageDTO.class);
    }

    @Override
    public PackageDTO updateNotExpirePackage(PackageDTO packageDTO) {

        if (isUpdateTimeExpire(packageDTO.getBookedDate()))
            throw new RuntimeException("Package update time is expire !");

        return updatePackage(packageDTO);
    }

    private boolean isUpdateTimeExpire(Date bookedDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookedDate);

        calendar.add(Calendar.HOUR_OF_DAY, 48);
        Date expireDate = calendar.getTime();

        Date nowDate = new Date();

        return nowDate.toInstant().isAfter(expireDate.toInstant());
    }

    @Override
    public void deletePackage(String packageId) {

        if (!packageRepository.existsById(packageId))
            throw new NotFoundException(packageId + " Package doesn't exist");

        PaymentDetails paymentDetails = paymentDetailsRepository.findByPackageId(packageId).get();

        if (paymentDetails.getFolderPath() != null){

            deleteExistingImg(paymentDetails.getSliImgPath());

            File folder = new File(paymentDetails.getFolderPath());

            if (!folder.delete())
                throw new RuntimeException("Payment slip directory delete failed !");
        }

        packageRepository.deleteById(packageId);
        paymentDetailsRepository.deleteByPackageId(packageId);
    }

    private void deleteExistingImg(String imagePath) {

        File oldFile = new File(imagePath);

        if (!oldFile.exists()) {
            throw new NotFoundException("Existing payment slip is not found !");
        }

        if (!oldFile.delete())
            throw new RuntimeException("Existing payment slip delete failed !");
    }

    @Override
    public List<PackageDTO> getPageablePackage(Integer page, Integer count) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return modelMapper
                .map(packageRepository.getPackageHQLWithPageable(pageRequest),
                        new TypeToken<List<PackageDTO>>() {
                        }.getType());
    }

    @Override
    public PackageDTO getById(String packageId) {

        if (!packageRepository.existsById(packageId))
            throw new NotFoundException(packageId + " Package doesn't exist");

        return modelMapper
                .map(packageRepository.findById(packageId).get(), PackageDTO.class);
    }

    @Override
    public ResponseUtil getVehicles(Integer page, Integer count, String category) {

        return vehicle.getVehicleWithCategory(page, count, category);
    }

    @Override
    public ResponseUtil getHotels(Integer page, Integer count, Integer category) {

        return hotel.getHotelWithCategory(page, count, category);
    }

    @Override
    public ResponseUtil getHotelById(Integer hotelId) {

        return hotel.getHotelById(hotelId);
    }

    @Override
    public ResponseUtil getVehicleById(Integer vehicleId) {

        return vehicle.getVehicleById(vehicleId);
    }

    @Override
    public ResponseUtil getAllUsers(String token) {

        return user.getAll(token);
    }

    @Override
    public ResponseUtil getUserByNic(String nic, String token) {

        return user.findByNic(token, nic);
    }

    @Override
    public ResponseUtil getGuideById(Integer guideId) {

        return guide.getGuideById(guideId);
    }

    @Override
    public List<GuideDTO> getFreeGuides(String startDate, String endDaten) {

        return guide.getAll();

        /*List<GuideDTO> freeList = new ArrayList<>();

        all.forEach(guideDTO -> {
            if (guideDTO.getDatesList().isEmpty()) {
                freeList.add(guideDTO);

            } else if (isFree(guideDTO.getDatesList(), startDate, endDate)) {
                freeList.add(guideDTO);
            }

        });
        return freeList;*/
    }

    @Override
    public List<PackageDTO> getPackageByUserNic(Integer page, Integer count, String nic) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return modelMapper
                .map(packageRepository.findAllByUserNic(nic, pageRequest),
                        new TypeToken<List<PackageDTO>>() {
                        }.getType());

    }

    @Override
    public void addPaymentSlip(String packageId, MultipartFile file) {

        Optional<PaymentDetails> byPackageId = paymentDetailsRepository.findByPackageId(packageId);
        if (byPackageId.isEmpty())
            throw new NotFoundException(packageId + " Package is doesn't exist !");

        try {

            PaymentDetails paymentDetails = byPackageId.get();

            if (paymentDetails.getFolderPath() == null) {

                String folderPath = mainPath + UUID.randomUUID();
                File pathFile = new File(folderPath);
                if (!pathFile.mkdir())
                    throw new RuntimeException("Hotel Image directory create failed !");

                String imagePth = folderPath + "\\" + file.getOriginalFilename();
                file.transferTo(Paths.get(imagePth));

                paymentDetails.setFolderPath(folderPath);
                paymentDetails.setSliImgPath(imagePth);

            } else {

                deleteExistingImg(paymentDetails.getSliImgPath());
                String imagePth = paymentDetails.getFolderPath() + "\\" + file.getOriginalFilename();
                file.transferTo(Paths.get(imagePth));

                paymentDetails.setSliImgPath(imagePth);
            }

            Package aPackage = packageRepository.findByPackageId(packageId).get();
            aPackage.setPaidValue("Pending");
            paymentDetails.setStatus("Pending");
            packageRepository.save(aPackage);
            paymentDetailsRepository.save(paymentDetails);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void confirmPayment(String packageId) {

        Optional<PaymentDetails> byPackageId = paymentDetailsRepository.findByPackageId(packageId);
        if (byPackageId.isEmpty())
            throw new NotFoundException(packageId + " Package is doesn't exist !");

        PaymentDetails paymentDetails = byPackageId.get();
        paymentDetails.setStatus("Payed");

        Package aPackage = packageRepository.findByPackageId(packageId).get();
        aPackage.setPaidValue(String.valueOf(aPackage.getPackageValue()));

        packageRepository.save(aPackage);
        paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public void rejectPayment(String packageId) {

        Optional<PaymentDetails> byPackageId = paymentDetailsRepository.findByPackageId(packageId);
        if (byPackageId.isEmpty())
            throw new NotFoundException(packageId + " Package is doesn't exist !");

        PaymentDetails paymentDetails = byPackageId.get();
        paymentDetails.setStatus("Not Payed");

        Package aPackage = packageRepository.findByPackageId(packageId).get();
        aPackage.setPaidValue("Not Payed");

        packageRepository.save(aPackage);
        paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public List<PaymentDetailsDTO> getPending() {

        return paymentDetailsRepository
                .getAllPayment("Pending")
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public PaymentDetailsDTO getPendingPaymentByPackageId(String packageId) {

        Optional<PaymentDetails> byPackageId = paymentDetailsRepository.findByPackageId(packageId);

        if (byPackageId.isEmpty())
            throw new NotFoundException(packageId + " Package doesn't exist !");

        return getDTO(byPackageId.get());
    }

    @Override
    public List<PackageDTO> getAllPackages() {

        return modelMapper.map(packageRepository.findAll(), new TypeToken<List<PackageDTO>>() {
        }.getType());
    }

    @Override
    public List<PackageDTO> searchByText(String text) {

        text = "%" + text + "%";

        return modelMapper
                .map(packageRepository.searchByText(text, text, text, text, text, text, text, text),
                        new TypeToken<List<PackageDTO>>() {
                        }.getType());
    }

    private PaymentDetailsDTO getDTO(PaymentDetails paymentDetails) {

        PaymentDetailsDTO detailsDTO = modelMapper.map(paymentDetails, PaymentDetailsDTO.class);

        try {

            byte[] slipImg = Files.readAllBytes(new File(paymentDetails.getSliImgPath()).toPath());

            if (slipImg.length == 0)
                throw new NotFoundException(paymentDetails.getPackageId() + " Slip image not found !");

            detailsDTO.setPaymentSlip(slipImg);
            return detailsDTO;

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*private boolean isFree(List<GuideDatesDTO> dateList, String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate sDate = LocalDate.parse(startDate, formatter);
        LocalDate eDate = LocalDate.parse(endDate, formatter);


        for (GuideDatesDTO guideDatesDTO : dateList) {

            LocalDate guideSDate = guideDatesDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate guideEDate = guideDatesDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!sDate.isBefore(guideSDate) && !sDate.isAfter(guideEDate) ||
                    !eDate.isBefore(guideSDate) && !eDate.isAfter(guideEDate) ||
                    !guideSDate.isBefore(sDate) && !guideSDate.isAfter(eDate) ||
                    !guideEDate.isBefore(sDate) && !guideEDate.isAfter(eDate)) {
                return false;
            }
        }
        return true;
    }*/
}
