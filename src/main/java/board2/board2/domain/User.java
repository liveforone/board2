package board2.board2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "auth")
    private String auth;

    @Column
    private String picture;

    @Builder
    public User(String name, String email, String password, String auth,String picture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.auth = auth;
        this.picture = picture;
    }

    //값 메소드
    @Override //사용자의 권한을 콜렉션으로 반환 / 클래스 자료형은 GrantedAuthority로 구현해야함!
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();  //권한 중복을 제거하기 위해 set 사용
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override  //계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        //만료 확인 로직
        return true;  //true -> 만료X
    }

    @Override  //계정 잠금 여부 반환
    public boolean isAccountNonLocked() {
        //잠금 확인 로직
        return true;
    }

    @Override  //패스워드 만료 여부 반환
    public boolean isCredentialsNonExpired() {
        //pw 만료 확인 로직
        return true;
    }

    @Override  //계정 사용 여부 반환
    public boolean isEnabled() {
        //계정 사용 가능 여부 확인 로직
        return true;
    }
}