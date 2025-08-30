package com.project.blogging;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.blogging.entities.Role;
import com.project.blogging.payload.AppConstants;
import com.project.blogging.repositories.RoleRepo;

@SpringBootApplication
@EnableCaching
public class BlogAppApisApplication{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);

		System.out.println("Blog application started.......");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Override
//	public void run(String... args) throws Exception {
//
////		System.out.println(this.passwordEncoder.encode("8989"));
//
//		try {
//			Role adminrole = new Role();
//			adminrole.setId(AppConstants.ADMIN_USER);
//			adminrole.setName("ROLE_ADMIN");
//
//			Role userrole = new Role();
//			userrole.setId(AppConstants.NORMAL_USER);
//			userrole.setName("ROLE_NORMAL");
//
//			List<Role> roles = List.of(adminrole, userrole);
//
//			List<Role> result = this.roleRepo.saveAll(roles);
//
//			result.forEach(role -> {
//				System.out.println(role.getName());
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//}
//
//}
}
