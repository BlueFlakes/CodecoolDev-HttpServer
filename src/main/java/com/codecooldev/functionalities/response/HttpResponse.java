package com.codecooldev.functionalities.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// TODO : fix dependencies and responsibilities add ctrl and public api
public class HttpResponse {
    private static final byte[] CRLF = "\r\n".getBytes();
    private static final byte[] separator = ": ".getBytes();

    private Map<AttrValue, String> map = new HashMap<>();
    private Map<String, String> customUserInputs = new HashMap<>();
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public HttpResponse( ) {
    }

    public void addKnownAttr(AttrValue attrValue, String value) {
        this.map.put(attrValue, value);
    }

    public void AddCustomAttr(String attribute, String value) {
        this.customUserInputs.put(attribute, value);
    }

    private interface ContainsDefaultValue {
        String getDefault();
    }

    public enum Attribute {
        HEADER("HTTP/1.0 %s %s", AttrValue.STATUS_CODE, AttrValue.STATUS_INFO),
        CURRENT_DATE("Date: %s", AttrValue.DATE),
        CONTENT_LENGTH("Content-Length: %s", AttrValue.CONTENT_LENGTH),
        CONTENT_TYPE("Content-Type: %s; %s", AttrValue.CONTENT_TYPE, AttrValue.ENCODING),
        BODY(null, AttrValue.BODY);

        private String name;
        private ContainsDefaultValue[] attributeValues;

        Attribute(String name, ContainsDefaultValue... attributeValues) {
            this.name = name;
            this.attributeValues = attributeValues;
        }
    }

    public enum AttrValue implements ContainsDefaultValue {
        STATUS_CODE("200"),
        STATUS_INFO("OK"),
        DATE(getCurrentDate()),
        CONTENT_TYPE("text/html"),
        CONTENT_LENGTH("46"),
        ENCODING("charset=iso-8859-1"),
        BODY("<html><body><h1>Hello World</h1></body></html>");

        private String value;

        AttrValue(String value) {
            this.value = value;
        }

        @Override
        public String getDefault( ) {
            return this.value;
        }

        private static String getCurrentDate( ) {
            return new Date().toString();
        }
    }

    public ByteArrayOutputStream createResponse( ) throws ResponseCreatorException {
        try {
            addHeaderAndGetNextStep();
            fillUpWithKnownProperties();
            fillUpWithCustomProperties();
            addBody(this.map.getOrDefault(AttrValue.BODY, AttrValue.BODY.getDefault()));
            return this.byteArrayOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new ResponseCreatorException("Couldn't create response!");
    }

    private void fillUpWithCustomProperties() throws IOException {
        for (Map.Entry<String, String> entry : this.customUserInputs.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            addProperty(name, value);
        }
    }

    private void addHeaderAndGetNextStep() throws IOException {

        String identity = Attribute.HEADER.name;
        String statusCode = map.getOrDefault(AttrValue.STATUS_CODE, AttrValue.STATUS_CODE.getDefault());
        String statusInfo = map.getOrDefault(AttrValue.STATUS_INFO, AttrValue.STATUS_INFO.getDefault());
        addHeader(identity, statusCode, statusInfo);
    }

    private void fillUpWithKnownProperties() throws IOException {

        Attribute[] knownProperties =
                new Attribute[]{Attribute.CONTENT_LENGTH, Attribute.CURRENT_DATE, Attribute.CONTENT_TYPE};

        for (Attribute property : knownProperties) {
            String identity = property.name;
            Object[] values = Arrays.stream(property.attributeValues)
                    .map(n -> this.map.getOrDefault(n, n.getDefault()))
                    .toArray();

            addProperty(identity, values);
        }
    }

    private void addHeader(String header, Object... attributes) throws IOException {
        String formattedHeader = replaceSpecialSignsWithAttributes(header, attributes);
        this.byteArrayOutputStream.write(formattedHeader.getBytes());
        this.byteArrayOutputStream.write(CRLF);
    }

    private void addProperty(String writeTo, Object... attributes) throws IOException {
        String propertyWithSettledValues = replaceSpecialSignsWithAttributes(writeTo, attributes);
        this.byteArrayOutputStream.write(propertyWithSettledValues.getBytes());
        this.byteArrayOutputStream.write(CRLF);
    }

    private void addProperty(String propertyName, String value) throws IOException {
        this.byteArrayOutputStream.write(propertyName.getBytes());
        this.byteArrayOutputStream.write(separator);
        this.byteArrayOutputStream.write(value.getBytes());
        this.byteArrayOutputStream.write(CRLF);
    }

    private void addBody(String body) throws IOException {
        byteArrayOutputStream.write(CRLF);
        byteArrayOutputStream.write(body.getBytes());
    }

    private void addBody(byte[] body) throws IOException {
        byteArrayOutputStream.write(CRLF);
        byteArrayOutputStream.write(body);
    }

    private static String replaceSpecialSignsWithAttributes(String writeTo, Object... attributes) {
        return String.format(writeTo, attributes);
    }
}
