package y.w.spring.propertyeditor;

import lombok.extern.log4j.Log4j;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * CustomDateEditor
 *
 * @author ywang
 * @date 8/21/2019
 */
@Log4j
public class CustomDateEditor extends PropertyEditorSupport
{
    public void setAsText(String text)
    {
        try
        {
            log.info("CustomDateEditor is called to convert String to Date.");
            setValue(new SimpleDateFormat("yyyy-mm-dd").parse(text));
        }
        catch (ParseException e)
        {
            setValue(null);
        }
    }
}
