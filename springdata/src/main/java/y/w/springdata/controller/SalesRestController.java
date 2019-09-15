package y.w.springdata.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import y.w.springdata.error.ErrorDetails;
import y.w.springdata.model.Sales;
import y.w.springdata.service.SalesService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SalesRestController
{
    private final SalesService salesService;

    @Autowired
    public SalesRestController(SalesService salesService)
    {
        this.salesService = salesService;
    }

    @GetMapping("/sales")
    public List<Sales> findAll()
    {
        Iterable<Sales> sales = salesService.findAll();

        List<Sales> list = new ArrayList<>();
        sales.forEach(list::add);

        for (Sales s:sales)
            System.out.println(s);

        return list;
    }

    /**
     * To access api/v1/sales/order_id?mediaType=xml or api/v1/sales/order_id
     * @param id
     * @return
     */
    @GetMapping(value = "/sales/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Optional<Sales> findOne(@PathVariable("id") @RequestParam(value="id", defaultValue="1") Long id)
    {
        return salesService.findOne(id);
    }

    /**
     * To access api/v1/order or api/v1/order?id=1?mediaType=xml or api/v1/order?id=1
     * @param id
     * @return
     */
    @GetMapping(value = "/order", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Optional<Sales> findOneOrder(@RequestParam(value="id", defaultValue="1") Long id)
    {
        return salesService.findOne(id);
    }

    /**
     * salesService.createSales may throw SQLException.
     *
     * @param sales
     * @return
     */
    @PostMapping("/sales")
    public ResponseEntity<Sales> createSales(@RequestBody Sales sales)
    {
        return ResponseEntity.ok(salesService.createSales(sales));
    }

    /**
     * salesService.createSales may throw SQLException, which will be handled by this handler.
     *
     * Refer to DefaultHandlerExceptionResolver for more details about default exception handler.
     *
     * @param ex
     * @param request
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex,  HttpServletRequest request)
    {
        log.error(ex.getMessage() + " : " + ClassUtils.getShortName(ex.getClass()));

        //TODO: handle exceptions here
    }

    @PostMapping("/delete")
    public ResponseEntity<Sales> deleteSales(@RequestBody Sales sales)
    {
        salesService.deleteOneById(sales.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Sales> deleteSalesById(@PathVariable("id") Long id)
    {
        salesService.deleteOneById(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Sales> deleteById(@PathVariable("id") Long id)
    {
        salesService.deleteOneById(id);

        return ResponseEntity.ok().build();
    }


    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleException(Exception e)
    {
        return new ErrorDetails(e.getMessage());
    }
}
