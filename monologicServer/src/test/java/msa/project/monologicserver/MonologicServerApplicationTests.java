package msa.project.monologicserver;

import msa.project.monologicserver.domain.product.entity.StatusType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class MonologicServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void enumTest() throws Exception {
        System.out.println(StatusType.PRE);
        System.out.println(StatusType.PRE.getStatus());
        System.out.println(StatusType.PRE.name());

        System.out.println(StatusType.POST);
        System.out.println(StatusType.POST.getStatus());
        System.out.println(StatusType.RESERVATION);
        System.out.println(StatusType.RESERVATION.getStatus());
        System.out.println(StatusType.valueOf("pre")); // String -> Enum
        List<StatusType> collect = Arrays.stream(StatusType.values()).collect(Collectors.toList());
        System.out.println("collect = " + collect);

    }

}
