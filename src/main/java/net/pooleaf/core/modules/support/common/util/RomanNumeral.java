package net.pooleaf.core.modules.support.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RomanNumeral {

    M(1000),
    CM(900),

    D(500),
    CD(400),

    C(100),
    XC(90),

    L(50),
    XL(40),

    X(10),
    IX(9),

    V(5),
    IV(4),

    I(1);


    private final int number;

}
