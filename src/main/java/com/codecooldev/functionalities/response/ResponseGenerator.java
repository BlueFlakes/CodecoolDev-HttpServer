package com.codecooldev.functionalities.response;

final class ResponseGenerator {
    private static final String CRLF = "\r\n";

    // creating instance is not permitted
    private ResponseGenerator() {
        throw new AssertionError();
    }

    static HeaderBuilder create( ) {
        StringBuilder responseAccumulator = new StringBuilder();
        return new HeaderBuilder(responseAccumulator);
    }

    static class HeaderBuilder {
        private StringBuilder response;

        private HeaderBuilder(StringBuilder response) {
            this.response = response;
        }

        MultiplePropertiesAdder addHeader(String header, Object... attributes) {
            try {
                String formattedHeader = replaceSpecialSignsWithAttributes(header, attributes);

                response.append(formattedHeader)
                        .append(CRLF);
                return new MultiplePropertiesAdder(response);

            } finally {
                this.response = null;
            }
        }
    }

    static class MultiplePropertiesAdder {
        private static final String separator = ": ";
        private StringBuilder response;

        private MultiplePropertiesAdder(StringBuilder response) {
            this.response = response;
        }

        MultiplePropertiesAdder addProperty(String propertyName, String propertyBody, Object... attributes) {
            String formattedBody = replaceSpecialSignsWithAttributes(propertyBody, attributes);
            this.response.append(propertyName)
                         .append(separator)
                         .append(formattedBody)
                         .append(CRLF);

            return this;
        }

        BodyAdder finishAddingProperties( ) {
            try {
                return new BodyAdder(this.response);
            } finally {
                this.response = null;
            }
        }
    }

    static class BodyAdder {
        private StringBuilder response;

        private BodyAdder(StringBuilder response) {
            this.response = response;
        }

        FormattedHttpResponse addBody(String body) {
            try {
                this.response.append(CRLF)
                        .append(body);

                return new FormattedHttpResponse(this.response);
            } finally {
                this.response = null;
            }
        }
    }

    static class FormattedHttpResponse {
        private StringBuilder response;

        private FormattedHttpResponse(StringBuilder response) {
            this.response = response;
        }

        StringBuilder getResponse( ) {
            return response;
        }
    }

    private static String replaceSpecialSignsWithAttributes(String writeTo, Object... attributes) {
        return String.format(writeTo, attributes);
    }
}
