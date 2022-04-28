package board2.board2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import board2.board2.domain.User;
import board2.board2.dto.UserDto;
import board2.board2.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
    ==Spring Security 필수 메소드==
    @param email
    @return UserDetails
    @throws UsernameNotFoundException -> 유저 없을때 예외
     */

    @Override  //유저 없을 때 예외 처리
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    /*
    회원 정보 저장
    @param UserDto
    @return 저장되어있는 회원의 pk
     */

    public Long save(UserDto userDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));  //pw암호화

        return userRepository.save(User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .auth(userDto.getAuth())
                .password(userDto.getPassword())
                .picture(userDto.getPicture())
                .build()
        ).getId();  //id값 반환
    }
}
