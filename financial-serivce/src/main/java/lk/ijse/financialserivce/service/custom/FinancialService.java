package lk.ijse.financialserivce.service.custom;

import lk.ijse.financialserivce.service.SuperService;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Fri 8:42 AM on 10/27/2023
 * @project financial-serivce
 **/
public interface FinancialService extends SuperService {

    Double getDailyIncome();

    Double getWeeklyIncome();

    Double getMonthlyIncome();

    Double getAnnualIncome();

    Long getTodaySalesCount();

    List<Double> getPastDayIncomes();
}
