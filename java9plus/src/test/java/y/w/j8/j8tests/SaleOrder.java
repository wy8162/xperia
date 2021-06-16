package y.w.j8.j8tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;

public class SaleOrder {
    private static final String[] fieldMappings = {
        "country",
        "orderMethod",
        "retailType",
        "productLine",
        "productType",
        "product",
        "year",
        "quarter",
        "revenue",
        "quantity",
        "grossMargin"
    };

    private String country;
    private String orderMethod;
    private String retailType;
    private String productLine;
    private String productType;
    private String product;
    private String year;
    private String quarter;
    private String revenue;
    private String quantity;
    private String grossMargin;

    public SaleOrder() {
    }

    private void setAttributeValues(String[] values) {
        try {
            for (int i = 0; i < fieldMappings.length; i++) {
                Field field = this.getClass().getDeclaredField(fieldMappings[i]);
                if (values[i] != null) {
                    field.set(getClass(), values[i]);
                } else {
                    field.set(getClass(), null);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final String buildJsonString(String values) {
        String[] valueList = values.split(",");
        StringBuilder sb = new StringBuilder().append("{");
        for (int i = 0; i < fieldMappings.length; i++) {
            String v = i < valueList.length ? v = valueList[i] : "None";
            sb.append("\"" + fieldMappings[i] + "\":\"" + v + "\"");
            if ( i != fieldMappings.length - 1)
                sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    public static final SaleOrder buildInstanceFromString(String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = buildJsonString(value);
        if (json == null) return null;
        Reader reader = new StringReader(json);
        try {
            return objectMapper.readValue(reader, SaleOrder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final SaleOrder buildInstance(String[] values) {
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setAttributeValues(values);
        return saleOrder;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOrderMethod() {
        return orderMethod;
    }

    public void setOrderMethod(String orderMethod) {
        this.orderMethod = orderMethod;
    }

    public String getRetailType() {
        return retailType;
    }

    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGrossMargin() {
        return grossMargin;
    }

    public void setGrossMargin(String grossMargin) {
        this.grossMargin = grossMargin;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{\"Exception\": Exception}";
    }
}
