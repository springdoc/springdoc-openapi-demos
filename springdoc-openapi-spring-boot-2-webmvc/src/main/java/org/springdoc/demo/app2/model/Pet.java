/*
 *
 *  * Copyright 2019-2020 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */


package org.springdoc.demo.app2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.demo.app2.model.Category;
import org.springdoc.demo.app2.model.Tag;

/**
 * Pet
 */

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-12-02T19:53:02.467132-01:00[Atlantic/Azores]")
@JacksonXmlRootElement(localName = "pet")
@XmlRootElement(name = "pet")
@XmlAccessorType(XmlAccessType.FIELD)

public class Pet {

	@JsonProperty("id")
	@JacksonXmlProperty(localName = "id")

	private Long id;


	@JsonProperty("name")
	@JacksonXmlProperty(localName = "name")

	private String name;


	@JsonProperty("category")
	@JacksonXmlProperty(localName = "category")

	private Category category;


	@JsonProperty("photoUrls")
	@JacksonXmlProperty(localName = "photoUrls")

	@Valid
	private List<String> photoUrls = new ArrayList<String>();


	@JsonProperty("tags")
	@JacksonXmlProperty(localName = "tags")

	@Valid
	private List<Tag> tags = null;

	/**
	 * pet status in the store
	 */
	public enum StatusEnum {
		AVAILABLE("available"),

		PENDING("pending"),

		SOLD("sold");

		private String value;

		StatusEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static StatusEnum fromValue(String value) {
			for (StatusEnum b : StatusEnum.values()) {
				if (b.value.equals(value)) {
					return b;
				}
			}
			throw new IllegalArgumentException("Unexpected value '" + value + "'");
		}
	}


	@JsonProperty("status")
	@JacksonXmlProperty(localName = "status")

	private StatusEnum status;


	public Pet id(Long id) {
		this.id = id;
		return this;
	}


	/**
	 * Get id
	 *
	 * @return id
	 */
	@Schema(example = "10", description = "")


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Pet name(String name) {
		this.name = name;
		return this;
	}


	/**
	 * Get name
	 *
	 * @return name
	 */
	@Schema(example = "doggie", required = true, description = "")
	@NotNull


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Pet category(Category category) {
		this.category = category;
		return this;
	}


	/**
	 * Get category
	 *
	 * @return category
	 */
	@Schema(description = "")

	@Valid

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public Pet photoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
		return this;
	}


	public Pet addPhotoUrlsItem(String photoUrlsItem) {
		this.photoUrls.add(photoUrlsItem);
		return this;
	}


	/**
	 * Get photoUrls
	 *
	 * @return photoUrls
	 */
	@Schema(required = true, description = "")
	@NotNull


	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}


	public Pet tags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}


	public Pet addTagsItem(Tag tagsItem) {
		if (this.tags == null) {
			this.tags = new ArrayList<>();
		}
		this.tags.add(tagsItem);
		return this;
	}


	/**
	 * Get tags
	 *
	 * @return tags
	 */
	@Schema(description = "")

	@Valid

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}


	public Pet status(StatusEnum status) {
		this.status = status;
		return this;
	}


	/**
	 * pet status in the store
	 *
	 * @return status
	 */
	@Schema(description = "pet status in the store")


	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Pet pet = (Pet) o;
		return Objects.equals(this.id, pet.id) &&
				Objects.equals(this.name, pet.name) &&
				Objects.equals(this.category, pet.category) &&
				Objects.equals(this.photoUrls, pet.photoUrls) &&
				Objects.equals(this.tags, pet.tags) &&
				Objects.equals(this.status, pet.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, category, photoUrls, tags, status);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Pet {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    category: ").append(toIndentedString(category)).append("\n");
		sb.append("    photoUrls: ").append(toIndentedString(photoUrls)).append("\n");
		sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
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