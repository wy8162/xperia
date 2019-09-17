package y.w.spring.hibernate.nativeapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Location
 *
 * @author ywang
 * @date 9/16/2019
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Location
{
    private static final long serialVersionUID = -3023672042412321139L;
    private Long id;
    private String name;
    private Address billingAddress;
    private Address mailingAddress;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Address
    {
        private String city;
        private String state;
        private String zipCode;
        private String streetAddress;
    }
}
