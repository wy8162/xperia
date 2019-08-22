package y.w.spring.propertyeditor;

import lombok.extern.log4j.Log4j;

import java.beans.PropertyEditorSupport;

/**
 * ExoticTypeEditor
 *
 * @author ywang
 * @date 8/21/2019
 */
@Log4j
public class ExoticTypeEditor extends PropertyEditorSupport
{

    public void setAsText(String text) {
        log.info("ExoticTypeEditor is automatically used by Java");
        setValue(new ExoticType(text.toUpperCase()));
    }
}