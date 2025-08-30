package com.project.blogging.payload;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDto {

	
	    private Long permissionId;

		private String permissionName;

		
//		private String permissionSlug;

		private String permissionOperation;
}
