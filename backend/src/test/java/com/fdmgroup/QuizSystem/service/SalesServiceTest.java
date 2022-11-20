package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.SalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesServiceTest {

    private SalesService salesService;

    @Mock
    private SalesRepository mockSalesRepo;

    @Mock
    private Sales mockSales;

    @BeforeEach
    void setup(){
        salesService = new SalesService(mockSalesRepo);
    }

    @Test
    void testGetSalesById(){
        long id = 1;
        when(mockSalesRepo.findById(id)).thenReturn(Optional.of(mockSales));

        Sales result = salesService.getSalesById(id);

        assertEquals(mockSales, result);
    }

    @Test
    void testGetSalesById_ThrowUserNotFoundException(){
        long id = 1;
        when(mockSalesRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> salesService.getSalesById(id));
    }

    @Test
    void testFindByUsername(){
        String username = "test";
        when(mockSalesRepo.findByUsername(username)).thenReturn(Optional.of(mockSales));

        User result = salesService.findByUsername(username);

        assertEquals(mockSales, result);
    }

    @Test
    void testFindByUsername_ThrowUserNotFoundException(){
        String username = "test";
        when(mockSalesRepo.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> salesService.findByUsername(username));
    }

    @Test
    void testAuthoriseSalesByUsername(){
        String username = "test";
        Sales sales = new Sales();
        when(mockSalesRepo.findByUsername(username)).thenReturn(Optional.of(sales));
        when(mockSalesRepo.save(sales)).thenReturn(sales);

        Sales result = salesService.authoriseSales(username);

        assertEquals(Role.AUTHORISED_SALES, result.getRole());
    }

    @Test
    void testGetAllUnauthorisedSales(){
        Sales sales1 = new Sales();
        sales1.setUsername("sales1");
        Sales sales2 = new Sales();
        sales2.setUsername("sales2");
        Sales sales3 = new Sales();
        sales3.setUsername("sales3");
        Sales sales4 = new Sales();
        sales4.setUsername("sales4");
        sales4.setRole(Role.AUTHORISED_SALES);
        List<Sales> sales = new ArrayList<>(List.of(sales1, sales2, sales3, sales4));

        when(mockSalesRepo.findAll()).thenReturn(sales);

        List<Sales> result = salesService.getAllUnauthorisedSales();
        assertEquals(3, result.size());
        assertEquals(sales1, result.get(0));
        assertEquals(sales2, result.get(1));
        assertEquals(sales3, result.get(2));
    }

    @Test
    void testSaveTrainer(){
        when(mockSalesRepo.save(mockSales)).thenReturn(mockSales);

        assertEquals(mockSales, salesService.save(mockSales));
    }

}
