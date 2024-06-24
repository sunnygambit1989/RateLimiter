package com.sunny.ratelimiter.junits;

import com.sunny.ratelimiter.controller.GenericController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GenericControllerTest {


    @InjectMocks
    private GenericController genericController;


    @Test
    public void getDevelopers() {
        List<String> response = genericController.getDevelopers();
        Assertions.assertEquals(response.size(), 2);
    }

    @Test
    public void getOrganizations() {
        List<String> response = genericController.getOrganizations();
        Assertions.assertEquals(response.size(), 2);
    }


}
