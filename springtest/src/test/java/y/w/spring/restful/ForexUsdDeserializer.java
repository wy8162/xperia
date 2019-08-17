package y.w.spring.restful;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ForexUsdDeserializer extends JsonDeserializer<ForexUsd>
{
    @Override public ForexUsd deserialize(
            JsonParser jsonParser,
            DeserializationContext ctxt) throws IOException
    {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        return new ForexUsd(node.get("data").get("EUR").asDouble());
    }
}

