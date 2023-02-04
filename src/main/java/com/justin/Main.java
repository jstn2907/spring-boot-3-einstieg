package com.justin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
}
    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
       customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer age){
        Customer customer = customerRepository.findById(id).get();
       if(name != null){
           customer.setName(name);
       }
        if(email != null){
            customer.setEmail(email);
        }
        if(age != null){
            customer.setAge(age);
        }
        customerRepository.save(customer);
    }

}
