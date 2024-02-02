package msa.project.monologicserver.global.regex;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonRegex {

    // 월 혹은 일이 '0' 없이 한자리로 왔을 때도 처리할 수 있도록 함
    public static final String dateRegex = "^(20)\\d{2}-(0?[1-9]|10|11|12)-(0?[1-9]|[12][0-9]|3[01])$";

    public static final String userIdRegex = "^(?=.*\\d{1})(?=.*[_]{1})(?=.*[a-zA-Z]{1}).{4,20}$";

    public static final String userPhoneRegex = "^[0-9]{11}$";


    // 사용자 비밀번호 (영문 or 숫자 or 특수문자(_) 8-20자)
    public static final String userPWRegex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";

    public static final String pattern2 = "^[A-Za-z[0-9]]{8,20}$"; // 영문, 숫자
    public static final String pattern3 = "^[[0-9]$@$!%*#?&]{8,20}$"; //영문, 특수문자
    public static final String pattern4 = "^[[A-Za-z]$@$!%*#?&]{8,20}$"; // 특수문자, 숫자

    public static final String patternEnglish = "^[A-Za-z]{8,20}$"; // 영문, 숫자
    public static final String patternNumber = "^[0-9]{8,20}$"; //영문, 특수문자
    public static final String patternSpecialChar = "^[$@$!%*#?&]{8,20}$"; // 특수문자, 숫자

}