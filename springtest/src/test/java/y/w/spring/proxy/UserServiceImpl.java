package y.w.spring.proxy;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

/**
 * UserServiceImpl
 *
 * @author ywang
 * @date 9/10/2019
 */
public class UserServiceImpl implements UserService
{

    @JsonAnyGetter
    @Override public String getName(String message)
    {
        return message + ", WYANG";
    }
}
