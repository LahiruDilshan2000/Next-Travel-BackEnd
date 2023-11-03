package lk.ijse.financialserivce.api;

import lk.ijse.financialserivce.service.custom.FinancialService;
import lk.ijse.financialserivce.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lahiru Dilshan
 * @created Fri 8:37 AM on 10/27/2023
 * @project financial-serivce
 **/

@RestController
@RequestMapping("api/v1/financial")
@RequiredArgsConstructor
@CrossOrigin
public class FinancialController {

    private final FinancialService financialService;


    @GetMapping(value = "/daily", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getDailyIncome(){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Daily income getting successful")
                .data(financialService.getDailyIncome())
                .build();
    }

    @GetMapping(value = "/weekly", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getWeeklyIncome(){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Weekly income getting successful")
                .data(financialService.getWeeklyIncome())
                .build();
    }

    @GetMapping(value = "/monthly", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getMonthlyIncome(){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Monthly income getting successful")
                .data(financialService.getMonthlyIncome())
                .build();
    }

    @GetMapping(value = "/annual", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getAnnualIncome(){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Annul income getting successful")
                .data(financialService.getAnnualIncome())
                .build();
    }

    @GetMapping(value = "/sales", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getTodaySalesCount(){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Today sales getting successful")
                .data(financialService.getTodaySalesCount())
                .build();
    }


    @GetMapping(value = "/pastDayIncome", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getPastDaysIncome(){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Past day incomes getting successful")
                .data(financialService.getPastDayIncomes())
                .build();
    }
}
