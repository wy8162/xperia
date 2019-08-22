package y.w.spring.propertyeditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * CustomPropertyEditorConfig
 *
 * @author ywang
 * @date 8/21/2019
 */
@Configuration
public class CustomDateEditorRegistrar implements PropertyEditorRegistrar
{
    @Override public void registerCustomEditors(PropertyEditorRegistry registry)
    {
        registry.registerCustomEditor(Date.class, new CustomDateEditor());
    }
}
