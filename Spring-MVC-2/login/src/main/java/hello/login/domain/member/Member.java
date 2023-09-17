package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    //@NotNull -> null만 허용하지 않음 ("", " " 허용)
    //@NotEmpty -> null과 "" 둘 다 허용하지 않음 (" "은 허용)
    //@NotBlank -> null과 ""과 " " 모두 허용하지 않음
    @NotEmpty
    private String loginId; //로그인 ID
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
}
