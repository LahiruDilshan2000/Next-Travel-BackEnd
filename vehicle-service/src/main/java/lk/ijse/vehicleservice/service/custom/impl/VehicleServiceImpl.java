package lk.ijse.vehicleservice.service.custom.impl;

import lk.ijse.vehicleservice.dto.VehicleDTO;
import lk.ijse.vehicleservice.entity.Vehicle;
import lk.ijse.vehicleservice.exception.NotFoundException;
import lk.ijse.vehicleservice.repository.VehicleRepository;
import lk.ijse.vehicleservice.service.custom.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:33 AM on 10/7/2023
 * @project nexttravel
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final ModelMapper modelMapper;

    private final String mainPath = "C:\\Images\\Vehicle\\";

    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO, List<MultipartFile> imageList) throws IOException {

        if (imageList.size() != 7)
            throw new NotFoundException("Vehicle All images not found !");

        String folderPath = mainPath + UUID.randomUUID();
        File file = new File(folderPath);

        if (!file.mkdir())
            throw new RuntimeException("Vehicle Image directory create failed !");


        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);

        vehicle.setFolderLocation(folderPath);
        vehicle.setVehicleImg1(saveAndGetPath(folderPath, "1", imageList.get(0)));
        vehicle.setVehicleImg2(saveAndGetPath(folderPath, "2", imageList.get(1)));
        vehicle.setVehicleImg3(saveAndGetPath(folderPath, "3", imageList.get(2)));
        vehicle.setVehicleImg4(saveAndGetPath(folderPath, "4", imageList.get(3)));
        vehicle.setVehicleImg5(saveAndGetPath(folderPath, "5", imageList.get(4)));
        vehicle.setDrivingLicenseImg1(saveAndGetPath(folderPath, "6", imageList.get(5)));
        vehicle.setDrivingLicenseImg2(saveAndGetPath(folderPath, "7", imageList.get(6)));

        return modelMapper.map(vehicleRepository.save(vehicle), VehicleDTO.class);
    }

    private String saveAndGetPath(String folderPath, String name,  MultipartFile file) throws IOException {

        String imgPath = folderPath + "\\" + name + file.getOriginalFilename();
        file.transferTo(Paths.get(imgPath));
        return imgPath;
    }

    @Override
    public VehicleDTO updateVehicle(VehicleDTO vehicleDTO, List<MultipartFile> imageList) throws IOException {

        if (!vehicleRepository.existsById(vehicleDTO.getVehicleId()))
            throw new NotFoundException(vehicleDTO.getVehicleId() + " Vehicle doesn't exists !");

        Vehicle exitVehicle = vehicleRepository.findById(vehicleDTO.getVehicleId()).get();

        deleteExistingImg(exitVehicle.getVehicleImg1());
        deleteExistingImg(exitVehicle.getVehicleImg2());
        deleteExistingImg(exitVehicle.getVehicleImg3());
        deleteExistingImg(exitVehicle.getVehicleImg4());
        deleteExistingImg(exitVehicle.getVehicleImg5());
        deleteExistingImg(exitVehicle.getDrivingLicenseImg1());
        deleteExistingImg(exitVehicle.getDrivingLicenseImg2());

        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);

        vehicle.setFolderLocation(exitVehicle.getFolderLocation());
        vehicle.setVehicleImg1(saveAndGetPath(exitVehicle.getFolderLocation(), "1", imageList.get(0)));
        vehicle.setVehicleImg2(saveAndGetPath(exitVehicle.getFolderLocation(), "2", imageList.get(1)));
        vehicle.setVehicleImg3(saveAndGetPath(exitVehicle.getFolderLocation(), "3", imageList.get(2)));
        vehicle.setVehicleImg4(saveAndGetPath(exitVehicle.getFolderLocation(), "4", imageList.get(3)));
        vehicle.setVehicleImg5(saveAndGetPath(exitVehicle.getFolderLocation(), "5", imageList.get(4)));
        vehicle.setDrivingLicenseImg1(saveAndGetPath(exitVehicle.getFolderLocation(), "6", imageList.get(5)));
        vehicle.setDrivingLicenseImg2(saveAndGetPath(exitVehicle.getFolderLocation(), "7", imageList.get(6)));

        return modelMapper.map(vehicleRepository.save(vehicle), VehicleDTO.class);

    }

    private void deleteExistingImg(String imagePath) {

        File oldFile = new File(imagePath);

        if (!oldFile.exists()) {
            throw new NotFoundException("Existing vehicle image is not found !");
        }

        if (!oldFile.delete())
            throw new RuntimeException("Existing vehicle image delete failed !");
    }


    @Override
    public void deleteVehicle(Integer vehicleId) {

        if (!vehicleRepository.existsById(vehicleId))
            throw new NotFoundException(vehicleId + " Vehicle doesn't exists !");

        Vehicle vehicle = vehicleRepository.findById(vehicleId).get();

        deleteExistingImg(vehicle.getVehicleImg1());
        deleteExistingImg(vehicle.getVehicleImg2());
        deleteExistingImg(vehicle.getVehicleImg3());
        deleteExistingImg(vehicle.getVehicleImg4());
        deleteExistingImg(vehicle.getVehicleImg5());
        deleteExistingImg(vehicle.getDrivingLicenseImg1());
        deleteExistingImg(vehicle.getDrivingLicenseImg2());

        File folder = new File(vehicle.getFolderLocation());

        if (!folder.delete())
            throw new RuntimeException("Vehicle Image directory delete failed !");

        vehicleRepository.deleteById(vehicleId);
    }

    @Override
    public List<VehicleDTO> getPageableHotels(Integer page, Integer count) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return vehicleRepository
                .getVehicleHQLWithPageable(pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public List<VehicleDTO> getVehiclesWithCategory(Integer page,
                                                    Integer count,
                                                    String category) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return vehicleRepository
                .searchAllByVehicleCategory(category, pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public List<VehicleDTO> getVehiclesWithSeatCapacity(Integer page,
                                                        Integer count,
                                                        Integer seatCapacity) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return vehicleRepository
                .searchAllBySeatCapacity(seatCapacity, pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public List<VehicleDTO> getVehiclesWithFuelAndTransmission(Integer page,
                                                               Integer count,
                                                               String fuelAndTrans) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return vehicleRepository
                .getVehicleByFuelAndTransmissionType(fuelAndTrans, pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public List<VehicleDTO> getVehiclesBySeatAndFuelWithTransmission(Integer page,
                                                                     Integer count,
                                                                     Integer seatCapacity,
                                                                     String fuelAndTrans) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return vehicleRepository
                .getVehicleBySeatAndFuelAndTransmissionType(seatCapacity, fuelAndTrans, pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public VehicleDTO findById(Integer vehicleId) {

        if (!vehicleRepository.existsById(vehicleId))
            throw new NotFoundException(vehicleId + " Vehicle doesn't exist !");

        return getDTO(vehicleRepository.findById(vehicleId).get());
    }

    private VehicleDTO getDTO(Vehicle vehicle) {

        VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);

        try {

            byte[] vehicleImg1 = Files.readAllBytes(new File(vehicle.getVehicleImg1()).toPath());
            byte[] vehicleImg2 = Files.readAllBytes(new File(vehicle.getVehicleImg2()).toPath());
            byte[] vehicleImg3 = Files.readAllBytes(new File(vehicle.getVehicleImg3()).toPath());
            byte[] vehicleImg4 = Files.readAllBytes(new File(vehicle.getVehicleImg4()).toPath());
            byte[] vehicleImg5 = Files.readAllBytes(new File(vehicle.getVehicleImg5()).toPath());

            if (vehicleImg1.length == 0 ||
                    vehicleImg2.length == 0 ||
                    vehicleImg3.length == 0 ||
                    vehicleImg4.length == 0 ||
                    vehicleImg5.length == 0)
                throw new NotFoundException(vehicle.getVehicleId() + " Vehicle images not found !");

            byte[] drivingLicenseImg1 = Files.readAllBytes(new File(vehicle.getDrivingLicenseImg1()).toPath());
            byte[] drivingLicenseImg2 = Files.readAllBytes(new File(vehicle.getDrivingLicenseImg2()).toPath());

            if (drivingLicenseImg1.length == 0 || drivingLicenseImg2.length == 0)
                throw new NotFoundException(vehicle.getVehicleId() + " Driving license images not found !");

            List<byte[]> imageList = new ArrayList<>();
            imageList.add(vehicleImg1);
            imageList.add(vehicleImg2);
            imageList.add(vehicleImg3);
            imageList.add(vehicleImg4);
            imageList.add(vehicleImg5);
            imageList.add(drivingLicenseImg1);
            imageList.add(drivingLicenseImg2);

            vehicleDTO.setImageList(imageList);

            return vehicleDTO;

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
