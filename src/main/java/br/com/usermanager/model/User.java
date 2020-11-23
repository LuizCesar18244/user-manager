package br.com.usermanager.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@NonNull
	private String CPF;
	
	@NonNull
	private String password;
	
	@NonNull
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private LocalDate birthdate;
	
	@NotNull
	@ManyToOne
	private Station station;
	
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Profile> profiles;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.status.equals(Status.ACTIVE);
	}

}
