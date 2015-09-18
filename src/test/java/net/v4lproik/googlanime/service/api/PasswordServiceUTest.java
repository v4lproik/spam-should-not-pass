package net.v4lproik.googlanime.service.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PasswordServiceUTest {

    PasswordService service;

    @Before
    public void setUp(){
        service = new PasswordService();
    }

    @Test
    public void test_validatePassword_shouldBeNotBeOK() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String pwd = "spidercochonlikes999";
        String hashes = "1ef4960421ec676f1fe5bbaf5077434e3c14fbd8ae226d954d545f0e3738894a8d583d726491ba70:afb2c15dafdec45c40f3e888d94050cd42bb3fefd0dd2351c280aada5a36140c56ef52d95f2aa0bd:1000";
        assertEquals("", service.validatePassword(pwd, hashes), false);
    }

    @Test
    public void test_validateEmptyPassword_shouldBeNotBeOK() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String pwd = "";
        String hashes = "1ef4960421ec676f1fe5bbaf5077434e3c14fbd8ae226d954d545f0e3738894a8d583d726491ba70:afb2c15dafdec45c40f3e888d94050cd42bb3fefd0dd2351c280aada5a36140c56ef52d95f2aa0bd:1000";
        assertEquals("", service.validatePassword(pwd, hashes), false);
    }

    @Test
    public void test_validatePassword_shouldBeOK() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String pwd = "spidercochonlikesSushis999";
        String hashes = "1ef4960421ec676f1fe5bbaf5077434e3c14fbd8ae226d954d545f0e3738894a8d583d726491ba70:afb2c15dafdec45c40f3e888d94050cd42bb3fefd0dd2351c280aada5a36140c56ef52d95f2aa0bd:1000";
        assertEquals("", service.validatePassword(pwd, hashes), true);
    }
}