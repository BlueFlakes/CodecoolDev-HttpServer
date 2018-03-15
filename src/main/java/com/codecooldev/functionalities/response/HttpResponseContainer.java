package com.codecooldev.functionalities.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// TODO : fix dependencies and responsibilities add ctrl and public api
public class HttpResponseContainer {
    private static final byte[] CRLF = "\r\n".getBytes();
    private static final byte[] separator = ": ".getBytes();

    private Map<AttrValue, String> map = new HashMap<>();
    private Map<String, String> customUserInputs = new HashMap<>();
    private ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

    public HttpResponseContainer( ) {
    }

    public void addKnownAttr(AttrValue attrValue, String value) {
        this.map.put(attrValue, value);
    }

    public void addCustomAttr(String attribute, String value) {
        this.customUserInputs.put(attribute, value);
    }

    private interface ContainsDefaultValue {
        String getDefault();
    }

    public enum Attribute {
        HEADER("HTTP/1.0 %s %s", AttrValue.STATUS_CODE, AttrValue.STATUS_INFO),
        CURRENT_DATE("Date: %s", AttrValue.DATE),
        CONTENT_LENGTH("Content-Length: %s", AttrValue.CONTENT_LENGTH),
        CONTENT_TYPE("Content-Type: %s; %s", AttrValue.CONTENT_TYPE, AttrValue.ENCODING);

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
        CONTENT_LENGTH("0"),
        ENCODING("UTF-8");

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

    ByteArrayOutputStream createResponse(ByteArrayOutputStream bodyStream) throws ResponseCreatorException {
        try {
            addHeader();
            fillUpWithKnownProperties();
            fillUpWithCustomProperties();
            addBody(bodyStream);

            return this.responseStream;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new ResponseCreatorException("Couldn't create responseStream!");
    }

    private void fillUpWithCustomProperties() throws IOException {
        for (Map.Entry<String, String> entry : this.customUserInputs.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            addProperty(name, value);
        }
    }

    private void addHeader() throws IOException {

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
        this.responseStream.write(formattedHeader.getBytes());
        this.responseStream.write(CRLF);
    }

    private void addProperty(String writeTo, Object... attributes) throws IOException {
        String propertyWithSettledValues = replaceSpecialSignsWithAttributes(writeTo, attributes);
        this.responseStream.write(propertyWithSettledValues.getBytes());
        this.responseStream.write(CRLF);
    }

    private void addProperty(String propertyName, String value) throws IOException {
        this.responseStream.write(propertyName.getBytes());
        this.responseStream.write(separator);
        this.responseStream.write(value.getBytes());
        this.responseStream.write(CRLF);
    }

    private void addBody(ByteArrayOutputStream bodyStream) throws IOException {
        responseStream.write(CRLF);
        bodyStream.writeTo(responseStream);
    }

    private static String replaceSpecialSignsWithAttributes(String writeTo, Object... attributes) {
        return String.format(writeTo, attributes);
    }
}
