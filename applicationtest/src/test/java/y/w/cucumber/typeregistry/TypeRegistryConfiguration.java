package y.w.cucumber.typeregistry;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;
import io.cucumber.datatable.TableEntryTransformer;
import y.w.model.Person;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import static java.util.Locale.ENGLISH;

/**
 * Cucumber TypeRegistryConfiguration
 *
 * 1.Transform the cells. Each cell represents an object.
 * 2.Transform the rows. Each row represents an object.
 * 3.Transform the entries. The entries of row paired with its corresponding header represent an object.
 * 4.Transform the table. The table as a whole is transformed into a single object.
 */
public class TypeRegistryConfiguration implements TypeRegistryConfigurer
{
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override public Locale locale()
    {
        return ENGLISH;
    }

    /**
     * Register a transformer to convert JSON String to Java object.
     *
     * @param typeRegistry
     */
    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry)
    {
        typeRegistry.defineDataTableType(
                new DataTableType(Person.class,
                        (TableEntryTransformer<Person>) entry ->
                                new Person(entry.get("firstName"), entry.get("lastName"), simpleDateFormat.parse(entry.get("birthDate"))))
        );

        CustomDataTransformer transformer = new CustomDataTransformer();
        typeRegistry.setDefaultDataTableEntryTransformer(transformer);
        typeRegistry.setDefaultParameterTransformer(transformer);
        typeRegistry.setDefaultDataTableCellTransformer(transformer);
    }

    private static final class CustomDataTransformer implements
            ParameterByTypeTransformer,
            TableCellByTypeTransformer,
            TableEntryByTypeTransformer
    {
        private final ObjectMapper objectMapper = new ObjectMapper();

        /**
         * 1.Transform the cells. Each cell represents an object.
         * @param cell
         * @return
         * @throws Throwable
         */
        @Override
        public <T> T transform(String cell, Class<T> cellType) {
            return objectMapper.convertValue(cell, cellType);
        }

        /**
         * 3.Transform the entries. The entries of row paired with its corresponding header represent an object.
         *
         * Map DataTable to list of Java objects. This has to use @Given annotation instead of Java 8 lambda.
         *
         * @param entry
         * @param type
         * @param cellTransformer
         * @param <T>
         * @return
         */
        @Override
        public <T> T transform(Map<String, String> entry, Class<T> type, TableCellByTypeTransformer cellTransformer)
        {
            return objectMapper.convertValue(entry, type);
        }

        /**
         * Maps a string of JSON data to a Java object.
         *
         * @param s a string or an instance of type.
         * @param type the class type of the target object. It can be String or any class. A string will be returned if it is String.
         * @return
         * @throws Throwable
         */
        @Override public Object transform(String s, Type type) throws Throwable
        {
            if (type.equals(String.class))
            {
                return s;
            }
            @SuppressWarnings("unchecked")
            Class<?> clazz = (Class<?>) type;
            Object o = objectMapper.readValue(new StringReader(s), clazz);
            return o;
        }
    }
}