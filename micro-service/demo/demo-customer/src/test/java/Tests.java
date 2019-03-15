import com.cs.micro.eureka.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;

/**
 * @author wangjiahao
 * @version 1.0
 * @className Tests
 * @since 2019-03-07 16:20
 */
public class Tests {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//                DateTimeFormat.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("张三");
        customer.setGender((byte) 1);
        customer.setDelFlag((byte) 0);
        customer.setCreateDate(OffsetDateTime.now());
        System.out.println(mapper.writeValueAsString(customer));
    }

}
