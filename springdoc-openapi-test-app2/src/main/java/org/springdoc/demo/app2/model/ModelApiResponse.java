package org.springdoc.demo.app2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * ModelApiResponse
 */

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-12-02T19:53:02.467132-01:00[Atlantic/Azores]")
@JacksonXmlRootElement(localName = "##default")
@XmlRootElement(name = "##default")
@XmlAccessorType(XmlAccessType.FIELD)

public class ModelApiResponse {

    @JsonProperty("code")
    @JacksonXmlProperty(localName = "code")

    private Integer code;


    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")

    private String type;


    @JsonProperty("message")
    @JacksonXmlProperty(localName = "message")

    private String message;


    public ModelApiResponse code(Integer code) {
        this.code = code;
        return this;
    }


    /**
     * Get code
     *
     * @return code
     */
    @Schema(description = "")


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public ModelApiResponse type(String type) {
        this.type = type;
        return this;
    }


    /**
     * Get type
     *
     * @return type
     */
    @Schema(description = "")


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public ModelApiResponse message(String message) {
        this.message = message;
        return this;
    }


    /**
     * Get message
     *
     * @return message
     */
    @Schema(description = "")


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelApiResponse modelApiResponse = (ModelApiResponse) o;
        return Objects.equals(this.code, modelApiResponse.code) &&
                Objects.equals(this.type, modelApiResponse.type) &&
                Objects.equals(this.message, modelApiResponse.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, type, message);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ModelApiResponse {\n");

        sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

