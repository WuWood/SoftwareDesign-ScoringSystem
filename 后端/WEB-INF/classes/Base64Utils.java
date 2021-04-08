import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Utils {

    /*����һ���ַ���*/
    public static String encode(String str)  {
        String encode = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        return encode;
    }

    //  ����
    public static String decode(String str)  {
        String decode = new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
        return decode;
    }

}