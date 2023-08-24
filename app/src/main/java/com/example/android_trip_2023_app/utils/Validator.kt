package com.example.android_trip_2023_app.utils

class Validator {

    /**
     * メールバリデーション処理.
     * @param email String
     * @return String?
     */
    fun mailValidation(email: String): String {
        val emailPattern = "^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$"

        if (email.isEmpty()) {
            return "メールアドレスを入力してください。"
        } else if (!Regex(pattern = emailPattern).containsMatchIn(email)) {
            return "正しいメールアドレスを入力してください。"
        }
        return ""
    }

    /**
     * パスワードバリデーション処理.
     * @param password String
     * @return String?
     */
    fun passwordValidation(password: String): String {
        if (password.isEmpty()) {
            return "パスワードを入力してください。"
        }
        return ""
    }
}
