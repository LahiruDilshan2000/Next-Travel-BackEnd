package lk.ijse.financialserivce.service.custom.impl;

import lk.ijse.financialserivce.dto.PackageDTO;
import lk.ijse.financialserivce.feign.Package;
import lk.ijse.financialserivce.service.custom.FinancialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Fri 8:43 AM on 10/27/2023
 * @project financial-serivce
 **/
@Service
@RequiredArgsConstructor
public class FinancialServiceImpl implements FinancialService {

    private final Package aPackage;

    @Override
    public Double getDailyIncome() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<PackageDTO> all = aPackage.getAllPackages();

        double total = 0;

        if (all.isEmpty())
            return total;

        for (PackageDTO packageDTO: all){

            if (today.equals(sdf.format(packageDTO.getBookedDate()))){
                total += packageDTO.getPackageValue();
            }
        }
        return total;
    }

    @Override
    public Double getWeeklyIncome() {

        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        List<PackageDTO> all = aPackage.getAllPackages();

        double total = 0;

        if (all.isEmpty())
            return total;

        for (PackageDTO packageDTO: all){

            LocalDate bookedDate = packageDTO.getBookedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!bookedDate.isBefore(startDate) && !bookedDate.isAfter(endDate)){
                total += packageDTO.getPackageValue();
            }
        }
        return total;
    }

    @Override
    public Double getMonthlyIncome() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = sdf.format(new Date());

        List<PackageDTO> all = aPackage.getAllPackages();

        double total = 0;

        if (all.isEmpty())
            return total;

        for (PackageDTO packageDTO: all){

            if (month.equals(sdf.format(packageDTO.getBookedDate()))){
                total += packageDTO.getPackageValue();
            }
        }
        return total;
    }

    @Override
    public Double getAnnualIncome() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String month = sdf.format(new Date());

        List<PackageDTO> all = aPackage.getAllPackages();

        double total = 0;

        if (all.isEmpty())
            return total;

        for (PackageDTO packageDTO: all){

            if (month.equals(sdf.format(packageDTO.getBookedDate()))){
                total += packageDTO.getPackageValue();
            }
        }
        return total;
    }

    @Override
    public Long getTodaySalesCount() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<PackageDTO> all = aPackage.getAllPackages();

        long count = 0;

        if (all.isEmpty())
            return count;

        for (PackageDTO packageDTO: all){

            if (today.equals(sdf.format(packageDTO.getBookedDate()))){
                count ++;
            }
        }
        return count;
    }

    @Override
    public List<Double> getPastDayIncomes() {

        List<Double> totalList = new ArrayList<>(7);

        List<PackageDTO> all = aPackage.getAllPackages();

        for (int i = 0; i < 7; i++){

            LocalDate parsDay = LocalDate.now().minusDays(i);
            double tot = 0;

            for (PackageDTO packageDTO: all){

                LocalDate bookedDate = packageDTO.getBookedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (parsDay.equals(bookedDate)){
                    tot += packageDTO.getPackageValue();
                }
            }
            totalList.add(0, tot);
        }
        return totalList;
    }
}
