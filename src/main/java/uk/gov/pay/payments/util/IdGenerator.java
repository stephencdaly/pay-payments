package uk.gov.pay.payments.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class IdGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public String newExternalId() {
        String id = new BigInteger(130, SECURE_RANDOM).toString(32);
        return StringUtils.leftPad(id, 26, '0');
    }

}
