package com.example.demo.Controllers;

import com.example.demo.Models.TestsModels;
import com.example.demo.Repositories.TestsRespository;
import com.example.demo.Services.TestsServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;

import java.util.List;

public class TestsControllersTests {
    @Autowired
    TestsServices testsServices;

    @Autowired
    TestsRespository testsRespositoryMock = Mockito.mock(TestsRespository.class);

    @Autowired
    TestsControllers testsControllers = new TestsControllers();
    @Test
    public void obtenerTestByID() {
        // Se inicializa un modelo mockTest
        /*{
            id: 1,
            name: "Test 1",
            description:"Descrippción T1"
        }*/
        TestsModels mockTest = new TestsModels();
        mockTest.setId((long) 1);
        mockTest.setName("Test 1");
        mockTest.setDescription("Descrippción T1");
        Mockito.when(testsRespositoryMock.findById(1)).then(mockTest);
    }

    /*
    @Test
    void obtenerTestByID() {
        TestsControllers testsControllers = new TestsControllers();
        final ? resultado = testsServices.obtenerTestByID();
        Assertions.assertArrayEquals("", "");
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.fail();
    }

    @Test
    void guardarTest() {
        TestsControllers testsControllers = new TestsControllers();
        final ? resultado = testsServices.guardarTest();
        Assertions.assertArrayEquals("", "");
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.fail();
    }

    @Test
    void actualizarTest() {
        TestsControllers testsControllers = new TestsControllers();
        final ? resultado = testsServices.actualizarTest();
        Assertions.assertArrayEquals("", "");
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.fail();
    }

    @Test
    void eliminarTest() {
        TestsControllers testsControllers = new TestsControllers();
        final ? resultado = testsServices.eliminarTest();
        Assertions.assertArrayEquals("", "");
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.fail();
    }
     */
}
