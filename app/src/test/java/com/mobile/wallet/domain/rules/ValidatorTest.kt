package com.mobile.wallet.domain.rules

import org.junit.Assert
import org.junit.Test


class ValidatorTest {

    @Test
    fun `given a validate name when the validateText is called then return a success`() {
        val input = "UserTest"
        val result = Validator.validateText(
            value = input
        )
        Assert.assertTrue(result.status)
    }

    @Test
    fun `given an error name when the validateText is called then return an error`() {
        val input = "d"
        val result = Validator.validateText(
            value = input
        )
        Assert.assertFalse(result.status)
    }

    @Test
    fun `given an empty name when the validateText is called then return an error`() {
        val input = ""
        val result = Validator.validateText(
            value = input
        )
        Assert.assertFalse(result.status)
    }

    @Test
    fun `given an empty email when the validateEmail is called then return an error`() {
        val input = ""
        val result = Validator.validateEmail(
            email = input
        )
        Assert.assertFalse(result.status)
    }

    @Test
    fun `given an validate email when the validateEmail is called then return a success`() {
        val input = "test@test.com"
        val result = Validator.validateEmail(
            email = input
        )
        Assert.assertTrue(result.status)
    }

    @Test
    fun `given an validate password when the validatePassword is called then return a success`() {
        val input = "1234567890pass"
        val result = Validator.validatePassword(
            password = input
        )
        Assert.assertTrue(result.status)
    }

    @Test
    fun `given an error password when the validatePassword is called then return an error`() {
        val input = "13"
        val result = Validator.validatePassword(
            password = input
        )
        Assert.assertFalse(result.status)
    }

    @Test
    fun `given an empty password when the validatePassword is called then return an error`() {
        val input = ""
        val result = Validator.validatePassword(
            password = input
        )
        Assert.assertFalse(result.status)
    }
}