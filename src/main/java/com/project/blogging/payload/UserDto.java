package com.project.blogging.payload;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 3, message = "User name must be min of 3 characters")
	private String name;

	@Email(message = "Email address is not valid!!")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be min of 3 characters and max of 10 charcters")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	@Pattern(regexp)
	private String password;

	@NotEmpty
	private String about;  
	
	private Boolean isAdmin = Boolean.FALSE;
	
	
	private Set<RoleDto> roles = new HashSet<>();
	
//	private Set<PermissionDto> permissions = new HashSet<>();

}
