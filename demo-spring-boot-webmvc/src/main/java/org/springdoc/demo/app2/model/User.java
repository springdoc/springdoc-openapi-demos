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

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * User
 */

@JacksonXmlRootElement(localName = "user")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)

public class User {

	@JsonProperty("id")
	@JacksonXmlProperty(localName = "id")

	private Long id;


	@JsonProperty("username")
	@JacksonXmlProperty(localName = "username")

	private String username;


	@JsonProperty("firstName")
	@JacksonXmlProperty(localName = "firstName")

	private String firstName;


	@JsonProperty("lastName")
	@JacksonXmlProperty(localName = "lastName")

	private String lastName;


	@JsonProperty("email")
	@JacksonXmlProperty(localName = "email")

	private String email;


	@JsonProperty("password")
	@JacksonXmlProperty(localName = "password")

	private String password;


	@JsonProperty("phone")
	@JacksonXmlProperty(localName = "phone")

	private String phone;


	@JsonProperty("userStatus")
	@JacksonXmlProperty(localName = "userStatus")

	private Integer userStatus;


	public User id(Long id) {
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


	public User username(String username) {
		this.username = username;
		return this;
	}


	/**
	 * Get username
	 *
	 * @return username
	 */
	@Schema(example = "theUser", description = "")


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public User firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}


	/**
	 * Get firstName
	 *
	 * @return firstName
	 */
	@Schema(example = "John", description = "")


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public User lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}


	/**
	 * Get lastName
	 *
	 * @return lastName
	 */
	@Schema(example = "James", description = "")


	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public User email(String email) {
		this.email = email;
		return this;
	}


	/**
	 * Get email
	 *
	 * @return email
	 */
	@Schema(example = "john@email.com", description = "")


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public User password(String password) {
		this.password = password;
		return this;
	}


	/**
	 * Get password
	 *
	 * @return password
	 */
	@Schema(example = "12345", description = "")


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public User phone(String phone) {
		this.phone = phone;
		return this;
	}


	/**
	 * Get phone
	 *
	 * @return phone
	 */
	@Schema(example = "12345", description = "")


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public User userStatus(Integer userStatus) {
		this.userStatus = userStatus;
		return this;
	}


	/**
	 * User Status
	 *
	 * @return userStatus
	 */
	@Schema(example = "1", description = "User Status")


	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(this.id, user.id) &&
				Objects.equals(this.username, user.username) &&
				Objects.equals(this.firstName, user.firstName) &&
				Objects.equals(this.lastName, user.lastName) &&
				Objects.equals(this.email, user.email) &&
				Objects.equals(this.password, user.password) &&
				Objects.equals(this.phone, user.phone) &&
				Objects.equals(this.userStatus, user.userStatus);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, firstName, lastName, email, password, phone, userStatus);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class User {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    username: ").append(toIndentedString(username)).append("\n");
		sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
		sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    password: ").append(toIndentedString(password)).append("\n");
		sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
		sb.append("    userStatus: ").append(toIndentedString(userStatus)).append("\n");
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
