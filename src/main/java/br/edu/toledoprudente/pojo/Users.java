package br.edu.toledoprudente.pojo;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@EntityScan
@Table(name = "Users")
public class Users extends AbstractEntity<Integer> implements UserDetails {

	@Column(length = 150, nullable = false)
	private String username;

	@Column(length = 350, nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean enabled;

	@Column(name = "isAdmin")
	private boolean isAdmin;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST)
	private List<Funcionario> funcionarios;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<AppAuthority> getAppAuthorities() {
		return appAuthorities;
	}

	public void setAppAuthorities(Set<AppAuthority> appAuthorities) {
		this.appAuthorities = appAuthorities;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "appUser")
	private Set<AppAuthority> appAuthorities;

	public Users(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,

			Collection<? extends AppAuthority> authorities
	) {
		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public Users() {
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
}
