package y.w.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Stock
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

    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Shares must be > 0")
    private int    shares;

    /**
     * @DateTimeFormat is important for Spring MVC to convert string, like 2019-08-01, to Date property for @ModelAttribute like
     *  submit(@ModelAttribute(value="stock") Stock stock)
     */
    @NotBlank(message = "Purchase date cannot be empty") // For Form Data validation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date   date;
}
