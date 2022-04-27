package board2.board2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import board2.board2.dto.UserDto;
import board2.board2.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/user") //user로 post 요청이 왔을때
    public String signup(UserDto userDto) {
        userService.save(userDto);
        return "redirect:/login";
    }

    @GetMapping(value = "/logout") //로그아웃 요청시 로그아웃
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
