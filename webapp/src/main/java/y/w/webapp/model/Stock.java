package y.w.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * Stock - Using JSR-303 bean validation
 *
 * @author ywang
 * @date 8/6/2019
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stock
{
    @NotBlank(message = "Stock name is required")
    private String stockName;

    @Positive(message = "Shares must be positive number")
    private int    shares;

    /**
     * @DateTimeFormat is important for Spring MVC to convert string, like 2019-08-01, to Date property for @ModelAttribute like
     *  submit(@ModelAttribute(value="stock") Stock stock)
     */
    @NotNull(message = "Purchase date cannot be empty") // For Form Data validation
    @Past(message = "Purchase date must be past")
    @DateTimeFormat(pattern = "mm/dd/yyyy") // Date data binding
    private Date   date;
}
