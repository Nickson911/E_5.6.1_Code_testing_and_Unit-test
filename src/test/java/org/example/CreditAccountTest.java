package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreditAccountTest {

    CreditAccount account;
    static final long balance = -1000;
    static final long limit = 10000;

    @BeforeEach
    public void testOpenAccount() {
        this.account = new CreditAccount(balance, limit);
    }

 @Test
    public void testInitAccountWithPositiveBalance() {
        System.out.println("Запуск теста - testInitAccountWithPositiveBalance");

        // given
        long positiveBalance = 1000;
        Class<RuntimeException> expected = RuntimeException.class;

        // when
        Executable executable = () -> {
            CreditAccount creditAccount = new CreditAccount(positiveBalance, limit);
        };

        // then
        Assertions.assertThrows(expected, executable);
    }

    @ParameterizedTest
    @MethodSource("addTestParameters")
    public void testAdd(long amount, long expected) {

        System.out.println(this.account);
        this.account.add(amount);

        long actual = this.account.getBalance();
        Assertions.assertEquals(expected, actual);
    }

    public static Stream<Arguments> addTestParameters() {
        return Stream.of(Arguments.of(-balance, 0)
                , Arguments.of(0, balance)
                , Arguments.of(100, balance + 100)
                , Arguments.of(-balance / 2, balance / 2)
        );
    }

    // Пополнение
    @ParameterizedTest
    @MethodSource("payTestParameters")
    public void testPay(long amount, long expected) {

        System.out.println(this.account);
        this.account.pay(amount);

        long actual = account.getBalance();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("payTestParameters")
    public void testPayHamcrest(long amount, long expected) {

        this.account.pay(amount);

        long actual = account.getBalance();
        assertThat(actual, lessThanOrEqualTo(0L));
        assertThat(actual, equalTo(expected));
    }

    public static Stream<Arguments> payTestParameters() {
        return Stream.of(Arguments.of(0, balance)
                , Arguments.of(100, balance - 100)
                , Arguments.of(balance, balance)
                , Arguments.of(limit + balance, -limit)
        );
    }
}