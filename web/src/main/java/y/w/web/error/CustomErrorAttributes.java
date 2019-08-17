package y.w.web.error;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * CustomErrorAttributes
 *
 * @author ywang
 * @date 8/7/2019
 */
public class CustomErrorAttributes extends DefaultErrorAttributes
{
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace)
    {
        Map<String, Object> errorAttributes =
                super.getErrorAttributes(request, includeStackTrace);

        // The user parameters provided in the request URL.
        errorAttributes.put("parameters", request.getParameterMap());

        return errorAttributes;
    }
}