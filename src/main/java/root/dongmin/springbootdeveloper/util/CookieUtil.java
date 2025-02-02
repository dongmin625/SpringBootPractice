package root.dongmin.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {
    // 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response, String name ,String value, int maxAge){

        Cookie cookie = new Cookie(name,value); // 새로운 쿠키를 만듬
        cookie.setPath("/"); // 모든 경로에서 쿠키가 적용되도록 설정
        cookie.setMaxAge(maxAge); // 쿠키가 유지될 시간 설정
        response.addCookie(cookie); // 클라이언트에게 쿠키를 포함하여 응답
    }

    // 쿠키의 이름을 받아 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name){

        Cookie[] cookies = request.getCookies(); // 클라이언트가 보낸 모든 쿠키를 받아옴
        if(cookies == null){
            return; // 없다면 종료
        }

        for(Cookie cookie : cookies){ // 해당되는 쿠키를 찾음
            if(name.equals(cookie.getName())){
                cookie.setValue(""); // 쿠키의 값을 빈문자열로 바꿈
                cookie.setPath("/"); // 쿠키를 전체 사이트에서 삭제할수 있게함
                cookie.setMaxAge(0); // 유지시간을 0으로
                response.addCookie(cookie); // 유지시간이 0 이된 쿠키는 클라이언트가 자동을 삭제함
            }
        }
    }

    // 객체를 직렬화해 쿠키의 값으로 변환
    public static String serialize(Object obj){
        return Base64.getUrlEncoder() // 인코딩하여 쿠키에 저장할수 있게함
                .encodeToString(SerializationUtils.serialize(obj)); // 객체를 직렬화 하여 바이트 배열로 바꿈
    }

    // 쿠키를 역직렬화헤 겍체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls){
        return cls.cast(
                SerializationUtils.deserialize( // 바이트 배열을 객체로 역직렬화 함
                        Base64.getUrlDecoder().decode(cookie.getValue()) // 쿠키에 저장된 값을 가져옴
                        // 디코딩 하여 원래의 바이트 배열로 바꿈
                )
        );
    }
}
