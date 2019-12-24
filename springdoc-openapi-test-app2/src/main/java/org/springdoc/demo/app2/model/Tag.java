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
 * Tag
 */

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-12-02T19:53:02.467132-01:00[Atlantic/Azores]")
@JacksonXmlRootElement(localName = "tag")
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.FIELD)

public class Tag {

    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")

    private Long id;


    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")

    private String name;


    public Tag id(Long id) {
        this.id = id;
        return this;
    }


    /**
     * Get id
     *
     * @return id
     */
    @Schema(description = "")


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Tag name(String name) {
        this.name = name;
        return this;
    }


    /**
     * Get name
     *
     * @return name
     */
    @Schema(description = "")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(this.id, tag.id) &&
                Objects.equals(this.name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Tag {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

