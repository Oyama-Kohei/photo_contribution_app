package com.example.android_trip_2023_app.utils

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ValidatorTest {

    // 正常系
    @Test
    fun mailValidationSuccess() = runTest {
        assertEquals(Validator().mailValidation("example@exp.com"), "")
    }

    @Test
    fun passwordValidationSuccess() = runTest {
        assertEquals(Validator().passwordValidation("pass"), "")
    }

    // 異常系(空文字以外)
    private var errStrs: Array<String> = arrayOf(
        "aaa", // @,ドメイン部なし
        "aaa@aa.a", // セカンドレベルドメインの文字数が足りない
        "aa@aa", // セカンドレベルドメインがなし
        "aa.aa", // @,サードレベルドメインがなし
        "@", // ユーザ名,ドメイン部なし
        ".", // ユーザ名,@,サードレベル,セカンドレベルドメイン部なし
        "aa@aa..aa", // .が連続している
        "aa@.aa.aa", // @の直後に.
        "あaa@aaa.aa", // ユーザ名に全角ひらがな
        "aaa@あaa.aa", // サードレベルドメイン部に全角ひらがな
        "aaa@aa.あaa", // セカンドレベルドメイン部に全角ひらがな
        "aアa@aa.aa", // ユーザ名に全角カタカナ
        "aaa@aアa.aa", // サードレベルドメイン部に全角カタカナ
        "aaa@aa.aアa", // セカンドレベルドメイン部に全角カタカナ
        "ｱaa@aa.aa", // ユーザ名に半角カタカナ
        "aaa@aaｱ.aa", // サードレベルドメイン部に半角カタカナ
        "aaa@aa.ｱa", // セカンドレベルドメイン部に半角カタカナ
        "aaａ@aa.aa", // ユーザ名に全角英字
        "aaa@aaａ.aa", // サードレベルドメイン部に全角英字
        "aaa@aa.aaａ", // セカンドレベルドメイン部に全角英字
        "aaa@aaa.11", // セカンドレベルドメイン部に半角英字
        "aa１@aa.aa", // ユーザ名に全角数字
        "aaa@１a.aa", // サードレベルドメイン部に全角数字
        "aaa@a.a１a", // セカンドレベルドメイン部に全角数字
        "＋aa@aa.aa", // ユーザ名に全角記号
        "aa@a＋a.aa", // サードレベルドメイン部に全角記号
        "aa@aa.aa＋", // セカンドレベルドメイン部に全角記号
        "aaa@-a.aa", // サードレベルドメイン部に半角記号
        "aaa@aa._a", // セカンドレベルドメイン部に半角記号
        "aaa①@aa.aa", // ユーザ名に特殊文字
        "aaa@a②a.aa", // サードレベルドメイン部に特殊文字
        "aaa@aa.aa③", // セカンドレベルドメイン部に特殊文字
        "亜aa@aa.aa", // ユーザ名に漢字
        "aaa@a亜a.aa", // サードレベルドメイン部に漢字
        "aaa@aa.aa亜", // セカンドレベルドメイン部に漢字
        "@aa@aaa.aaa", // @始まり
        "aaa@aaa.aa@", // @終わり
        "aaa@@aaa.aaa", // @が連続している
        " aaa@aaa.aaa", // 先頭にスペースが入っている
        "aaa@a aa.aa", // サードレベルドメイン部にスペース
        "aaa@aa.aa ", // セカンドレベルドメイン部にスペース
        "😃aa@aaa.aa", // 絵文字
        "*aa@aaa.aa", // ワイルドカード
    )

    // 異常系 - バリデーション
    @Test
    fun passwordValidationFailEmpty() = runTest {
        assertEquals(Validator().passwordValidation(""), "パスワードを入力してください。")
    }

    @Test
    fun mailValidationFailEmpty() = runTest {
        assertEquals(Validator().mailValidation(""), "メールアドレスを入力してください。")
    }

    @Test
    fun mailValidationFail() = runTest {
        for (errStr in errStrs) {
            assertEquals(Validator().mailValidation(errStr), "正しいメールアドレスを入力してください。")
        }
    }
}
