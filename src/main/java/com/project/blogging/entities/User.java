package com.project.blogging.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@SequenceGenerator(
	        name = "user_seq",
	        sequenceName = "user_sequence",
	        allocationSize = 1
	    )
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	private int id;
	
	@Column(name="user_name", nullable=false,length=100)
	private String name;
	
	private String password;
	
	
	private String email;
	
	
	private String about;
	
	
	private Boolean isAdmin = Boolean.FALSE;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts=new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	private Set<Comment> comments=new HashSet<>();
	
	@ManyToMany(cascade=CascadeType.ALL, fetch =FetchType.EAGER )
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//
//		List<SimpleGrantedAuthority> authorities = this.roles.stream()
//				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//
//		return authorities;
//	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

	    List<SimpleGrantedAuthority> roleAuthorities = this.roles.stream()
	            .map(role -> new SimpleGrantedAuthority(role.getName()))
	            .collect(Collectors.toList());

	    List<SimpleGrantedAuthority> permissionAuthorities = this.roles.stream()
	            .flatMap(role -> role.getPermissions().stream()) // Get permissions from roles
	            .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName())) // Convert to GrantedAuthority
	            .collect(Collectors.toList());

	    roleAuthorities.addAll(permissionAuthorities); // Merge roles & permissions
	    return roleAuthorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
}
