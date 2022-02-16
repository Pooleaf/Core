package net.pooleaf.core;

import net.pooleaf.core.modules.support.common.util.EncryptionUtil;
import org.junit.jupiter.api.Test;

public class EncryptTest {

    @Test
    public void test() {
        String password1 = EncryptionUtil.encryptAes256("sqlconfig passwd", "test");
        System.out.println(password1 + " / " + password1.length());

        String password2 = EncryptionUtil.encryptAes256("sqlconfig passwd", "testasfasfasfasf");
        System.out.println(password2 + " / " + password2.length());

        System.out.println(EncryptionUtil.encryptAes256("sqlconfig passwd", "1"));
        System.out.println(EncryptionUtil.encryptAes256("sqlconfig passwd", "a"));
        System.out.println(EncryptionUtil.encryptAes256("sqlconfig passwd", "!141241414214124353252525325k25k3jjkkj53jk25k2"));
    }

}
