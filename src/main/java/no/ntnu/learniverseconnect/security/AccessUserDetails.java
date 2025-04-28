package no.ntnu.learniverseconnect.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import no.ntnu.learniverseconnect.model.entities.Role;
import no.ntnu.learniverseconnect.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contains authentication information, needed by UserDetailsService.
 */
public class AccessUserDetails implements UserDetails {
  private final String username;
  private final String password;
  private final boolean isActive;
  private final List<GrantedAuthority> authorityList = new LinkedList<>();

  /**
   * Create access object.
   *
   * @param user The user to copy data from
   */
  public AccessUserDetails(User user){
    this.username = user.getName();
    this.password = user.getPasswordHash();
    this.isActive = user.isActive();
    this.convertRoles(user.getRole());
  }

  /**
   * Converts the roles and grants authority
   *
   * @param roles roles to be granted
   */
  public void convertRoles(Set<Role> roles){
    authorityList.clear();
    for (Role role : roles){
      authorityList.add(new SimpleGrantedAuthority(role.getName()));
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
    return authorityList;
  }

  @Override
  public String getPassword(){
    return this.password;
  }

  @Override
  public String getUsername(){
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired(){
    return this.isActive;
  }

  @Override
  public boolean isAccountNonLocked(){
    return this.isActive;
  }

  @Override
  public boolean isCredentialsNonExpired(){
    return this.isActive;
  }

  @Override
  public boolean isEnabled(){
    return true;
  }

}
